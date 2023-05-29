package com.hugocg21.bemanager.Menus.Jugadores;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hugocg21.bemanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NuevoJugador extends AppCompatActivity {
    Button button_nuevoJugador;
    EditText editText_nombreJugador, editText_apellidosJugador, editText_dorsalJugador;
    Spinner spinner_equipoJugador, spinner_posicionJugador;
    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser usuarioLogueado;
    CollectionReference collectionReference_jugadores, collectionReference_equipos, collectionReference_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_jugador);

        editText_nombreJugador = findViewById(R.id.editTextNombreNuevoJugador);
        editText_apellidosJugador = findViewById(R.id.editTextApellidosNuevoJugador);
        spinner_equipoJugador = findViewById(R.id.spinnerEquipoNuevoJugador);
        spinner_posicionJugador = findViewById(R.id.spinnerPosicionNuevoJugador);
        editText_dorsalJugador = findViewById(R.id.editTextDorsalNuevoJugador);

        button_nuevoJugador = findViewById(R.id.buttonAnadirJugadorNuevoJugador);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        usuarioLogueado = auth.getCurrentUser();
        String correo = Objects.requireNonNull(usuarioLogueado).getEmail();

        Log.e("Usuario", correo);

        collectionReference_usuario = database.collection("Usuarios");
        collectionReference_equipos = collectionReference_usuario.document(Objects.requireNonNull(correo)).collection("Equipos");

        rellenarSpinnerPosicion();
        rellenarSpinnerEquipos();

        button_nuevoJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
                String equipo = sharedPreferences.getString("nombreEquipo", null);

                collectionReference_jugadores = collectionReference_equipos.document(equipo).collection("Jugadores");

                String nombreJugador = editText_nombreJugador.getText().toString().trim();
                String apellidosJugador = editText_apellidosJugador.getText().toString().trim();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
                adapter.add(equipo);

                spinner_equipoJugador.setAdapter(adapter);

                String equipoJugador = spinner_equipoJugador.getSelectedItem().toString().trim();
                String posicionJugador = spinner_posicionJugador.getSelectedItem().toString().trim();
                String dorsalJugador = editText_dorsalJugador.getText().toString().trim();

                if (nombreJugador.isEmpty() || apellidosJugador.isEmpty() || equipoJugador.isEmpty() || posicionJugador.isEmpty() || dorsalJugador.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    crearJugador(nombreJugador, apellidosJugador, equipoJugador, posicionJugador, dorsalJugador);
                    finish();
                }
            }
        });
    }

    private void crearJugador(String nombreJugador, String apellidosJugador, String equipoJugador, String posicionJugador, String dorsalJugador) {
        Map<String, Object> equipo = new HashMap<>();
        equipo.put("nombreJugador", nombreJugador);
        equipo.put("apellidosJugador", apellidosJugador);
        equipo.put("equipoJugador", equipoJugador);
        equipo.put("posicionJugador", posicionJugador);
        equipo.put("dorsalJugador", dorsalJugador);

        collectionReference_jugadores.document(nombreJugador + " " + apellidosJugador).set(equipo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rellenarSpinnerPosicion() {
        ArrayList<String> posiciones = new ArrayList<String>();
        posiciones.add("Posición del jugador");
        posiciones.add("Base");
        posiciones.add("Escolta");
        posiciones.add("Alero");
        posiciones.add("Ala-pivot");
        posiciones.add("Pivot");

        ArrayAdapter<String> adapterPosiciones = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, posiciones);

        adapterPosiciones.setDropDownViewResource(R.layout.custom_spinner_lista);

        spinner_posicionJugador.setAdapter(adapterPosiciones);
    }

    private void rellenarSpinnerEquipos() {
        ArrayList<String> equipos = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
        String equipo = sharedPreferences.getString("nombreEquipo", null);

        equipos.add(equipo);

        ArrayAdapter<String> adapterEquipos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, equipos);

        adapterEquipos.setDropDownViewResource(R.layout.custom_spinner_lista);

        spinner_equipoJugador.setAdapter(adapterEquipos);
    }
}