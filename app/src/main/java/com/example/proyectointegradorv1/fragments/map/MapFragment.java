package com.example.proyectointegradorv1.fragments.map;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.proyectointegradorv1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    // Agrega estas variables globales a tu clase
    private double latitudEspecifica;
    private double longitudEspecifica;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_find_us, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Verificar y solicitar permisos al crear la vista
        checkAndRequestPermissions();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);  // Cambia 'map_container' con 'map'
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar y solicitar permisos al crear el fragmento
        checkAndRequestPermissions();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicita los permisos de ubicación
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Habilita la capa de ubicación
        mMap.setMyLocationEnabled(true);

        // Obtiene la última ubicación
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    // Obtiene la última ubicación conocida. En algunos casos raros, puede ser nulo.
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                        // Establece las coordenadas específicas del enlace proporcionado
                        // Aquí deberás extraer las coordenadas del enlace proporcionado
                        // Puedes hacerlo manualmente o mediante alguna biblioteca de análisis de URL
                        latitudEspecifica = 18.898600712033797; // Reemplaza con la latitud específica del enlace
                        longitudEspecifica = -96.9304224206741; // Reemplaza con la longitud específica del enlace
                        // Agrega marcadores en la ubicación actual y la ubicación específica
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Mi ubicación"));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitudEspecifica, longitudEspecifica)).title("Ubicación Específica"));

                        // Muestra la ruta entre la ubicación actual y la ubicación específica
                        showRoute(currentLocation, new LatLng(latitudEspecifica, longitudEspecifica));

                        // Ajusta la cámara para mostrar ambas ubicaciones
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(currentLocation);
                        builder.include(new LatLng(latitudEspecifica, longitudEspecifica));
                        LatLngBounds bounds = builder.build();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                    } else {
                        Toast.makeText(requireContext(), "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRoute(LatLng origin, LatLng destination) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDRklr8QS6e9qJZnf773cqhZ2wRl3FY5vQ") // Reemplaza con tu clave de API de Google Maps
                .build();

        DirectionsApiRequest request = DirectionsApi.getDirections(context,
                origin.latitude + "," + origin.longitude,
                destination.latitude + "," + destination.longitude);

        try {
            DirectionsResult result = request.await();
            // Maneja el resultado y muestra la ruta en el mapa
            // Aquí deberías implementar la lógica para extraer y mostrar la ruta
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja los errores de la solicitud de direcciones
        }
    }

    private void checkAndRequestPermissions() {
        if (!checkPermission()) {
            // Si los permisos no están concedidos, solicitarlos
            requestPermissions();
        }
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        // Solicitar permisos al usuario
        ActivityCompat.requestPermissions(requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }
}
