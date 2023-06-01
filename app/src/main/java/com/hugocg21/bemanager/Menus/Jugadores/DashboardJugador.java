package com.hugocg21.bemanager.Menus.Jugadores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.hugocg21.bemanager.Menus.Entrenamientos.EntrenamientosFragment;
import com.hugocg21.bemanager.Menus.Estadisticas.EstadisticasEquidoFragment;
import com.hugocg21.bemanager.Menus.Partidos.PartidosFragment;
import com.hugocg21.bemanager.R;
import com.hugocg21.bemanager.databinding.ActivityDashboardJugadorBinding;

public class DashboardJugador extends AppCompatActivity {
    ActivityDashboardJugadorBinding activityDashboardJugadorBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDashboardJugadorBinding = ActivityDashboardJugadorBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardJugadorBinding.getRoot());

        cambiarFragment(new PerfilJugadorFragment());

        activityDashboardJugadorBinding.bottomNavigationViewDashboardJugador.setBackground(null);

        activityDashboardJugadorBinding.bottomNavigationViewDashboardJugador.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottomMenuJugadorDashboardJugador) {
                PerfilJugadorFragment perfilJugadorFragment = new PerfilJugadorFragment(); //Creamos un objeto de tipo JugadoresFragment
                cambiarFragment(perfilJugadorFragment); //Cambiamos el Fragment al de la lista de jugadores
            } else if (item.getItemId() == R.id.bottomMenuPartidosDashboardJugador) {
                PartidosJugadorFragment partidosJugadorFragment = new PartidosJugadorFragment(); //Creamos un objeto de tipo PartidosFragment
                cambiarFragment(partidosJugadorFragment); //Cambiamos el Fragment al de la lista de partidos
            } else if (item.getItemId() == R.id.bottomMenuEstadisticasDashboardJugador) {
                EstadisticasJugadorFragment estadisticasJugadorFragment = new EstadisticasJugadorFragment(); //Creamos un objeto de tipo EntrenamientosFragment
                cambiarFragment(estadisticasJugadorFragment); //Cambiamos el Fragment al de la lista de entrenamientos
            }
            return true;
        });
    }

    private void cambiarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutDashboardJugador, fragment);
        fragmentTransaction.commit();
    }
}