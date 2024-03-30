package com.example.proyectointegradorv1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.fragments.map.MapFragment;

public class FindUsActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 200;
    private ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_us);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Verifica y solicita permisos de ubicación al inicio de la actividad
        checkAndRequestLocationPermission();
    }

    private void checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no está concedido, solicítalo
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Si el permiso ya está concedido, inicializa el mapa
            initMap();
        }
    }

    private void initMap() {
        // Obtiene el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Crea una instancia de MyMapFragment
        MapFragment mapFragment = new MapFragment();

        // Reemplaza cualquier instancia anterior del fragmento con la nueva
        fragmentManager.beginTransaction()
                .replace(R.id.map, mapFragment)  // Reemplaza 'map_container' con 'map'
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de ubicación concedido, inicializa el mapa
                initMap();
            } else {
                // Permiso de ubicación denegado, muestra un mensaje
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
                finish(); // Puedes finalizar la actividad o tomar otras medidas según tus necesidades
            }
        }
    }
}
