package com.example.proyectointegradorv1.activities.individual_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.ReservationsActivity;

public class DisenosActivity extends AppCompatActivity {

    ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disenos);

        btnReturn = (ImageView) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void reservar(View view) {
        Intent intent = new Intent(this, ReservationsActivity.class);
        intent.putExtra("activityOrigen", getClass().getSimpleName());
        startActivity(intent);
    }
}