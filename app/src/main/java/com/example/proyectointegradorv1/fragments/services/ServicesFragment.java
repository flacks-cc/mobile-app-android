package com.example.proyectointegradorv1.fragments.services;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.individual_services.AfeitadoActivity;
import com.example.proyectointegradorv1.activities.individual_services.ArregloBarbaActivity;
import com.example.proyectointegradorv1.activities.individual_services.ArregloCejaActivity;
import com.example.proyectointegradorv1.activities.individual_services.CorteEscolarActivity;
import com.example.proyectointegradorv1.activities.individual_services.DesvanecidoActivity;
import com.example.proyectointegradorv1.activities.individual_services.DisenosActivity;
import com.example.proyectointegradorv1.activities.individual_services.MascarillaActivity;

public class ServicesFragment extends Fragment {

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout desvanecidoService = view.findViewById(R.id.btnDesvanecidoService);

        // Asigna un OnClickListener al botón
        desvanecidoService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), DesvanecidoActivity.class);
                startActivity(intent);
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout arregloBarbarService = view.findViewById(R.id.btnArregloBarbaService);

        // Asigna un OnClickListener al botón
        arregloBarbarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), ArregloBarbaActivity.class);
                startActivity(intent);
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout corteEscolarService = view.findViewById(R.id.btnCorteEscolarService);

        // Asigna un OnClickListener al botón
        corteEscolarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), CorteEscolarActivity.class);
                startActivity(intent);
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout afeitadoService = view.findViewById(R.id.btnAfeitadoService);

        // Asigna un OnClickListener al botón
        afeitadoService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), AfeitadoActivity.class);
                startActivity(intent);
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout disenosService = view.findViewById(R.id.btnDisenosService);

        // Asigna un OnClickListener al botón
        disenosService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), DisenosActivity.class);
                startActivity(intent);
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout arregloCejaService = view.findViewById(R.id.btnArregloCejaService);

        // Asigna un OnClickListener al botón
        arregloCejaService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), ArregloCejaActivity.class);
                startActivity(intent);
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout mascarillaService = view.findViewById(R.id.btnMascarillaService);

        // Asigna un OnClickListener al botón
        mascarillaService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), MascarillaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
