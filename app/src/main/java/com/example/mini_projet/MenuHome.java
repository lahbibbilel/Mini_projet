package com.example.mini_projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        Button goAdmin = findViewById(R.id.Admin);

        // Ajoutez le OnClickListener pour le bouton de redirection
        goAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancez l'activité correspondante ici
                Intent intent = new Intent(MenuHome.this, SignUpActivity.class);
                startActivity(intent);
            }
        });



    Button goClient = findViewById(R.id.Client);

    // Ajoutez le OnClickListener pour le bouton de redirection
        goClient.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Lancez l'activité correspondante ici
            Intent intent = new Intent(MenuHome.this, AuthClient.class);
            startActivity(intent);
        }
    });

}


}