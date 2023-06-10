package com.hugocg21.bemanager.Menus.Estadisticas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hugocg21.bemanager.R;

import java.util.Objects;

public class EstadisticasPartido extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser usuarioLogueado;
    CollectionReference collectionReference_estadisticas, collectionReference_partidos, collectionReference_equipos, collectionReference_usuario;
    TextView textView_nombreRival, textView_fechaPartido, textView_horaPartido, textView_lugarPartido, textView_resultadoPartido, textView_equipoLocal, textView_equipoVisitante,
            textView_puntosLocal, textView_puntosVisitante;
    TextView textView_puntos, textView_rebotesOfensivos, textView_rebotesDefensivos, textView_rebotes, textView_asistencias, textView_robos, textView_tapones, textView_perdidas, textView_faltasRecibidas,
        textView_faltasCometidas, textView_triples, textView_porcentajeTriples, textView_mediaDistancia, textView_porcentajeMediaDistancia, textView_tirosLibres, textView_porcentajeTirosLibres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_partido);

        textView_nombreRival = findViewById(R.id.textViewRivalPartido);
        textView_fechaPartido = findViewById(R.id.textViewFechaPartido);
        textView_horaPartido = findViewById(R.id.textViewHoraPartido);
        textView_lugarPartido = findViewById(R.id.textViewLugarPartido);
        textView_resultadoPartido = findViewById(R.id.textViewResultadoPartido);
        textView_equipoLocal = findViewById(R.id.textViewEquipoLocal);
        textView_equipoVisitante = findViewById(R.id.textViewEquipoVisitante);
        textView_puntosLocal = findViewById(R.id.textViewPuntosLocal);
        textView_puntosVisitante = findViewById(R.id.textViewPuntosVisitante);

        textView_puntos = findViewById(R.id.textViewPuntosEquipo);
        textView_rebotesOfensivos = findViewById(R.id.textViewRebotesOfensivosEquipo);
        textView_rebotesDefensivos = findViewById(R.id.textViewRebotesDefensivosEquipo);
        textView_rebotes = findViewById(R.id.textViewRebotesEquipo);
        textView_asistencias = findViewById(R.id.textViewAsistenciasEquipo);
        textView_robos = findViewById(R.id.textViewRobosEquipo);
        textView_tapones = findViewById(R.id.textViewTaponesEquipo);
        textView_perdidas = findViewById(R.id.textViewPerdidasEquipo);
        textView_faltasRecibidas = findViewById(R.id.textViewFaltasRecibidasEquipo);
        textView_faltasCometidas = findViewById(R.id.textViewFaltasCometidasEquipo);
        textView_triples = findViewById(R.id.textViewTriplesEquipo);
        textView_porcentajeTriples = findViewById(R.id.textViewPorcentajeTriplesEquipo);
        textView_mediaDistancia = findViewById(R.id.textViewMediaDistanciaEquipo);
        textView_porcentajeMediaDistancia = findViewById(R.id.textViewPorcentajeMediaDistanciaEquipo);
        textView_tirosLibres = findViewById(R.id.textViewTirosLibresEquipo);
        textView_porcentajeTirosLibres = findViewById(R.id.textViewPorcentajeTirosLibresEquipo);

        //Obtenemos la instancia de la base de datos y de la autenticación de Firebase
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Creamos un String y le asignamos el correo del usuario logueado actual obtenido
        usuarioLogueado = auth.getCurrentUser();
        String correo = Objects.requireNonNull(usuarioLogueado).getEmail();

        SharedPreferences sharedPreferencesEquipo = getApplicationContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String nombreEquipo = sharedPreferencesEquipo.getString("nombreEquipo", null);

        SharedPreferences sharedPreferencesRival = getApplicationContext().getSharedPreferences("partido", Context.MODE_PRIVATE);
        String nombreRival = sharedPreferencesRival.getString("nombreRival", null);

        //Inicializamos las referencias a las colecciones
        collectionReference_usuario = database.collection("Usuarios");
        collectionReference_equipos = collectionReference_usuario.document(Objects.requireNonNull(correo)).collection("Equipos");
        collectionReference_partidos = collectionReference_equipos.document(nombreEquipo).collection("Partidos");
        collectionReference_estadisticas = collectionReference_partidos.document().collection("Estadisticas");

        collectionReference_partidos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String textViewRival = textView_nombreRival.getText().toString();

                        textView_nombreRival.setText(textViewRival + " " + document.getString("rivalPartido"));
                        textView_fechaPartido.setText(document.getString("fechaPartido"));
                        textView_horaPartido.setText(document.getString("horaPartido"));
                        textView_lugarPartido.setText(document.getString("sedePartido"));
                        textView_equipoLocal.setText(nombreEquipo);
                        textView_equipoVisitante.setText(nombreRival);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        collectionReference_estadisticas.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        textView_puntosLocal.setText(document.getString("puntosEquipo"));
                        textView_puntosVisitante.setText(document.getString("puntosVisitante"));

                        textView_puntos.setText(document.getString("puntosEquipo"));
                        textView_rebotesOfensivos.setText(document.getString("rebotesOfensivos"));
                        textView_rebotesDefensivos.setText(document.getString("rebotesDefensivos"));
                        textView_rebotes.setText(document.getString("rebotesEquipo"));
                        textView_asistencias.setText(document.getString("asistenciasEquipo"));
                        textView_robos.setText(document.getString("robosEquipo"));
                        textView_tapones.setText(document.getString("taponesEquipo"));
                        textView_perdidas.setText(document.getString("perdidasEquipo"));
                        textView_faltasRecibidas.setText(document.getString("faltasRecibidasEquipo"));
                        textView_faltasCometidas.setText(document.getString("faltasCometidasEquipo"));
                        textView_triples.setText(document.getString("triplesEquipo"));
                        textView_porcentajeTriples.setText(document.getString("porcentajeTriplesEquipo"));
                        textView_mediaDistancia.setText(document.getString("mediaDistanciaEquipo"));
                        textView_porcentajeMediaDistancia.setText(document.getString("porcentajeMediaDistanciaEquipo"));
                        textView_tirosLibres.setText(document.getString("tirosLibresEquipo"));
                        textView_porcentajeTirosLibres.setText(document.getString("porcentajeTirosLibresEquipo"));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}