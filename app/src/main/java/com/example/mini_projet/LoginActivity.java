package com.example.mini_projet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText loginemail,loginpassword;
    private Button loginbutton;
    private TextView SignUp_RedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialisation de l'objet Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Récupération des éléments de l'interface graphique
        loginemail = findViewById(R.id.login_email);
        loginpassword = findViewById(R.id.login_password);
        loginbutton = findViewById(R.id.login_button);
        SignUp_RedirectText = findViewById(R.id.SignUpRedirectText);

        // Définition de l'action à effectuer lors du clic sur le bouton de connexion
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginemail.getText().toString();
                String pass = loginpassword.getText().toString();

                // Vérification de la validité de l'email
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    // Vérification de la validité du mot de passe
                    if (!pass.isEmpty()) {

                        // Authentification de l'utilisateur avec l'email et le mot de passe saisis
                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // Si l'authentification est réussie, afficher un message de succès, ouvrir l'activité principale et terminer l'activité de connexion
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Si l'authentification échoue, afficher un message d'erreur
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Si le mot de passe est vide, afficher un message d'erreur sous le champ de mot de passe
                        loginpassword.setError("Password cannot be empty");
                    }
                } else if (email.isEmpty()) {
                    // Si l'email est vide, afficher un message d'erreur sous le champ d'email
                    loginemail.setError("email cannot be empty ");
                } else {
                    // Si l'email n'est pas valide, afficher un message d'erreur sous le champ d'email
                    loginemail.setError("please enter valid email");
                }
            }
        });

        // Définition de l'action à effectuer lors du clic sur le texte de redirection vers l'activité d'inscription
        SignUp_RedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ouvrir l'activité d'inscription
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }}