package com.hugocg21.bemanager.Menus.Equipo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hugocg21.bemanager.Menus.Entrenamientos.EntrenamientosFragment;
import com.hugocg21.bemanager.Menus.Entrenamientos.NuevoEntrenamiento;
import com.hugocg21.bemanager.Menus.Estadisticas.EstadisticasEquidoFragment;
import com.hugocg21.bemanager.Menus.Jugadores.NuevoJugador;
import com.hugocg21.bemanager.Menus.Partidos.NuevoPartido;
import com.hugocg21.bemanager.Menus.Partidos.PartidosFragment;
import com.hugocg21.bemanager.R;
import com.hugocg21.bemanager.databinding.ActivityDashboardEquipoBinding;

public class DashboardEquipo extends AppCompatActivity {
    ActivityDashboardEquipoBinding activityDashboardEquipoBinding; //Creamos una variable binding para poder bindear los datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bindeamos todos los datos y lo inflamos
        activityDashboardEquipoBinding = ActivityDashboardEquipoBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardEquipoBinding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
        String equipo = sharedPreferences.getString("nombreEquipo", null);

        // Crea el Bundle con el nombre del equipo
        Bundle bundle = new Bundle();
        bundle.putString("nombreEquipo", equipo);

        cambiarFragment(new JugadoresFragment());

        //Bindeamos el BottomNavigationView
        activityDashboardEquipoBinding.bottomNavigationViewEquipoDasboard.setBackground(null);

        //Método para abrir cada Fragment dependiendo de que item del menú se clickee
        activityDashboardEquipoBinding.bottomNavigationViewEquipoDasboard.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottomMenuJugadoresDashboardEquipo) {
                JugadoresFragment jugadoresFragment = new JugadoresFragment(); //Creamos un objeto de tipo JugadoresFragment
                jugadoresFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(jugadoresFragment); //Cambiamos el Fragment al de la lista de jugadores
            } else if (item.getItemId() == R.id.bottomMenuPartidosDashboardEquipo) {
                PartidosFragment partidosFragment = new PartidosFragment(); //Creamos un objeto de tipo PartidosFragment
                partidosFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(partidosFragment); //Cambiamos el Fragment al de la lista de partidos
            } else if (item.getItemId() == R.id.bottomMenuEntrenamientosDashboardEquipo) {
                EntrenamientosFragment entrenamientosFragment = new EntrenamientosFragment(); //Creamos un objeto de tipo EntrenamientosFragment
                entrenamientosFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(entrenamientosFragment); //Cambiamos el Fragment al de la lista de entrenamientos
            } else if (item.getItemId() == R.id.bottomMenuEstadisticasDashboardEquipo) {
                EstadisticasEquidoFragment estadisticasEquidoFragment = new EstadisticasEquidoFragment(); //Creamos un objeto de tipo EstadisticasFragment
                estadisticasEquidoFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(estadisticasEquidoFragment); //Cambiamos el Fragment al de las estadísticas
            }
            return true;
        });

        //Método al hacer click en el Button de añadir del BottomNavigationView
        activityDashboardEquipoBinding.floatingActionButtonDesplegableEquipoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog(); //Llamammos al método para mostrar el Dialog desplegable inferior
            }
        });

    }

    //Método para cambiar los Fragments de la actividad
    private void cambiarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutEquipoDashboard, fragment);
        fragmentTransaction.commit();
    }

    //Método para mostrar
    private void showBottomDialog() {
        //Creamos un objeto de tipo Dialog y lo adaptamos a la View
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        //Creamos los LinearLayout y el ImageView del Dialog, y los asignamos, que funcionan a modo de Buttons
        LinearLayout nuevoJugador = dialog.findViewById(R.id.linearLayoutNuevoJugadorDesplegable);
        LinearLayout nuevoPartido = dialog.findViewById(R.id.linearLayoutNuevoPartidoDesplegable);
        LinearLayout nuevoEntrenamiento = dialog.findViewById(R.id.linearLayoutNuevoEntrenaminetoDesplegable);
        ImageView cerrarDialog = dialog.findViewById(R.id.imageViewCerrarDesplegable);

        //Método al hacer click en el LinearLayout de crear un nuevo jugador
        nuevoJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NuevoJugador.class)); //Creamos un Intent y abrimos la actividad de crear un nuevo jugador
                dialog.dismiss(); //Cerramos el Dialog del menú inferior
            }
        });

        //Método al hacer click en el LinearLayout de crear un nuevo partido
        nuevoPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NuevoPartido.class));
                dialog.dismiss();
            }
        });

        //Método al hacer click en el LinearLayout de crear un nuevo entrenamiento
        nuevoEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NuevoEntrenamiento.class));
                dialog.dismiss();
            }
        });

        //Método al hacer click en el ImageView de cerrar el Dialog
        cerrarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cerramos el Dialog
                dialog.dismiss();
            }
        });

        //Mostramos el Dialog en pantalla y lo modificamos a nuestro gusto
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Le ajustamos las dimensiones
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //Le cambiamos el color de fondo
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Le aplicamos las animaciones
        dialog.getWindow().setGravity(Gravity.BOTTOM); //Lo iniciamos en la parte inferior de la pantalla

    }
}