package com.hugocg21.bemanager.Menus.Jugadores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugocg21.bemanager.R;
public class PartidosJugadorFragment extends Fragment {
    public PartidosJugadorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partidos_jugador, container, false);

        TextView textView_nombreJugador = view.findViewById(R.id.textViewNombrePartidos);

        textView_nombreJugador.setText("Pepito");

        return view;
    }
}