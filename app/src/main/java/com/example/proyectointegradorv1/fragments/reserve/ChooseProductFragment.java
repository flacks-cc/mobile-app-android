package com.example.proyectointegradorv1.fragments.reserve;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.ReserveActivity;

public class ChooseProductFragment extends Fragment {

    private CheckBox chkUno, chkDos, chkTres, chkCuatro, chkCinco, chkSeis, chkSiete, chkOcho;
    private LinearLayout layoutPrincipal;

    public ChooseProductFragment() {
        // Required empty public constructor
    }

    public static ChooseProductFragment newInstance(int productoPredeterminadoId) {
        ChooseProductFragment fragment = new ChooseProductFragment();
        Bundle args = new Bundle();
        args.putInt("defaultProductId", productoPredeterminadoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_product, container, false);

        // Inicializa tus CheckBox
        chkUno = view.findViewById(R.id.chkUno);
        chkDos = view.findViewById(R.id.chkDos);
        chkTres = view.findViewById(R.id.chkTres);
        chkCuatro = view.findViewById(R.id.chkCuatro);
        chkCinco = view.findViewById(R.id.chkCinco);
        chkSeis = view.findViewById(R.id.chkSeis);
        chkSiete = view.findViewById(R.id.chkSiete);
        chkOcho = view.findViewById(R.id.chkOcho);

        // Inicializa tu LinearLayout
        layoutPrincipal = view.findViewById(R.id.LayoutPrincipal);

        // Agrega un Listener para los CheckBox
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Llama a la función para mostrar el producto al que se le hizo clic
                mostrarProductoSeleccionado(buttonView.getId(), isChecked);
            }
        };

        // Asigna el Listener a cada CheckBox
        chkUno.setOnCheckedChangeListener(checkBoxListener);
        chkDos.setOnCheckedChangeListener(checkBoxListener);
        chkTres.setOnCheckedChangeListener(checkBoxListener);
        chkCuatro.setOnCheckedChangeListener(checkBoxListener);
        chkCinco.setOnCheckedChangeListener(checkBoxListener);
        chkSeis.setOnCheckedChangeListener(checkBoxListener);
        chkSiete.setOnCheckedChangeListener(checkBoxListener);
        chkOcho.setOnCheckedChangeListener(checkBoxListener);

        // Ahora puedes realizar la verificación después de inicializar los CheckBox
        selectCheckBox();

        return view;
    }

    private void mostrarProductoSeleccionado(int checkBoxId, boolean isChecked) {
        Pair<String, String> producto = obtenerProducto(checkBoxId);
        String titulo = producto.first;
        String contenido = isChecked ? producto.second : "";

        // Llama al método en ReservationsActivity para agregar el segundo estado
        ((ReserveActivity) requireActivity()).agregarSegundoEstado(titulo, contenido);
    }


    private Pair<String, String> obtenerProducto(int checkBoxId) {
        String titulo = "";
        String contenido = "";

        if (checkBoxId == R.id.chkUno) {
            titulo = "Crema Barba";
            contenido = getString(R.string.imperialDesc);
        } else if (checkBoxId == R.id.chkDos) {
            titulo = "Pomada Barba";
            contenido = getString(R.string.imperial2Desc);
        } else if (checkBoxId == R.id.chkTres) {
            titulo = "Crema";
            contenido = getString(R.string.cremaDesc);
        } else if (checkBoxId == R.id.chkCuatro) {
            titulo = "Bálsamo Barba";
            contenido = getString(R.string.donPorfDesc);
        } else if (checkBoxId == R.id.chkCinco) {
            titulo = "Polvo Volum";
            contenido = getString(R.string.cuatroDesc);
        } else if (checkBoxId == R.id.chkSeis) {
            titulo = "Fibra Cabello";
            contenido = getString(R.string.topikkDesc);
        } else if (checkBoxId == R.id.chkSiete) {
            titulo = "Shampoo Barba";
            contenido = getString(R.string.graveBefDesc);
        } else if (checkBoxId == R.id.chkOcho) {
            titulo = "Cera";
            contenido = getString(R.string.fisticuffsDesc);
        }

        return new Pair<>(titulo, contenido);
    }


    public boolean isCheckBoxSelected() {
        return chkUno.isChecked() || chkDos.isChecked() || chkTres.isChecked() ||
                chkCuatro.isChecked() || chkCinco.isChecked() || chkSeis.isChecked() ||
                chkSiete.isChecked() || chkOcho.isChecked();
    }

    public void selectCheckBox() {
        if (getArguments() != null) {
            int defaultProductId = getArguments().getInt("defaultProductId", -1);
            if (defaultProductId != -1) {
                // Marca el CheckBox seleccionado basado en el id proporcionado
                if (defaultProductId == R.id.chkUno) {
                    chkUno.setChecked(true);
                } else if (defaultProductId == R.id.chkDos) {
                    chkDos.setChecked(true);
                } else if (defaultProductId == R.id.chkTres) {
                    chkTres.setChecked(true);
                } else if (defaultProductId == R.id.chkCuatro) {
                    chkCuatro.setChecked(true);
                } else if (defaultProductId == R.id.chkCinco) {
                    chkCinco.setChecked(true);
                } else if (defaultProductId == R.id.chkSeis) {
                    chkSeis.setChecked(true);
                } else if (defaultProductId == R.id.chkSiete) {
                    chkSiete.setChecked(true);
                } else if (defaultProductId == R.id.chkOcho) {
                    chkOcho.setChecked(true);
                }
            }
        }
    }

}