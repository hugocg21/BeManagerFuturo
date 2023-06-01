package com.hugocg21.bemanager.Menus.Jugadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugocg21.bemanager.R;
public class PerfilJugadorFragment extends Fragment {
    TextView textView_nombreJugador, textView_posicionJugador, textView_puntosJugador, textView_rebotesJugador, textView_asistenciasJugador;
    public PerfilJugadorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_jugador, container, false);

        textView_nombreJugador = view.findViewById(R.id.textViewNombrePerfilJugador);
        textView_posicionJugador = view.findViewById(R.id.textViewPosicionPerfilJugador);
        textView_puntosJugador = view.findViewById(R.id.textViewPuntosMediaPerfilJugador);
        textView_rebotesJugador = view.findViewById(R.id.textViewRebotesMediaPerfilJugador);
        textView_asistenciasJugador = view.findViewById(R.id.textViewAsistenciasMediaPerfilJugador);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("jugador", Context.MODE_PRIVATE);
        String nombreJugador = sharedPreferences.getString("nombreJugador", null);

        textView_nombreJugador.setText(nombreJugador);

        return view;
    }
}