package com.hugocg21.bemanager.Menus.Partidos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hugocg21.bemanager.Adaptadores.AdaptadorJugadores;
import com.hugocg21.bemanager.Adaptadores.AdaptadorPartidos;
import com.hugocg21.bemanager.Clases.Jugador;
import com.hugocg21.bemanager.Clases.Partido;
import com.hugocg21.bemanager.Menus.Estadisticas.EstadisticasPartido;
import com.hugocg21.bemanager.Menus.Jugadores.DashboardJugador;
import com.hugocg21.bemanager.R;

import java.util.Objects;

public class PartidosFragment extends Fragment {

    RecyclerView recyclerView_partidos;
    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser usuarioLogueado;
    CollectionReference collectionReference_partidos, collectionReference_equipos, collectionReference_usuario;
    AdaptadorPartidos adaptadorPartidos;
    public PartidosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partidos, container, false);
        recyclerView_partidos = view.findViewById(R.id.recyclerViewPartidosFragmentPartidos);
        recyclerView_partidos.setLayoutManager(new LinearLayoutManager(getContext()));

        //Obtenemos la instancia de la base de datos y de la autenticación de Firebase
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Creamos un String y le asignamos el correo del usuario logueado actual obtenido
        usuarioLogueado = auth.getCurrentUser();
        String correo = Objects.requireNonNull(usuarioLogueado).getEmail();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String nombreEquipo = sharedPreferences.getString("nombreEquipo", null);

        //Inicializamos las referencias a las colecciones
        collectionReference_usuario = database.collection("Usuarios");
        collectionReference_equipos = collectionReference_usuario.document(Objects.requireNonNull(correo)).collection("Equipos");
        collectionReference_partidos = collectionReference_equipos.document(nombreEquipo).collection("Partidos");

        Query query = collectionReference_partidos;

        FirestoreRecyclerOptions<Partido> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Partido>().setQuery(query, Partido.class).build();

        adaptadorPartidos = new AdaptadorPartidos(firestoreRecyclerOptions, new AdaptadorPartidos.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Partido partido = documentSnapshot.toObject(Partido.class); // Corrección: utiliza toObject() para obtener el objeto Partido
                String nombreRival = partido.getRivalPartido(); // Obtén el nombre del rival

                SharedPreferences.Editor editor = getContext().getSharedPreferences("partido", Context.MODE_PRIVATE).edit();
                editor.putString("nombreRival", nombreRival);
                editor.apply();

                startActivity(new Intent(getContext(), EstadisticasPartido.class));
            }

            /*public void onDeleteClick(DocumentSnapshot documentSnapshot, int position) {
                String idJugador = documentSnapshot.getId();
                DocumentReference documentReference_jugador = collectionReference_partidos.document(idJugador);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Eliminar jugador");
                builder.setMessage("¿Está seguro que desea eliminar a este jugador?");
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        documentReference_jugador.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Jugador eliminado correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Ha habido un error eliminando el jugador", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancelar", null);
                builder.create().show();
            }*/
        });

        adaptadorPartidos.notifyDataSetChanged();
        recyclerView_partidos.setAdapter(adaptadorPartidos);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adaptadorPartidos.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adaptadorPartidos.stopListening();
    }
}