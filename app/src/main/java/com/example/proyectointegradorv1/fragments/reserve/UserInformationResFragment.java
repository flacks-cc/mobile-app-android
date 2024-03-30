package com.example.proyectointegradorv1.fragments.reserve;

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

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.ReservationsActivity;
import com.example.proyectointegradorv1.activities.ReserveActivity;

public class UserInformationResFragment extends Fragment {

    private EditText txtName, txtLastName1, txtLastName2, txtUsername, txtEmail, txtPhone;

    public UserInformationResFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_information_res, container, false);

        txtName = view.findViewById(R.id.txtName);
        txtLastName1 = view.findViewById(R.id.txtLastName1);
        txtLastName2 = view.findViewById(R.id.txtLastName2);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPhone = view.findViewById(R.id.txtPhone);

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
        String username = txtUsername.getText().toString().trim();
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
            ((ReserveActivity) requireActivity()).agregarCuartoEstado(informacion.first, informacion.second);
        }
    }

    public boolean validarCampos() {
        String name = txtName.getText().toString().trim();
        String lastName1 = txtLastName1.getText().toString().trim();
        String lastName2 = txtLastName2.getText().toString().trim();
        String username = txtUsername.getText().toString().trim();
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
        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("El nombre de usuario es obligatorio");
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
}