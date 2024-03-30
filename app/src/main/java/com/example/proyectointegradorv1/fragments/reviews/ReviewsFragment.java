package com.example.proyectointegradorv1.fragments.reviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.fragments.history.HistoryFragment;
import com.example.proyectointegradorv1.fragments.reservations.ChooseServiceFragment;
import com.example.proyectointegradorv1.fragments.reservations.ChooseTimeFragment;
import com.example.proyectointegradorv1.fragments.reservations.ConfirmInformationFragment;
import com.example.proyectointegradorv1.fragments.reservations.UserInformationFragment;

public class ReviewsFragment extends Fragment {

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }
}