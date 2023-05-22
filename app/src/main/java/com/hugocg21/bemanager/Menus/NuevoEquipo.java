package com.hugocg21.bemanager.Menus;

import android.content.Intent;
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
import com.hugocg21.bemanager.Dashboard;
import com.hugocg21.bemanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NuevoEquipo extends AppCompatActivity {
    Button button_nuevoEquipo;
    EditText editText_nombreEquipo, editText_sedeEquipo;
    Spinner spinner_categoriaEquipo;
    FirebaseFirestore database;
    FirebaseAuth auth;
    FirebaseUser usuario;
    CollectionReference collectionReference_equipos, collectionReference_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_equipo);

        editText_nombreEquipo = findViewById(R.id.editTextNombreNuevoEquipo);
        editText_sedeEquipo = findViewById(R.id.editTextSedeNuevoEquipo);

        spinner_categoriaEquipo = findViewById(R.id.spinnerCategoriaNuevoEquipo);

        rellenarSpinner();

        button_nuevoEquipo = findViewById(R.id.buttonAnadirNuevoEquipo);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        usuario = auth.getCurrentUser();
        String correo = Objects.requireNonNull(usuario).getEmail();

        Log.e("Usuario", correo);

        collectionReference_usuario = database.collection("Usuarios");
        collectionReference_equipos = collectionReference_usuario.document(Objects.requireNonNull(correo)).collection("Equipos");

        button_nuevoEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreEquipo = editText_nombreEquipo.getText().toString().trim();
                String sedeEquipo = editText_sedeEquipo.getText().toString().trim();
                String categoriaEquipo = spinner_categoriaEquipo.getSelectedItem().toString().trim();


                if(nombreEquipo.isEmpty() && categoriaEquipo.isEmpty() && sedeEquipo.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else{
                    crearEquipo(nombreEquipo, categoriaEquipo, sedeEquipo, 0);
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                }
            }
        });
    }

    private void crearEquipo(String nombreEquipo, String categoriaEquipo, String sedeEquipo, int numJugadoresEquipo){
        Map<String, Object> equipo = new HashMap<>();
        equipo.put("nombreEquipo", nombreEquipo);
        equipo.put("categoriaEquipo", categoriaEquipo);
        equipo.put("sedeEquipo", sedeEquipo);
        equipo.put("numJugadores", numJugadoresEquipo);

        collectionReference_equipos.document(nombreEquipo).set(equipo).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void rellenarSpinner() {
        ArrayList<String> categorias = new ArrayList<String>();
        categorias.add("Categoría del equipo");
        categorias.add("Cto. España 1ª División Mas (Competiciones federadas)");
        categorias.add("Cto. España 1ª División Fem (Competiciones federadas)");
        categorias.add("1ª Autonómica Mas (Competiciones federadas)");
        categorias.add("1ª Autonómica Fem (Competiciones federadas)");
        categorias.add("2ª Autonómica Mas (Competiciones federadas)");
        categorias.add("2ª Autonómica Fem (Competiciones federadas)");
        categorias.add("3ª Autonómica Mas (Competiciones federadas)");
        categorias.add("Junior Mas 1ª (Competiciones federadas)");
        categorias.add("Junior Mas 2ª (Competiciones federadas)");
        categorias.add("Junior Mas 3ª (Competiciones federadas)");
        categorias.add("Junior Fem 1ª (Competiciones federadas)");
        categorias.add("Junior Fem 2ª (Competiciones federadas)");
        categorias.add("Alevín Mas Copa (Competiciones federadas)");
        categorias.add("Alevín Fem Copa (Competiciones federadas)");
        categorias.add("Benjamín Mas Copa (Competiciones federadas)");
        categorias.add("Benjamín Fem Copa (Competiciones federadas)");

        categorias.add("Cadete Mas 1ª (Juegos deportivos)");
        categorias.add("Cadete Mas 2ª (Juegos deportivos)");
        categorias.add("Cadete Fem 1ª (Juegos deportivos)");
        categorias.add("Cadete Fem 2ª (Juegos deportivos)");
        categorias.add("Infantil Mas 1ª (Juegos deportivos)");
        categorias.add("Infantil Fem 1ª (Juegos deportivos)");
        categorias.add("Infantil Mas 2ª (Juegos deportivos)");
        categorias.add("Infantil Fem 2ª (Juegos deportivos)");
        categorias.add("Alevín Mas (Juegos deportivos)");
        categorias.add("Alevín Fem (Juegos deportivos)");
        categorias.add("Benjamín Mas (Juegos deportivos)");
        categorias.add("Benjamín Fem (Juegos deportivos)");

        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categorias);

        adapterCategorias.setDropDownViewResource(R.layout.custom_spinner_lista);

        spinner_categoriaEquipo.setAdapter(adapterCategorias);
    }
}