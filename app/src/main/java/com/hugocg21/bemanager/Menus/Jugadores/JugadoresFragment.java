package com.hugocg21.bemanager.Menus.Jugadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hugocg21.bemanager.Adaptadores.AdaptadorJugadores;
import com.hugocg21.bemanager.Clases.Jugador;
import com.hugocg21.bemanager.Dashboard;
import com.hugocg21.bemanager.R;

import java.util.Objects;

public class JugadoresFragment extends Fragment {
    RecyclerView recyclerView_jugadores;
    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser usuarioLogueado;
    CollectionReference collectionReference_jugadores, collectionReference_equipos, collectionReference_usuario;
    AdaptadorJugadores adaptadorJugadores;

    public JugadoresFragment() {
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jugadores, container, false);
        recyclerView_jugadores = view.findViewById(R.id.recyclerViewJugadoresFragmentJugadores);

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
        collectionReference_jugadores = collectionReference_equipos.document(nombreEquipo).collection("Jugadores");

        Query query = collectionReference_jugadores;

        FirestoreRecyclerOptions<Jugador> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Jugador>().setQuery(query, Jugador.class).build();

        adaptadorJugadores = new AdaptadorJugadores(firestoreRecyclerOptions, new AdaptadorJugadores.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                /*Jugador jugador = documentSnapshot.toObject(Jugador.class);
                Intent i = new Intent(getContext(), Dashboard.class);
                startActivity(i);*/
            }
        });

        adaptadorJugadores.notifyDataSetChanged();
        recyclerView_jugadores.setAdapter(adaptadorJugadores);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adaptadorJugadores.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adaptadorJugadores.stopListening();
    }
}