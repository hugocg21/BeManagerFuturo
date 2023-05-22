package com.hugocg21.bemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hugocg21.bemanager.Adaptadores.AdaptadorEquipos;
import com.hugocg21.bemanager.Clases.Equipo;
import com.hugocg21.bemanager.Login.Login;
import com.hugocg21.bemanager.Menus.EquipoDashboard;
import com.hugocg21.bemanager.Menus.NuevoEquipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private RecyclerView recyclerView_equipos;
    private FirebaseFirestore database;
    private FirebaseAuth auth;
    private FirebaseUser usuarioLogueado;
    private CollectionReference collectionReference_equipos, collectionReference_usuario;
    private AdaptadorEquipos adaptadorEquipos;
    private List<Equipo> listaEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        allocateActivityTitle("Dashboard");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        auth = FirebaseAuth.getInstance();
        usuarioLogueado = auth.getCurrentUser();
        String correo = usuarioLogueado.getEmail();

        database = FirebaseFirestore.getInstance();
        collectionReference_usuario = database.collection("Usuarios");
        collectionReference_equipos = collectionReference_usuario.document(Objects.requireNonNull(correo)).collection("Equipos");

        updateUserInfoInNavigationHeader();

        recyclerView_equipos = findViewById(R.id.teamsRecyclerView);
        listaEquipos = new ArrayList<>();
        adaptadorEquipos = new AdaptadorEquipos(listaEquipos, getApplicationContext());/*, new AdaptadorEquipos.OnItemClickListener() {
            @Override
            public void onItemClick(Equipo equipo) {
                startActivity(new Intent(getApplicationContext(), EquipoDashboard.class));
            }
        });*/

        fetchTeams();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_equipos.setLayoutManager(layoutManager);
        recyclerView_equipos.setAdapter(adaptadorEquipos);

        Button addButton = navigationView.findViewById(R.id.addTeamButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NuevoEquipo.class);
                startActivity(intent);
            }
        });

        Button cerrarSesion = findViewById(R.id.buttonCerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchTeams() {
        collectionReference_equipos.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        listaEquipos.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Equipo equipo = documentSnapshot.toObject(Equipo.class);
                            listaEquipos.add(equipo);
                        }

                        adaptadorEquipos.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Dashboard.this, "Failed to fetch teams: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserInfoInNavigationHeader() {
        TextView textView_correo = findViewById(R.id.textViewCorreo);
        TextView textView_usuario = findViewById(R.id.textViewUsuario);

        if (usuarioLogueado != null) {
            String correo = usuarioLogueado.getEmail();

            database.collection("Usuarios").document(correo).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String nombreUsuario = documentSnapshot.getString("nombreUsuario");
                        textView_usuario.setText(nombreUsuario);
                        textView_correo.setText(correo);
                    }
                }
            });

            // Set the user's name and email in the TextViews

        } else {
            // Clear the TextViews if no user is logged in
            textView_usuario.setText("");
            textView_correo.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Add an authentication state listener
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Remove the authentication state listener
        auth.removeAuthStateListener(authStateListener);
    }

    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            // Update user information in the navigation header
            usuarioLogueado = firebaseAuth.getCurrentUser();
            updateUserInfoInNavigationHeader();
        }
    };
}