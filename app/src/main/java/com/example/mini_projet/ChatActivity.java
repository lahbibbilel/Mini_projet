package com.example.mini_projet;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mini_projet.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String receiverId;

    DatabaseReference databaseReferenceSender,databaseReferenceReceiver;

    String senderRoom,receiverRoom;

    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupération de l'identifiant du destinataire
        receiverId = getIntent().getStringExtra("id");

        // Création des noms de salle pour l'expéditeur et le destinataire
        senderRoom = FirebaseAuth.getInstance().getUid()+receiverId;
        receiverRoom = receiverId+FirebaseAuth.getInstance().getUid();

        // Configuration de l'adaptateur de messages
        messageAdapter = new MessageAdapter(this);
        binding.recyclerchat.setAdapter(messageAdapter); // Ajout de l'adaptateur au RecyclerView
        binding.recyclerchat.setLayoutManager(new LinearLayoutManager(this)); // Configuration du LayoutManager

        // Récupération des références de la base de données Firebase
        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);

        // Ajout d'un écouteur de changements pour les messages envoyés
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Effacement des messages précédents
                messageAdapter.clear();
                // Parcours des messages pour les ajouter à l'adaptateur
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessagesModel messagesModel = dataSnapshot.getValue(MessagesModel.class);
                    messageAdapter.add(messagesModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Ajout d'un écouteur de clic sur le bouton d'envoi de messages
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageEd.getText().toString();
                // Vérification que le message n'est pas vide
                if (message.trim().length() > 0) {
                    // Envoi du message
                    sendMessage(message);
                }
            }
        });
    }

    // Envoi d'un message
    private void sendMessage(String message) {
        String messageId = UUID.randomUUID().toString();
        MessagesModel messagesModel = new MessagesModel(messageId, FirebaseAuth.getInstance().getUid(), message);
        // Ajout du message à l'adaptateur
        messageAdapter.add(messagesModel);
        // Envoi du message à la base de données Firebase pour l'expéditeur et le destinataire
        databaseReferenceSender.child(messageId).setValue(messagesModel);
        databaseReferenceReceiver.child(messageId).setValue(messagesModel);
    }

}
