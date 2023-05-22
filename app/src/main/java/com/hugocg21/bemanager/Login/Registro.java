package com.hugocg21.bemanager.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hugocg21.bemanager.Dashboard;
import com.hugocg21.bemanager.R;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    EditText editText_correoRegistro, editText_usuarioRegistro, editText_contrasenaRegistro, editText_repetirContrasenaRegistro;
    Button button_registro, button_volver;
    ImageButton imageButton_verContrasena, imageButton_ocultarContrasena, imageButton_verRepetirContrasena, imageButton_ocultarRepetirContrasena;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        editText_correoRegistro = findViewById(R.id.editTextCorreoRegistro);
        editText_usuarioRegistro = findViewById(R.id.editTextUsuarioRegistro);
        editText_contrasenaRegistro = findViewById(R.id.editTextPasswordRegistro);
        editText_repetirContrasenaRegistro = findViewById(R.id.editTextRepetirPasswordRegistro);

        button_registro = findViewById(R.id.buttonRegistro);
        button_volver = findViewById(R.id.buttonVolverRegistro);

        imageButton_verContrasena = findViewById(R.id.imageButtonVerPasswordRegistro);
        imageButton_ocultarContrasena = findViewById(R.id.imageButtonOcultarPasswordRegistro);
        imageButton_verRepetirContrasena = findViewById(R.id.imageButtonVerRepetirPasswordRegistro);
        imageButton_ocultarRepetirContrasena = findViewById(R.id.imageButtonOcultarRepetirContrasenaRegistro);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creando tu cuenta en CourtCoach");
        progressDialog.setMessage("Tu cuenta se está creando");

        imageButton_verContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_contrasenaRegistro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                imageButton_verContrasena.setVisibility(View.GONE);
                imageButton_ocultarContrasena.setVisibility(View.VISIBLE);
            }
        });

        imageButton_ocultarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_contrasenaRegistro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                imageButton_ocultarContrasena.setVisibility(View.GONE);
                imageButton_verContrasena.setVisibility(View.VISIBLE);
            }
        });

        imageButton_verRepetirContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_repetirContrasenaRegistro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                imageButton_verRepetirContrasena.setVisibility(View.GONE);
                imageButton_ocultarRepetirContrasena.setVisibility(View.VISIBLE);
            }
        });

        imageButton_ocultarRepetirContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_repetirContrasenaRegistro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                imageButton_ocultarRepetirContrasena.setVisibility(View.GONE);
                imageButton_verRepetirContrasena.setVisibility(View.VISIBLE);
            }
        });

        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarse();
            }
        });

        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void registrarse(){
        String correo = editText_correoRegistro.getText().toString();
        String usuario = editText_usuarioRegistro.getText().toString();
        String password = editText_contrasenaRegistro.getText().toString();
        String repetirPassword = editText_repetirContrasenaRegistro.getText().toString();

        if (correo.isEmpty() && usuario.isEmpty() && password.isEmpty() && repetirPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No puede haber campos en blanco", Toast.LENGTH_SHORT).show();
        } else {
            if (correo.contains(" ") || usuario.contains(" ") || password.contains(" ") || repetirPassword.contains(" ")) {
                Toast.makeText(getApplicationContext(), "Ningún campo puede contener espacios en blanco", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(repetirPassword)) {
                    progressDialog.show();

                    auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Map<String, Object> Usuario = new HashMap<>();
                                Usuario.put("correoElectronico", correo);
                                Usuario.put("nombreUsuario", usuario);
                                Usuario.put("password", password);

                                database.collection("Usuarios").document(correo).set(Usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        String id = database.collection("Usuarios").document(correo).getId();
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + id);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });

                                Toast.makeText(Registro.this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(i);
                            }
                        }
                    }).addOnFailureListener(e -> {
                        try {
                            try {
                                throw e;
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Toast.makeText(getApplicationContext(), "La contraseña es demasiado débil", Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthInvalidCredentialsException invalidEmail) {
                                Toast.makeText(getApplicationContext(), "El correo electrónico es inválido", Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthUserCollisionException emailExists) {
                                Toast.makeText(getApplicationContext(), "El correo electrónico ya está registrado", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Error al crear usuario: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    }
}