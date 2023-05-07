package com.example.mini_projet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {
    // Declare variables
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<DataClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        // Get reference to the RecyclerView from the layout file
        recyclerView = findViewById(R.id.listData);

        // Get a reference to the "Products" node in the Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference("Products");

        // Set the layout manager for the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList and the adapter for the RecyclerView
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(myAdapter);

        // Add a ValueEventListener to the "Products" node in the Firebase Realtime Database
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the ArrayList before adding new data to it
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Retrieve the DataClass object from the DataSnapshot and add it to the ArrayList
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    list.add(dataClass);
                }
                // Notify the adapter that the data has changed
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}