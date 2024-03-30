package com.example.proyectointegradorv1.fragments.reserve;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout; // Agrega esta línea

import com.example.proyectointegradorv1.R;

public class ConfirmInformationResFragment extends Fragment{

    public ConfirmInformationResFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_information_res, container, false);

        // Aquí colocas la lógica para desaparecer frameContainer y ajustar CardInferior
        View frameContainer = getActivity().findViewById(R.id.frameContainer);
        View cardInferior = getActivity().findViewById(R.id.CardInferior);

        // Desaparecer frameContainer
        frameContainer.setVisibility(View.GONE);

        // Ajustar CardInferior
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) cardInferior.getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        layoutParams.topMargin = marginInDp;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.UNSET;
        layoutParams.topToBottom = R.id.barCardView;
        cardInferior.setLayoutParams(layoutParams);

        return view;
    }
}