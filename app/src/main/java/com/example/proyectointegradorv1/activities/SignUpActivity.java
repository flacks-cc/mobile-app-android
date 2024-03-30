package com.example.proyectointegradorv1.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectointegradorv1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtName, txtLastName1, txtLastName2, txtUsername, txtEmail, txtPhone, txtPassword, txtRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inicializa los campos de entrada
        txtName = findViewById(R.id.txtName);
        txtLastName1 = findViewById(R.id.txtLastName1);
        txtLastName2 = findViewById(R.id.txtLastName2);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtRepeatPassword = findViewById(R.id.txtRepeatPassword);
    }

    public void iniciarSesion(View view) {
        onBackPressed();
    }

    public void singUp(View view) {
        // Obtener los valores de los campos de entrada
        String name = txtName.getText().toString().trim();
        String lastName1 = txtLastName1.getText().toString().trim();
        String lastName2 = txtLastName2.getText().toString().trim();
        String username = txtUsername.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String repeatPassword = txtRepeatPassword.getText().toString().trim();

        // Validaciones...
        if (isValidInput(name, lastName1, lastName2, username, email, phone, password, repeatPassword)) {
            registerUser(name, lastName1, lastName2, username, email, phone, password);
        }
    }

    private boolean isValidInput(String name, String lastName1, String lastName2, String username,
                                 String email, String phone, String password, String repeatPassword) {
        if (TextUtils.isEmpty(name)) {
            txtName.setError("Este campo es obligatorio");
            return false;
        }
        if (TextUtils.isEmpty(lastName1)) {
            txtLastName1.setError("Este campo es obligatorio");
            return false;
        }
        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Este campo es obligatorio");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Este campo es obligatorio");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Ingrese un correo electrónico válido");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            txtPhone.setError("Este campo es obligatorio");
            return false;
        } else if (phone.length() != 10) {
            txtPhone.setError("El número de teléfono debe tener 10 caracteres");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Este campo es obligatorio");
            return false;
        } else if (password.length() < 8) {
            txtPassword.setError("La contraseña debe tener al menos 8 caracteres");
            return false;
        }
        if (TextUtils.isEmpty(repeatPassword)) {
            txtRepeatPassword.setError("Este campo es obligatorio");
            return false;
        } else if (!password.equals(repeatPassword)) {
            txtRepeatPassword.setError("Las contraseñas no coinciden");
            return false;
        }

        return true; // Devolver true si todas las validaciones son exitosas
    }

    private void registerUser(String name, String lastName1, String lastName2, String username,
                              String email, String phone, String password) {
        String url = "http://192.168.3.236:80/barberia/public/api/register"; // Reemplaza con la URL correcta de tu API

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("firstlastname", lastName1);
            requestBody.put("secondlastname", lastName2);
            requestBody.put("firstname", username);
            requestBody.put("email", email);
            requestBody.put("phone", phone);
            requestBody.put("password", password);
            // Añade más campos según sea necesario
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            // Puedes manejar el token y otros datos de la respuesta según sea necesario
                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorResponse = new String(error.networkResponse.data);
                            Log.e("ErrorResponse", errorResponse);

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                boolean success = jsonObject.optBoolean("success", false);
                                String message = jsonObject.optString("message", "Error en la solicitud");

                                if (!success && jsonObject.has("data")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    if (data.has("email")) {
                                        // El correo electrónico ya está registrado
                                        String emailError = data.getJSONArray("email").getString(0);
                                        Toast.makeText(SignUpActivity.this, "Error: " + emailError, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                // Manejo general del error
                                Toast.makeText(SignUpActivity.this, "Error en la solicitud: " + message, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        error.printStackTrace();
                    }
                });

// Agrega la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(request);
    }
}