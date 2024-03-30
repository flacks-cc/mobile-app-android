package com.example.proyectointegradorv1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.proyectointegradorv1.R;

public class ConfigurationActivity extends AppCompatActivity {

    ImageView btnReturn;
    RelativeLayout logoutLayout;  // Agregado

    private Switch switchNotifications;
    private Switch switchDarkMode;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String NOTIFICATIONS_KEY = "notifications";
    private static final String DARK_MODE_KEY = "darkMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        // Referencias a los elementos de la interfaz
        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);

        // Configuración de los listeners
        configureSwitchListeners();

        // Restaurar el estado de los switches desde SharedPreferences
        restoreSwitchStates();

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        logoutLayout = findViewById(R.id.logoutLayout);  // Agregado
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para cerrar sesión y regresar a LoginActivity
                cerrarSesion();
            }
        });
    }

    private void configureSwitchListeners() {
        // Listener para el switch de Modo Oscuro
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Lógica para activar o desactivar el Modo Oscuro
                if (isChecked) {
                    // Activar Modo Oscuro
                    // Implementa tu lógica aquí
                } else {
                    // Desactivar Modo Oscuro
                    // Implementa tu lógica aquí
                }
            }
        });

        // Listener para el switch de Notificaciones
        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Lógica para activar o desactivar las Notificaciones
                saveSwitchState(NOTIFICATIONS_KEY, isChecked);
            }
        });
    }

    private void saveSwitchState(String key, boolean isChecked) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, isChecked);
        editor.apply();
    }

    private void restoreSwitchStates() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        switchNotifications.setChecked(settings.getBoolean(NOTIFICATIONS_KEY, false));
        switchDarkMode.setChecked(settings.getBoolean(DARK_MODE_KEY, false));
    }

    public void editar_perfil(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        // Aquí colocas la lógica para cerrar sesión
        // Por ejemplo, puedes borrar las preferencias de usuario y luego iniciar LoginActivity
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();

        // Opcional: Iniciar LoginActivity después de cerrar sesión
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finaliza la actividad actual
        finish();
    }
}


