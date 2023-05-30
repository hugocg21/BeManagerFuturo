package com.hugocg21.bemanager.Menus.Jugadores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugocg21.bemanager.R;

public class EstadisticasJugadorFragment extends Fragment {
    public EstadisticasJugadorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estadisticas_jugador, container, false);

        Bundle bundle = getArguments();
        if (bundle != null){
            String nombreJugador = bundle.getString("nombreJugador");

            TextView textView_nombreJugador = view.findViewById(R.id.textViewNombreJugadorEstadisticasJugador);
            textView_nombreJugador.setText(nombreJugador);
        }

        return view;
    }
}