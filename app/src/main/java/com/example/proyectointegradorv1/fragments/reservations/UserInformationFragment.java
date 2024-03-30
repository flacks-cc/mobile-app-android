package com.example.proyectointegradorv1.fragments.reservations;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.LoginActivity;
import com.example.proyectointegradorv1.activities.ReservationsActivity;

public class UserInformationFragment extends Fragment {

    private EditText txtName, txtLastName1, txtLastName2, txtEmail, txtPhone;
    private TextView lblLogin;

    public UserInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);

        txtName = view.findViewById(R.id.txtName);
        txtLastName1 = view.findViewById(R.id.txtLastName1);
        txtLastName2 = view.findViewById(R.id.txtLastName2);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPhone = view.findViewById(R.id.txtPhone);
        lblLogin = view.findViewById(R.id.lblLogin);

        // Agrega un escuchador a todos los EditText
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No necesitamos hacer nada antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Cuando el texto cambia, llama al método para mostrar la información del usuario
                mostrarInformacionUsuario();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No necesitamos hacer nada después de que el texto cambie
            }
        };

        // Agrega el OnClickListener al lblLogin
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion(v);
            }
        });

        setValoresCamposTexto();

        txtName.addTextChangedListener(textWatcher);
        txtLastName1.addTextChangedListener(textWatcher);
        txtLastName2.addTextChangedListener(textWatcher);
        txtEmail.addTextChangedListener(textWatcher);
        txtPhone.addTextChangedListener(textWatcher);

        return view;
    }

    // Método para obtener los valores de los campos de texto
    private Pair<String, String> obtenerInformacionUsuario() {
        // Obtén los valores de los campos de texto
        String name = txtName.getText().toString().trim();
        String lastName1 = txtLastName1.getText().toString().trim();
        String lastName2 = txtLastName2.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();

        // Construye el contenido a mostrar
        String nombreCompleto = name + " " + lastName1 + " " + lastName2;
        String contacto = email + "\n" + phone;

        // Retorna la información obtenida como un Pair
        return new Pair<>(nombreCompleto, contacto);
    }

    // Método para mostrar la información del usuario
    private void mostrarInformacionUsuario() {
        // Llama al método en ReservationsActivity para agregar el tercer estado
        if (validarCampos()) {
            Pair<String, String> informacion = obtenerInformacionUsuario();
            ((ReservationsActivity) requireActivity()).agregarCuartoEstado(informacion.first, informacion.second);
        }
    }

    public boolean validarCampos() {
        String name = txtName.getText().toString().trim();
        String lastName1 = txtLastName1.getText().toString().trim();
        String lastName2 = txtLastName2.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();

        boolean hayErrores = false;

        if (TextUtils.isEmpty(name)) {
            txtName.setError("El nombre es obligatorio");
            hayErrores = true;
        }
        if (TextUtils.isEmpty(lastName1)) {
            txtLastName1.setError("El primer apellido es obligatorio");
            hayErrores = true;
        }
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("El correo electrónico es obligatorio");
            hayErrores = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Ingrese un correo electrónico válido");
            hayErrores = true;
        }
        if (TextUtils.isEmpty(phone)) {
            txtPhone.setError("El número de teléfono es obligatorio");
            hayErrores = true;
        } else if (phone.length() != 10) {
            txtPhone.setError("El número de teléfono debe tener 10 caracteres");
            hayErrores = true;
        }

        // Retorna true si no hay errores, false si hay al menos un error
        return !hayErrores;
    }

    // Función para establecer los valores de los campos de texto si existen previamente
    private void setValoresCamposTexto() {
        String name = obtenerValor("name");
        String lastName1 = obtenerValor("lastName1");
        String lastName2 = obtenerValor("lastName2");
        String email = obtenerValor("email");
        String phone = obtenerValor("phone");

        if (!TextUtils.isEmpty(name)) {
            txtName.setText(name);
        }
        if (!TextUtils.isEmpty(lastName1)) {
            txtLastName1.setText(lastName1);
        }
        if (!TextUtils.isEmpty(lastName2)) {
            txtLastName2.setText(lastName2);
        }
        if (!TextUtils.isEmpty(email)) {
            txtEmail.setText(email);
        }
        if (!TextUtils.isEmpty(phone)) {
            txtPhone.setText(phone);
        }
    }

    // Función para obtener los valores de los campos de texto desde el almacenamiento
    private String obtenerValor(String clave) {
        // Implementa la lógica para obtener el valor asociado a la clave desde donde sea necesario (preferencias, base de datos, etc.)
        // Por ahora, retorna una cadena vacía.
        return "";
    }


    public void iniciarSesion(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}