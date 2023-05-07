package com.example.mini_projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mini_projet.databinding.ActivityAuthClientBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthClient extends AppCompatActivity {
    ActivityAuthClientBinding binding;
    String name, email, password;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Créer l'interface utilisateur avec le layout ActivityAuthClientBinding
        binding = ActivityAuthClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Créer une référence à la base de données Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Gérer le clic sur le bouton "Se connecter"
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupérer les champs email et mot de passe
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                // Appeler la méthode login
                login();
            }
        });

        // Gérer le clic sur le bouton "S'inscrire"
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupérer les champs nom, email et mot de passe
                name = binding.name.getText().toString();
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();
                // Appeler la méthode SignUp
                SignUp();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Vérifier si l'utilisateur est déjà connecté et rediriger vers la page d'accueil le cas échéant
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(AuthClient.this, HomeActivity.class));
            finish();
        }
    }

    private void login() {
        // Authentifier l'utilisateur avec Firebase
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Rediriger l'utilisateur vers la page d'accueil en cas de succès
                        startActivity(new Intent(AuthClient.this, HomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Afficher un message d'erreur en cas d'échec
                        Toast.makeText(AuthClient.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void SignUp() {
        // Création d'un nouvel utilisateur dans Firebase avec l'adresse e-mail et le mot de passe fournis
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password)
                // Si l'inscription réussit, on exécute ce code
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // On met à jour le nom d'affichage de l'utilisateur avec le nom fourni
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);

                        // On crée un nouvel objet ClientModel avec l'ID utilisateur, le nom, l'adresse e-mail et le mot de passe fournis
                        ClientModel clientModel = new ClientModel(FirebaseAuth.getInstance().getUid(), name, email, password);

                        // On enregistre les informations de l'utilisateur dans la base de données Firebase en utilisant son ID utilisateur comme clé
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(clientModel);

                        // On redirige l'utilisateur vers l'écran d'accueil de l'application et on ferme l'écran d'inscription
                        startActivity(new Intent(AuthClient.this, HomeActivity.class));
                        finish();
                    }
                });
    }
}
