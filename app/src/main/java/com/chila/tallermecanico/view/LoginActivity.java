package com.chila.tallermecanico.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chila.tallermecanico.Firestore.Database;
import com.chila.tallermecanico.R;
import com.chila.tallermecanico.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "CustomAuthActivity";

    TextView tvUsuario, tvcontrasena, tvRecuperoContrasena;
    Button btInicioSesion, btInicioSesionGoogle, btRegistrarse;
    ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        tvUsuario = findViewById(R.id.login_username);
        tvcontrasena = findViewById(R.id.login_password);
        tvRecuperoContrasena = findViewById(R.id.login_recuperar_contraseña);
        btInicioSesion = findViewById(R.id.login_singin);
        btRegistrarse = findViewById(R.id.login_registrar);
        btInicioSesionGoogle = findViewById(R.id.login_google);
        progressBar = findViewById(R.id.login_progressbar);


        iniciarMain();


        //Listeners
        btInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = tvUsuario.getText().toString();
                String password = tvcontrasena.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "login sucesfull)");
                                    progressBar.setVisibility(View.INVISIBLE);
                                    iniciarMain();
                                } else {
                                    Log.w(TAG, "Login error");
                                    Toast toastError = Toast.makeText(LoginActivity.this, "Error de inicio de sesión", Toast.LENGTH_LONG);
                                    toastError.show();
                                }
                            }
                        });
            }
        });

        btRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = tvUsuario.getText().toString();
                String password = tvcontrasena.getText().toString();
                crearUsuario(email, password);

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void iniciarMain() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void crearUsuario(String usuario, String contraseña) {
        mAuth.createUserWithEmailAndPassword(usuario, contraseña)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            progressBar.setVisibility(View.INVISIBLE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            crearUsuarioFirestore(user.getUid());
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                });
    }

    private void crearUsuarioFirestore(final String uid) {
        Database db = Database.getInstance();
        Usuario usuario = new Usuario(uid, tvUsuario.getText().toString());
        db.crearUsuario(usuario);


    }


}


