package com.example.proyectointegradorv1.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectointegradorv1.R;

public class ContactUsActivity extends AppCompatActivity {

    private ImageView btnReturn;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtPhone;
    private EditText txtEmail;
    private EditText txtMessage;
    private TextView lblCallUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        // Inicializa los campos de entrada
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtMessage = findViewById(R.id.txtMessage);
        lblCallUs = findViewById(R.id.lblCallUs);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Agrega el OnClickListener a lblCallUs
        lblCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarNumeroTelefono();
            }
        });
    }

    public void sendMessage(View view) {
        String firstName = txtFirstName.getText().toString().trim();
        String lastName = txtLastName.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String message = txtMessage.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            txtFirstName.setError("Este campo es obligatorio");
        }
        if (TextUtils.isEmpty(lastName)) {
            txtLastName.setError("Este campo es obligatorio");
        }
        if (TextUtils.isEmpty(phone)) {
            txtPhone.setError("Este campo es obligatorio");
        } else if (phone.length() != 10) {
            txtPhone.setError("El número de teléfono debe tener 10 caracteres");
        }
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Este campo es obligatorio");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Ingrese un correo electrónico válido");
        }
        if (TextUtils.isEmpty(message)) {
            txtMessage.setError("Este campo es obligatorio");
        }
    }

    // Función para llamar al número de teléfono
    private void llamarNumeroTelefono() {
        // Obtiene el número de teléfono del TextView
        String numeroTelefono = "2712667365";

        // Verifica que el número de teléfono no esté vacío
        if (!TextUtils.isEmpty(numeroTelefono)) {
            // Crea un Intent con la acción ACTION_DIAL y el número de teléfono
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numeroTelefono));

            // Inicia la actividad del marcador de teléfono
            startActivity(intent);
        }
    }
}