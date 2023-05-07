package com.example.mini_projet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class UploadActivity extends AppCompatActivity {

    // Déclaration des variables pour les éléments de l'interface utilisateur
    ImageView uploadImage;
    Button saveButton;
    EditText uploadTopic, uploadDesc, uploadprice;

    // Déclaration des variables pour stocker l'URL de l'image et l'URI de l'image sélectionnée
    String imageUrl;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Initialisation des éléments de l'interface utilisateur
        uploadImage = findViewById(R.id.uploadimage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadprice = findViewById(R.id.uploadprice);
        saveButton = findViewById(R.id.btnsave);

        // Création d'un gestionnaire de résultat d'activité pour la sélection d'image
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    uri = data.getData();
                    uploadImage.setImageURI(uri);
                } else {
                    Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Définition d'un écouteur de clic pour le bouton d'ajout d'image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo = new Intent(Intent.ACTION_PICK);
                photo.setType("image/*");
                activityResultLauncher.launch(photo);
            }
        });

        // Définition d'un écouteur de clic pour le bouton d'enregistrement
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();


            }
        });

    }

    public void saveData() {
        // On récupère une référence à l'emplacement de stockage Firebase où on veut sauvegarder l'image
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("android images").child(uri.getLastPathSegment());

        // On crée une boîte de dialogue qui va afficher une barre de progression
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progression);
        AlertDialog dialog = builder.create();
        dialog.show();

        // On envoie l'image vers l'emplacement de stockage Firebase et on attend la fin de l'opération
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Une fois que l'opération est terminée avec succès, on récupère l'URL de l'image sauvegardée
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();

                // On appelle la méthode qui va sauvegarder les autres informations dans la base de données Firebase
                uploadData();

                // On ferme la boîte de dialogue affichant la barre de progression
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // En cas d'erreur, on ferme la boîte de dialogue affichant la barre de progression
                dialog.dismiss();
            }
        });
    }

    public void uploadData() {
        // On récupère les informations saisies par l'utilisateur dans l'interface graphique
        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        String price = uploadprice.getText().toString();

        // On crée un objet DataClass avec les informations récupérées et l'URL de l'image sauvegardée précédemment
        DataClass dataClass = new DataClass(title, desc, price, imageUrl);

        // On sauvegarde l'objet DataClass dans la base de données Firebase à l'emplacement "Android Tutorials" avec comme clé le titre de l'article
        FirebaseDatabase.getInstance().getReference("Products").child(title).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // En cas de succès, on affiche un message Toast indiquant que la sauvegarde a réussi et on ferme l'activité
                    Toast.makeText(UploadActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // En cas d'erreur, on affiche un message Toast avec le détail de l'erreur
                Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}