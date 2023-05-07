package com.example.mini_projet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataClass> list;

    public MyAdapter(Context context, ArrayList<DataClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

View  v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
return new MyViewHolder(v);

    }
    //kol ma view kherjet mel ecrant kol mabch naamlo ythat lena
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass data = list.get(position);
        holder.titre.setText(data.getDataTitle());
        holder.description.setText(data.getDataDescription());
        holder.price.setText(data.getDataPrice());

        Picasso.get().load(data.getDataImage()).into(holder.img);
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("dataImage", list.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("dataDescription", list.get(holder.getAdapterPosition()).getDataDescription());
                intent.putExtra("dataTitle", list.get(holder.getAdapterPosition()).getDataTitle());
                context.startActivity(intent);
            }
        });

    }

//f scroll lfou9 houa yaaml count ll data l chtscroliha chychouf 9adech men element ychargi fih
    @Override
    public int getItemCount() {
        return list.size();
    }


    // definir tous les element de la recycler
    public static class MyViewHolder extends  RecyclerView.ViewHolder
    {
TextView titre,description,price;
ImageView img;
        CardView recCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.image);
            recCard = itemView.findViewById(R.id.recCard);


        }
    }
}
