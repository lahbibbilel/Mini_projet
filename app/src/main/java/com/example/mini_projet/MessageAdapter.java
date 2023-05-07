package com.example.mini_projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

// Définition de la classe MessageAdapter
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    // Déclaration des variables
    private Context context;
    public List<MessagesModel> messageModelList;

    // Constructeur de la classe
    public MessageAdapter(Context context) {
        this.context = context;
        this.messageModelList = new ArrayList<>();
    }

    // Ajouter un élément à la liste
    public void add(MessagesModel messagesModel) {
        messageModelList.add(messagesModel);
        notifyDataSetChanged();
    }

    // Vider la liste
    public void clear() {
        messageModelList.clear();
        notifyDataSetChanged();
    }

    // Créer la vue pour chaque élément de la liste
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MyViewHolder(view);
    }

    // Afficher les éléments dans chaque vue
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessagesModel messagesModel = messageModelList.get(position);
        holder.msg.setText(messagesModel.getMessage());

        // Si l'expéditeur est l'utilisateur actuel
        if (messagesModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
            holder.msg.setTextColor(context.getResources().getColor(R.color.white));
        }
        // Sinon
        else {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.lavender));
            holder.msg.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    // Retourne le nombre d'éléments dans la liste
    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    // Définition des vues pour chaque élément
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView msg;
        private LinearLayout main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}
