package com.example.proyectointegradorv1.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.SignUpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmailOrPhone;
    private EditText txtPassword;
    private static final String TAG = "LoginActivity";
    String stringPassword;
    String stringEmail;
    String url = "http://192.168.3.236:80/barberia/public/api/login";
    Button googleAuth;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient nGoogleSignInClient;
    int RC_SIGN_IN = 20;
    private ProgressDialog progressDialog;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa los campos de entrada
        txtEmailOrPhone = findViewById(R.id.txtEmailOrPhone);
        txtPassword = findViewById(R.id.txtPassword);
        googleAuth = findViewById(R.id.btnGoogleAuth);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        nGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleAuth.setOnClickListener(view -> googleSignIn());
    }

    public void registrate(View view) {
        Intent registrate = new Intent(this, SignUpActivity.class);
        startActivity(registrate);
    }

    public void invitado(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void googleSignIn() {
        Intent intent = nGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (nGoogleSignInClient != null) {
            nGoogleSignInClient.signOut();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id", user.getUid());
                                map.put("name", user.getDisplayName());
                                if (user.getPhotoUrl() != null) {
                                    map.put("profile", user.getPhotoUrl().toString());
                                }
                                database.getReference().child("users").child(user.getUid()).setValue(map);

                                // Guarda el id
                                guardarid(user.getUid());

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error en la autenticación", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleLoginResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.optBoolean("success", false)) {
                JSONObject userData = jsonResponse.optJSONObject("data");

                if (userData != null) {
                    String id = userData.optString("id", "");
                    String token = userData.optString("token", "");
                    String name = userData.optString("name", "");

                    // Guarda el nombre del usuario en las preferencias compartidas con la clave "name"
                    guardarNombreEnPreferencias(name);

                    // Puedes utilizar la información adicional según sea necesario
                    // Por ejemplo, almacenar el token en SharedPreferences

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", id);
                    editor.putString("userToken", token);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();

                    // Continuar con la lógica de navegación, por ejemplo, ir a la pantalla principal
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Error durante el inicio de sesión", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Error durante el inicio de sesión", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error en el formato de la respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    // Modifica la clave a "name"
    // Modifica la clave a "name"
    private void guardarNombreEnPreferencias(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();

        // Agrega una línea para imprimir el nombre en el Log
        Log.d(TAG, "Nombre guardado en preferencias: " + name);
    }




    private String getidFromResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.optString("id", "");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }


    public void login(View view) {
        if (txtEmailOrPhone.getText().toString().isEmpty()) {
            Log.d(TAG, "Email is empty");
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (txtPassword.getText().toString().isEmpty()) {
            Log.d(TAG, "Password is empty");
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            final String email = txtEmailOrPhone.getText().toString().trim();
            final String password = txtPassword.getText().toString().trim();

            Log.d(TAG, "Logging in with email: " + email);

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            handleLoginResponse(response);

                            if (response.equalsIgnoreCase("ingreso correctamente")) {
                                // Obtén el id de la respuesta
                                String id = getidFromResponse(response);

                                // Guarda el id en las preferencias compartidas
                                guardarIdEnPreferencias(id);

                                // Inicia la actividad EditProfileActivity
                                Intent intent = new Intent(LoginActivity.this, EditProfileActivity.class);
                                startActivity(intent);

                                // Muestra un mensaje de éxito
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            } else {
                                // Muestra un mensaje de error
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e(TAG, "Error during login: " + error.getMessage());
                            Toast.makeText(LoginActivity.this, "Error during login", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
            Log.d(TAG, "Login request sent");
        }
    }



    // Agrega este método para guardar el id en las preferencias compartidas
    private void guardarIdEnPreferencias(String id) {
        SharedPreferences sharedPreference = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("userId", id);
        editor.apply();
    }

    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();
    }

    // Método para guardar el id en una variable privada de la clase
    private void guardarid(String id) {
        this.id = id;
    }

    // Método para obtener el id guardado
    public String obtenerid() {
        return this.id;
    }
}