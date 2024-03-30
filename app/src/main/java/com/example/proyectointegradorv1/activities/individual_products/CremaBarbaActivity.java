package com.example.proyectointegradorv1.activities.individual_products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.ReserveActivity;

public class CremaBarbaActivity extends AppCompatActivity {
    private TextView txtStock;

    private int cantidad = 1; // Valor mínimo
    ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crema_barba);

        btnReturn = (ImageView) findViewById(R.id.btnReturn);

        txtStock = findViewById(R.id.txtStock);
        Button btnMenos = findViewById(R.id.btnMenos);
        Button btnMas = findViewById(R.id.btnMas);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementarCantidad();
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementarCantidad();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void reservar(View view) {
        Intent intent = new Intent(this, ReserveActivity.class);
        intent.putExtra("activityOrigen", getClass().getSimpleName());
        startActivity(intent);
    }

    private void decrementarCantidad() {
        if (cantidad > 1) {
            cantidad--;
            actualizarCantidad();
        }
    }

    private void incrementarCantidad() {

        if (cantidad < 10) {
            cantidad++;
            actualizarCantidad();
        }
    }

    private void actualizarCantidad() {
        txtStock.setText(String.valueOf(cantidad));
    }
}