package com.example.proyectointegradorv1.fragments.products;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.individual_products.BalsamoBarbaActivity;
import com.example.proyectointegradorv1.activities.individual_products.CeraActivity;
import com.example.proyectointegradorv1.activities.individual_products.CremaActivity;
import com.example.proyectointegradorv1.activities.individual_products.CremaBarbaActivity;
import com.example.proyectointegradorv1.activities.individual_products.FibraActivity;
import com.example.proyectointegradorv1.activities.individual_products.PolvoActivity;
import com.example.proyectointegradorv1.activities.individual_products.PomadaBarbaActivity;
import com.example.proyectointegradorv1.activities.individual_products.ShampooActivity;

public class ProductsFragment extends Fragment {

    private int currentFavoriteIndex = -1;

    private ImageView btnFavorite1,
            btnFavorite2,
            btnFavorite3,
            btnFavorite4,
            btnFavorite5,
            btnFavorite6,
            btnFavorite7,
            btnFavorite8;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        btnFavorite1 = (ImageView) view.findViewById(R.id.btnFavorite1);
        btnFavorite2 = (ImageView) view.findViewById(R.id.btnFavorite2);
        btnFavorite3 = (ImageView) view.findViewById(R.id.btnFavorite3);
        btnFavorite4 = (ImageView) view.findViewById(R.id.btnFavorite4);
        btnFavorite5 = (ImageView) view.findViewById(R.id.btnFavorite5);
        btnFavorite6 = (ImageView) view.findViewById(R.id.btnFavorite6);
        btnFavorite7 = (ImageView) view.findViewById(R.id.btnFavorite7);
        btnFavorite8 = (ImageView) view.findViewById(R.id.btnFavorite8);

        btnFavorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite1.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite1.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite2.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite2.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite3.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite3.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite4.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite4.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite5.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite5.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite6.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite6.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite7.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite7.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        btnFavorite8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar entre marcar y desmarcar como favorito
                if (currentFavoriteIndex == 1) {
                    // Si ya está marcado como favorito, desmarcarlo
                    btnFavorite8.setImageResource(R.drawable.ic_favorite_vacio);
                    currentFavoriteIndex = -1; // No hay favorito actualmente
                } else {
                    // Si no está marcado como favorito, marcarlo
                    btnFavorite8.setImageResource(R.drawable.ic_favorite_lleno);
                    currentFavoriteIndex = 1;
                }
            }
        });

        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout cremaBarbaProduct = view.findViewById(R.id.btnCremaBarbaProduct);

        // Asigna un OnClickListener al botón
        cremaBarbaProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), CremaBarbaActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout pomadaBarbaProduct = view.findViewById(R.id.btnPomadaBarbaProduct);

        // Asigna un OnClickListener al botón
        pomadaBarbaProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), PomadaBarbaActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout cremaProduct = view.findViewById(R.id.btnCremaProduct);

        // Asigna un OnClickListener al botón
        cremaProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), CremaActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout balsamoBarbaProduct = view.findViewById(R.id.btnBalsamoBarbaProduct);

        // Asigna un OnClickListener al botón
        balsamoBarbaProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), BalsamoBarbaActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout polvoProduct = view.findViewById(R.id.btnPolvoProduct);

        // Asigna un OnClickListener al botón
        polvoProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), PolvoActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout fibraProduct = view.findViewById(R.id.btnFibraProduct);

        // Asigna un OnClickListener al botón
        fibraProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), FibraActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout shampooProduct = view.findViewById(R.id.btnSahmpooProduct);

        // Asigna un OnClickListener al botón
        shampooProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), ShampooActivity.class);
                startActivity(intent);
            }
        });


        // Obtén una referencia al botón que quieres que inicie la actividad
        LinearLayout ceraProduct = view.findViewById(R.id.btnCeraProduct);

        // Asigna un OnClickListener al botón
        ceraProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad IndividualProductActivity
                Intent intent = new Intent(getActivity(), CeraActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}