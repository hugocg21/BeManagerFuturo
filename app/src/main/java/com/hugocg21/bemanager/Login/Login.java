package com.hugocg21.bemanager.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.hugocg21.bemanager.Dashboard;
import com.hugocg21.bemanager.R;

public class Login extends AppCompatActivity {
    TextView textView_recuperarContrasena;
    EditText editText_correoLogin, editText_contrasenaLogin;
    Button button_login, button_crearCuenta;
    ImageButton imageButton_verContrasena, imageButton_ocultarContrasena;
    CheckBox checkBox_recordarSesion;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView_recuperarContrasena = findViewById(R.id.textViewPasswordOlvidada);

        editText_correoLogin = findViewById(R.id.editTextCorreoLogin);
        editText_contrasenaLogin = findViewById(R.id.editTextPasswordLogin);

        imageButton_verContrasena = findViewById(R.id.imageButtonVerPasswordLogin);
        imageButton_ocultarContrasena = findViewById(R.id.imageButtonOcultarPasswordLogin);

        checkBox_recordarSesion = findViewById(R.id.checkBoxRecordarSesionIniciadaLogin);

        button_login = findViewById(R.id.buttonLogin);
        button_crearCuenta = findViewById(R.id.buttonCrearCuentaLogin);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Iniciando sesión en CourtCoach");
        progressDialog.setMessage("Se están cargando tus datos");

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        button_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registro.class));
            }
        });

        imageButton_verContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_contrasenaLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                imageButton_verContrasena.setVisibility(View.GONE);
                imageButton_ocultarContrasena.setVisibility(View.VISIBLE);
            }
        });

        imageButton_ocultarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_contrasenaLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                imageButton_ocultarContrasena.setVisibility(View.GONE);
                imageButton_verContrasena.setVisibility(View.VISIBLE);
            }
        });

        checkBox_recordarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoUsuarioLogin = editText_correoLogin.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("datos", MODE_PRIVATE).edit();

                if (checkBox_recordarSesion.isChecked()) {
                    editor.putBoolean("recordar", true);
                    editor.putString("usuario", correoUsuarioLogin);
                    editor.apply();
                } else {
                    editor.putBoolean("recordar", false);
                    editor.apply();
                }
            }
        });

        textView_recuperarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CambiarContrasena.class);
                startActivity(i);
            }
        });
    }

    private void iniciarSesion() {
        String correo = editText_correoLogin.getText().toString();
        String password = editText_contrasenaLogin.getText().toString();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "No puede haber campos en blanco", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();

            auth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(i);
                    }
                }
            }).addOnFailureListener(e -> {
                try {
                    try {
                        throw e;
                    } catch (FirebaseAuthInvalidUserException invalidEmail) {
                        // El correo electrónico no está registrado en Firebase
                        editText_correoLogin.setText("");
                        editText_contrasenaLogin.setText("");
                        Toast.makeText(this, "El correo electrónico no está registrado en Firebase", Toast.LENGTH_LONG).show();
                    } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                        // La contraseña es incorrecta
                        editText_contrasenaLogin.setText("");
                        Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Error al iniciar sesión: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}