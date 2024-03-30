package com.example.proyectointegradorv1.fragments.reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.ReservationsActivity;

import kotlin.Triple;

public class ChooseServiceFragment extends Fragment {

    private RadioButton rbtUno, rbtDos, rbtTres, rbtCuatro, rbtCinco, rbtSeis, rbtSiete;
    private RadioGroup radioGroupServicios;

    public ChooseServiceFragment() {
        // Required empty public constructor
    }

    public class Triple<F, S, T> {
        public final F first;
        public final S second;
        public final T third;

        public Triple(F first, S second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }


    public static ChooseServiceFragment newInstance(int servicioPredeterminadoId) {
        ChooseServiceFragment fragment = new ChooseServiceFragment();
        Bundle args = new Bundle();
        args.putInt("defaultServiceId", servicioPredeterminadoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_service, container, false);

        // Inicializa tus RadioButton
        rbtUno = view.findViewById(R.id.rbtUno);
        rbtDos = view.findViewById(R.id.rbtDos);
        rbtTres = view.findViewById(R.id.rbtTres);
        rbtCuatro = view.findViewById(R.id.rbtCuatro);
        rbtCinco = view.findViewById(R.id.rbtCinco);
        rbtSeis = view.findViewById(R.id.rbtSeis);
        rbtSiete = view.findViewById(R.id.rbtSiete);

        // Agrega un RadioGroup y establece un Listener para los RadioButton
        radioGroupServicios = view.findViewById(R.id.radioGroupServicios);
        radioGroupServicios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Llama a la función para mostrar el servicio al que se le hizo clic
                mostrarServicioSeleccionado(checkedId);
            }
        });

        // Ahora puedes realizar la verificación después de inicializar radioGroupServicios
        selectRadioButton();

        return view;
    }

    private void mostrarServicioSeleccionado(int checkedId) {
        Triple<String, String, Integer> servicio = obtenerServicio(checkedId);
        String titulo = servicio.first;
        String contenido = servicio.second;
        int precio = servicio.third;

        // Llama al método en ReservationsActivity para agregar el segundo estado
        ((ReservationsActivity) requireActivity()).agregarSegundoEstado(titulo, contenido, precio);
    }

    private Triple<String, String, Integer> obtenerServicio(int checkedId) {
        String titulo = "";
        String contenido = "";
        int precio = 0;

        if (checkedId == R.id.rbtUno) {
            titulo = "Corte desvanecido";
            contenido = "Un corte que se caracteriza por tener un aspecto limpio y bien definido.";
            precio = 100;
        } else if (checkedId == R.id.rbtDos) {
            titulo = "Arreglo de barba";
            contenido = "Un servicio que incluye recorte y cuidado de la barba para un aspecto pulido.";
            precio = 120;
        } else if (checkedId == R.id.rbtTres) {
            titulo = "Corte escolar";
            contenido = "Un estilo de corte clásico y sencillo, ideal para un aspecto pulcro y ordenado.";
            precio = 80;
        } else if (checkedId == R.id.rbtCuatro) {
            titulo = "Afeitado express";
            contenido = "Un servicio rápido de afeitado para mantener un rostro suave y bien cuidado.";
            precio = 50;
        } else if (checkedId == R.id.rbtCinco) {
            titulo = "Diseños";
            contenido = "Servicio que ofrece opciones de diseño y patrones creativos en el cabello.";
            precio = 150;
        } else if (checkedId == R.id.rbtSeis) {
            titulo = "Arreglo de ceja";
            contenido = "Un servicio para dar forma y definir las cejas para un aspecto pulido.";
            precio = 90;
        } else if (checkedId == R.id.rbtSiete) {
            titulo = "Mascarilla facial";
            contenido = "Un tratamiento facial relajante para cuidar y rejuvenecer la piel.";
            precio = 30;
        }

        return new Triple<>(titulo, contenido, precio);
    }

    public boolean isRadioButtonSelected() {
        RadioGroup radioGroup = getView().findViewById(R.id.radioGroupServicios);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        return checkedRadioButtonId != -1;
    }

    public void selectRadioButton() {
        if (getArguments() != null) {
            int defaultServiceId = getArguments().getInt("defaultServiceId", -1);
            if (defaultServiceId != -1) {
                // Establece el RadioButton seleccionado basado en el id proporcionado
                radioGroupServicios.check(defaultServiceId);
            }
        }
    }

}
