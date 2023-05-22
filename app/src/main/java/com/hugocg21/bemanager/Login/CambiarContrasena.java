package com.hugocg21.bemanager.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hugocg21.bemanager.R;

public class CambiarContrasena extends AppCompatActivity {
    Button button_enviarCodigo, button_volver;
    EditText editText_correo;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        editText_correo = findViewById(R.id.editTextCorreoRecuperarPassword);

        button_enviarCodigo = findViewById(R.id.buttonRecuperarPassword);
        button_volver = findViewById(R.id.buttonVolverRecuperarPassword);

        auth = FirebaseAuth.getInstance();

        button_enviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoRecuperar = editText_correo.getText().toString();

                auth.sendPasswordResetEmail(correoRecuperar).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Correo enviado correctamente a " + correoRecuperar, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Ha habido un problema enviando el correo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}