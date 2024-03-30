package com.example.proyectointegradorv1.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.fragments.history.HistoryFragment;
import com.example.proyectointegradorv1.fragments.home.HomeFragment;
import com.example.proyectointegradorv1.fragments.products.ProductsFragment;
import com.example.proyectointegradorv1.fragments.reviews.ReviewsFragment;
import com.example.proyectointegradorv1.fragments.services.ServicesFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    BottomNavigationView mBottomNavigation;
    private Fragment currentFragment;
    private static final int EDIT_PROFILE_REQUEST_CODE = 1;
    private TextView lblName;
    private TextView mostrarIdTextView;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializa los TextView y la ImageView
        lblName = findViewById(R.id.lblName);
        mostrarIdTextView = findViewById(R.id.Mostrarid);
        imgProfile = findViewById(R.id.imgProfile);

        // Recupera el nombre, el ID y la imagen del usuario desde SharedPreferences
        String userName = getUserName();
        String userId = getUserId();
        String encodedImage = getEncodedImage();

        // Agrega líneas para imprimir el nombre y el ID en el Log
        Log.d(TAG, "Nombre recuperado en HomeActivity: " + userName);
        Log.d(TAG, "ID recuperado en HomeActivity: " + userId);

        // Actualiza el texto de los TextView con el nombre y el ID del usuario
        lblName.setText(userName);
        mostrarIdTextView.setText(userId);

        // Carga la imagen de perfil si está disponible
        if (encodedImage != null && !encodedImage.isEmpty()) {
            imgProfile.setImageBitmap(base64ToBitmap(encodedImage));
        }
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_fragment) {
                showSelectedFragment(new HomeFragment());
            }
            // Agrega el resto de las condiciones para otros fragments...
            return true;
        });

        showSelectedFragment(new HomeFragment());
        mBottomNavigation.setSelectedItemId(R.id.home_fragment);
    }

    // Método para obtener el nombre del usuario desde SharedPreferences
    private String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        return sharedPreferences.getString("name", "Invitado");
    }

    // Método para obtener el ID del usuario desde SharedPreferences
    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }

    private String getEncodedImage() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
        return sharedPreferences.getString("image", null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Verifica si el usuario ha iniciado sesión con Google
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String name = account.getDisplayName();
            lblName.setText(name);

            // Muestra un mensaje de bienvenida
            Toast.makeText(this, "¡Bienvenido, " + name + "!", Toast.LENGTH_LONG).show();
        }
    }







    @Override
    public void onBackPressed() {
        // Obtén el número actual de fragmentos en la pila
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount > 0) {
            // Si hay fragmentos en la pila, retrocede a la entrada anterior
            getSupportFragmentManager().popBackStack();
            // Actualiza el fragmento actual
            currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameContainer);
        } else if (currentFragment instanceof HomeFragment) {
            // Si el fragmento actual es HomeFragment, cierra la aplicación
            finish();
        } else {
            // Si no hay fragmentos en la pila y no estás en HomeFragment, vuelve al HomeFragment
            showSelectedFragment(new HomeFragment());
            currentFragment = new HomeFragment();
            mBottomNavigation.setSelectedItemId(R.id.home_fragment);
        }
    }

    public void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void servicios(View view) {
        showSelectedFragment(new ServicesFragment());
        mBottomNavigation.setSelectedItemId(R.id.services_fragment);
    }

    public void productos(View view) {
        showSelectedFragment(new ProductsFragment());
        mBottomNavigation.setSelectedItemId(R.id.products_fragment);
    }

    public void resenas(View view) {
        showSelectedFragment(new ReviewsFragment());
    }

    public void historial(View view) {
        showSelectedFragment(new HistoryFragment());
    }

    public void configuracion(View view) {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }


    public void contactanos(View view) {
        Intent intent = new Intent(this, ContactUsActivity.class);
        startActivity(intent);
    }

    public void encuentranos(View view) {
        Intent intent = new Intent(this, FindUsActivity.class);
        startActivity(intent);
    }

    private void removeProfileImage() {
        // Elimina la foto de las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
        sharedPreferences.edit().remove("image").apply();

        // Establece el fondo predeterminado (puedes ajustar según tus necesidades)
        ImageView imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setBackgroundResource(R.drawable.ic_user); // Reemplaza con tu imagen predeterminada

        // Muestra un mensaje indicando que la foto se eliminó
        Toast.makeText(this, "Foto de perfil eliminada", Toast.LENGTH_SHORT).show();
    }


    public void editar_perfil(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE) {
            // Manejar el resultado de EditProfileActivity
            if (resultCode == RESULT_OK) {
                // El perfil se actualizó, recargar la imagen de perfil
                loadProfileImage();
            } else if (resultCode == EditProfileActivity.RESULT_IMAGE_REMOVED) {
                // La imagen de perfil fue eliminada, manejar según sea necesario
                handleProfileImageRemoved();
            }
        }
    }

    private void handleProfileImageRemoved() {

        ImageView imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setImageResource(R.drawable.ic_user); // Establece tu imagen predeterminada
        Toast.makeText(this, "Foto de perfil eliminada", Toast.LENGTH_SHORT).show();
    }

    private void loadProfileImage() {

        new Thread(() -> {
            final ImageView imgProfile = findViewById(R.id.imgProfile);
            final SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
            final String image = sharedPreferences.getString("image", null);
            if (image != null) {
                runOnUiThread(() -> imgProfile.setImageBitmap(base64ToBitmap(image)));
            }
        }).start();
    }


    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
