package com.hugocg21.bemanager.Menus;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hugocg21.bemanager.Menus.Entrenamientos.EntrenamientosFragment;
import com.hugocg21.bemanager.Menus.Estadisticas.EstadisticasFragment;
import com.hugocg21.bemanager.Menus.Jugadores.JugadoresFragment;
import com.hugocg21.bemanager.Menus.Jugadores.NuevoJugador;
import com.hugocg21.bemanager.Menus.Partidos.PartidosFragment;
import com.hugocg21.bemanager.R;
import com.hugocg21.bemanager.databinding.ActivityEquipoDashboardBinding;

public class EquipoDashboard extends AppCompatActivity {
    ActivityEquipoDashboardBinding binding; //Creamos una variable binding para poder bindear los datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bindeamos todos los datos y lo inflamos
        binding = ActivityEquipoDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cambiarFragment(new JugadoresFragment());

        //Bindeamos el BottomNavigationView
        binding.bottomNavigationViewEquipoDasboard.setBackground(null);

        SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
        String equipo = sharedPreferences.getString("nombreEquipo", null);

        // Crea el Bundle con el nombre del equipo
        Bundle bundle = new Bundle();
        bundle.putString("nombreEquipo", equipo);

        //Método para abrir cada Fragment dependiendo de que item del menú se clickee
        binding.bottomNavigationViewEquipoDasboard.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottomMenuJugadores) {
                JugadoresFragment jugadoresFragment = new JugadoresFragment(); //Creamos un objeto de tipo JugadoresFragment
                jugadoresFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(jugadoresFragment); //Cambiamos el Fragment al de la lista de jugadores
            } else if (item.getItemId() == R.id.bottomMenuPartidos) {
                PartidosFragment partidosFragment = new PartidosFragment(); //Creamos un objeto de tipo PartidosFragment
                partidosFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(partidosFragment); //Cambiamos el Fragment al de la lista de partidos
            } else if (item.getItemId() == R.id.bottomMenuEntrenamientos) {
                EntrenamientosFragment entrenamientosFragment = new EntrenamientosFragment(); //Creamos un objeto de tipo EntrenamientosFragment
                entrenamientosFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(entrenamientosFragment); //Cambiamos el Fragment al de la lista de entrenamientos
            } else if (item.getItemId() == R.id.bottomMenuEstadisticas) {
                EstadisticasFragment estadisticasFragment = new EstadisticasFragment(); //Creamos un objeto de tipo EstadisticasFragment
                estadisticasFragment.setArguments(bundle); //Guardamos el bundle con el nombre del equipo
                cambiarFragment(estadisticasFragment); //Cambiamos el Fragment al de las estadísticas
            }
            return true;
        });

        //Método al hacer click en el Button de añadir del BottomNavigationView
        binding.floatingActionButtonDesplegableEquipoDashboard.setOnClickListener(new View.OnClickListener() {
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

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Create a short is Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        //Método al hacer click en el LinearLayout de crear un nuevo entrenamiento
        nuevoEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Go live is Clicked", Toast.LENGTH_SHORT).show();

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