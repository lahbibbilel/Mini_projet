package com.example.mini_projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
private FirebaseAuth auth;
private EditText signupemail,signuppassword;
private Button signupbutton;
private TextView login_RedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        signupemail = findViewById(R.id.signup_email);
        signuppassword = findViewById(R.id.signup_password);
        signupbutton = findViewById(R.id.signup_button);
        login_RedirectText = findViewById(R.id.LoginRedirectText);
        // Définition d'un listener sur le bouton de l'interface utilisateur pour l'inscription.
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération des champs de l'interface utilisateur.
                String user = signupemail.getText().toString().trim();
                String pass = signuppassword.getText().toString().trim();
                // Vérification des champs.
                if (user.isEmpty()) {
                    signupemail.setError("email cannot be empty !!");
                }
                if (pass.isEmpty()) {
                    signuppassword.setError("password cannot be empty !!");
                } else {
                    // Création de l'utilisateur sur Firebase.
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Si la création est réussie, affichage d'un message et redirection vers l'activité de connexion.
                                Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else {
                                // Sinon, affichage d'un message d'erreur.
                                Toast.makeText(SignUpActivity.this, "signUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Définition d'un listener sur le champ de l'interface utilisateur pour rediriger vers l'activité de connexion.
        login_RedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }}