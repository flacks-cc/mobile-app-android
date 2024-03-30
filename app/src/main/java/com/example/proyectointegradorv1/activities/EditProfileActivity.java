package com.example.proyectointegradorv1.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectointegradorv1.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EditProfileActivity extends AppCompatActivity {

    private EditText txtPhone;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfigProfile;
    private EditText txtName;
    private EditText txtLastName1;
    private EditText txtLastName2;
    private EditText txtUsername;
    private ImageView btnReturn;

    private static final int PICK_IMAGE = 1;
    private static final String PREF_PROFILE_IMAGE = "ProfileImage";
    public static final int RESULT_IMAGE_REMOVED = RESULT_FIRST_USER + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txtName = findViewById(R.id.txtName);
        txtLastName1 = findViewById(R.id.txtLastName1);
        txtLastName2 = findViewById(R.id.txtLastName2);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        // Muestra el ID en el TextView
        TextView mostrarIdTextView = findViewById(R.id.mostrarIdTextView);
        mostrarIdTextView.setText(userId);


        ImageView profileImageView = findViewById(R.id.imgProfileDos);
        txtConfigProfile = findViewById(R.id.txtName);


        SharedPreferences profileImagePreferences = getSharedPreferences(PREF_PROFILE_IMAGE, MODE_PRIVATE);

        String image = profileImagePreferences.getString("image", null);
        if (image != null) {
            profileImageView.setImageBitmap(base64ToBitmap(image));
        }

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfilePicture();
            }
        });

        String id = getIntent().getStringExtra("id");
        Log.d("EditProfileActivity", "User ID from Intent: " + id);
        if (id != null) {
            // Ahora puedes usar el id según sea necesario
        } else {
            // Maneja la situación donde id es nulo
        }

        txtConfigProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // No hay cuadro de diálogo, directamente llama a editarNombreUsuario
                editarNombreUsuario();
            }
        });

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // No hay cuadro de diálogo, directamente llama a editarNumeroTelefono
                editarNumeroTelefono();
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // No hay cuadro de diálogo, directamente llama a editarCorreoElectronico
                editarCorreoElectronico();
            }
        });

        txtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // No hay cuadro de diálogo, directamente llama a editarContrasena
                editarContrasena();
            }
        });

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void sendMessage(View view) {
    }

    private void removeProfileImage() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_PROFILE_IMAGE, MODE_PRIVATE);
        sharedPreferences.edit().remove("image").apply();

        ImageView imgProfile = findViewById(R.id.imgProfileDos);
        imgProfile.setImageResource(R.drawable.ic_user);

        Toast.makeText(this, "Foto de perfil eliminada", Toast.LENGTH_SHORT).show();

        setResult(RESULT_IMAGE_REMOVED);

    }

    public void changeProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ImageView imageView = findViewById(R.id.imgProfileDos);
                imageView.setImageBitmap(bitmap);

                SharedPreferences sharedPreferences = getSharedPreferences(PREF_PROFILE_IMAGE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("image", bitmapToBase64(bitmap));
                editor.apply();

                Toast.makeText(this, "Tu foto de perfil se ha cambiado correctamente", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void handleUpdateButtonClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        if (isValidForm()) {
            String name = txtName.getText().toString();
            String phone = txtPhone.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String lastName1 = txtLastName1.getText().toString();
            String lastName2 = txtLastName2.getText().toString();
            String username = txtUsername.getText().toString();

            // Llamada al método para actualizar el perfil
            updateUserProfile(userId, name, phone, email, password, lastName1, lastName2, username);

            // Mostrar el Toast
            Toast.makeText(this, "Tus datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();

            // Regresar a la actividad principal (HomeActivity)
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish(); // Esto finaliza la actividad actual para que no se pueda regresar con el botón de atrás
        }
    }



    public void editarNombreUsuario() {
        // No hay cuadro de diálogo, se llama directamente
    }

    public void editarNumeroTelefono() {
    }

    public void editarCorreoElectronico() {
    }

    public void editarContrasena() {
    }

    private void mostrarCuadroDeDialogoParaEditar(String titulo, final EditText campo, int inputType) {
        // El cuadro de diálogo se ha eliminado, el código directo se coloca en los métodos correspondientes
    }


    private void updateUserProfile(String id, String name, String phone, String email, String password, String lastName1, String lastName2, String username) {
        String url = "http://192.168.3.236:80/barberia/public/api/update/" + id;
        Log.d("UpdateUserProfile", "URL de la solicitud: " + url);

        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("name", name);
            requestBody.put("firstlastname", lastName1);
            requestBody.put("secondlastname", lastName2);
            requestBody.put("firstname", username);
            requestBody.put("email", email);
            requestBody.put("phone", phone);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Maneja la respuesta aquí
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error aquí
                    }
                });

        // Añade la solicitud a la cola de solicitudes
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }



    private void handleRequestError(VolleyError error) {
        // Manejar errores de la solicitud
        Toast.makeText(EditProfileActivity.this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        error.printStackTrace();

        // Imprimir la respuesta del servidor
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null && networkResponse.data != null) {
            try {
                String responseString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                Log.e("UpdateUserProfile", "Respuesta del servidor: " + responseString);

                // Verificar si la respuesta es un JSON válido
                if (responseString.startsWith("<!doctype")) {
                    // La respuesta no es un JSON válido, manejar de acuerdo a tus requerimientos
                    Log.e("UpdateUserProfile", "La respuesta del servidor no es un JSON válido");

                    // Puedes agregar aquí la lógica para manejar respuestas que no sean JSON
                    // Por ejemplo, mostrar un mensaje de error al usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProfileActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // La respuesta es un JSON válido
                    JSONObject jsonObject = new JSONObject(responseString);
                    // Manejar la respuesta JSON según sea necesario
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleServerError(String errorMessage) {
        // Manejar errores relacionados con el servidor
        Toast.makeText(EditProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("UpdateUserProfile", errorMessage);
    }


    private boolean isValidForm() {
        // Validar que todos los campos estén llenos y la información sea correcta
        if (TextUtils.isEmpty(txtName.getText())
                || TextUtils.isEmpty(txtLastName1.getText())
                || TextUtils.isEmpty(txtUsername.getText())
                || TextUtils.isEmpty(txtPhone.getText())
                || TextUtils.isEmpty(txtEmail.getText())
                || TextUtils.isEmpty(txtPassword.getText())) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidUsername(txtUsername.getText().toString().trim())) {
            Toast.makeText(this, "Nombre de usuario no válido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPhoneNumber(txtPhone.getText().toString().trim())) {
            Toast.makeText(this, "Número de teléfono no válido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(txtEmail.getText().toString().trim())) {
            Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(txtPassword.getText().toString().trim())) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return !phoneNumber.isEmpty();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
