package com.hugocg21.bemanager.Menus.Partidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hugocg21.bemanager.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NuevoPartido extends AppCompatActivity {
    Button button_nuevoPartido;
    EditText editText_rival, editText_sede, editText_fechaPartido, editText_horaPartido;
    RadioButton radioButton_local, radioButton_visitante;
    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser usuarioLogueado;
    CollectionReference collectionReference_partidos, collectionReference_equipos, collectionReference_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_partido);

        //Inicializamos los EditTexts
        editText_rival = findViewById(R.id.editTextRivalPartido);
        editText_sede = findViewById(R.id.editTextPabellonPartido);
        editText_fechaPartido = findViewById(R.id.editTextFechaPartido);
        editText_horaPartido = findViewById(R.id.editTextHoraPartido);

        //Inicializamos los RadioButtons
        radioButton_local = findViewById(R.id.radiobuttonLocalPartido);
        radioButton_visitante = findViewById(R.id.radiobuttonVisitantePartido);

        //Inicializamos el Button
        button_nuevoPartido = findViewById(R.id.buttonAnadirNuevoPartido);

        //Obtenemos la instancia de la base de datos y de la autenticación de Firebase
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Creamos un String y le asignamos el correo del usuario logueado actual obtenido
        usuarioLogueado = auth.getCurrentUser();
        String correo = Objects.requireNonNull(usuarioLogueado).getEmail();

        //Inicializamos las referencias a las colecciones
        collectionReference_usuario = database.collection("Usuarios");
        collectionReference_equipos = collectionReference_usuario.document(Objects.requireNonNull(correo)).collection("Equipos");

        //Método al hacer click en el Button de añadir un nuevo equipo
        button_nuevoPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
                String equipo = sharedPreferences.getString("nombreEquipo", null);

                collectionReference_partidos = collectionReference_equipos.document(equipo).collection("Partidos");

                String rivalPartido = editText_rival.getText().toString().trim();

                String rolPartido;
                if (radioButton_local.isChecked()){
                    rolPartido = "Local";
                } else{
                    rolPartido = "Visitante";
                }

                String sedePartido = editText_sede.getText().toString().trim();
                String fechaPartido = editText_fechaPartido.getText().toString().trim();
                String horaPartido = editText_horaPartido.getText().toString().trim();

                if (rivalPartido.isEmpty() || rolPartido.length() == 0 || sedePartido.isEmpty() || fechaPartido.isEmpty() || horaPartido.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
                } else {
                    crearPartido(rivalPartido, rolPartido, sedePartido, fechaPartido, horaPartido);
                    finish();
                }
            }
        });
    }

    private void crearPartido(String rivalPartido, String rolPartido, String sedePartido, String fechaPartido, String horaPartido) {
        Map<String, Object> partido = new HashMap<>();
        partido.put("rivalPartido", rivalPartido);
        partido.put("rolPartido", rolPartido);
        partido.put("sedePartido", sedePartido);
        partido.put("fechaPartido", fechaPartido);
        partido.put("horaPartido", horaPartido);

        collectionReference_partidos.document(rivalPartido + " " + fechaPartido).set(partido).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Partido añadido correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar el partido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}