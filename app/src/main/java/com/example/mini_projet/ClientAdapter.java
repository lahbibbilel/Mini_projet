package com.example.mini_projet;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewHolder> {
    private Context context;
    public List<ClientModel> clientModelList;

    // Constructeur pour l'adaptateur, initialise la liste des clients
    public ClientAdapter(Context context) {
        this.context = context;
        this.clientModelList = new ArrayList<>();
    }

    // Ajoute un client à la liste et actualise l'affichage de la RecyclerView
    public void add(ClientModel clientModel) {
        clientModelList.add(clientModel);
        notifyDataSetChanged();
    }

    // Vide la liste des clients et actualise l'affichage de la RecyclerView
    public void clear() {
        clientModelList.clear();
        notifyDataSetChanged();
    }

    // Crée une vue pour chaque élément de la RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    // Lie les données d'un client à une vue de la RecyclerView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClientModel clientModel = clientModelList.get(position);
        if (clientModel != null) {
            holder.name.setText(clientModel.getClientName());
            holder.email.setText(clientModel.getClientEmail());

            // Ajoute un écouteur pour la vue de chaque client qui lance une nouvelle activité ChatActivity
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context,ChatActivity.class);
                    intent.putExtra("id",clientModel.getClientId());
                    context.startActivity(intent);
                }
            });
        } else {
            Log.e(TAG, "clientModel is null clientModel is null for position: " + position);
        }
    }

    // Retourne le nombre d'éléments dans la liste des clients
    @Override
    public int getItemCount() {
        return clientModelList.size();
    }

    // Représente la vue de chaque élément de la RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,email;

        // Constructeur pour chaque vue
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.useremail);
        }
    }
}
