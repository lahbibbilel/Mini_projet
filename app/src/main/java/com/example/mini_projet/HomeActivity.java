package com.example.mini_projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mini_projet.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    DatabaseReference databaseReference;

    ClientAdapter clientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clientAdapter = new ClientAdapter(this);
        binding.recycler.setAdapter(clientAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.orderByChild("clientId").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ClientModel> newClientModelList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String clientId = dataSnapshot.getKey();
                    ClientModel clientModel = dataSnapshot.getValue(ClientModel.class);
                    newClientModelList.add(clientModel);
                }
                clientAdapter.clear();
                clientAdapter.clientModelList = newClientModelList;
                clientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Obtenez la référence du bouton de redirection dans la Toolbar
        Button redirectButton = findViewById(R.id.redirect_button);

        // Ajoutez le OnClickListener pour le bouton de redirection
        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancez l'activité correspondante ici
                Intent intent = new Intent(HomeActivity.this, ListData.class);
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.logout_button);

        // Ajoutez le OnClickListener pour le bouton de déconnexion
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Déconnectez l'utilisateur ici
                FirebaseAuth.getInstance().signOut();

                // Lancez l'activité correspondante ici
                Intent intent = new Intent(HomeActivity.this, AuthClient.class);
                startActivity(intent);
                finish(); // Terminez l'activité HomeActivity pour empêcher l'utilisateur de revenir en arrière
            }
        });
    }

}
