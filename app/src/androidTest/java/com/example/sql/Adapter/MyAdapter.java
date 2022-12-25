package com.example.sql.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sql.R;

import java.util.List;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>
{


    List<ListItem> listItemArrayList;
    Context context;
    public  MyAdapter(List<ListItem>listItemArrayList, Context context)
    {
        this.listItemArrayList = listItemArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent, false);
        return new MyAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        ListItem listItem = listItemArrayList.get(position);


        holder.textViewType.setText(listItem.getType());
        holder.textViewCode.setText(listItem.getCode());
        Linkify.addLinks(holder.textViewCode,Linkify.ALL);

    }

    @Override
    public int getItemCount() {
        return listItemArrayList.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewCode,textViewType;
        CardView cardView;
        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            textViewCode = (TextView)itemView.findViewById(R.id.textViewCode);
            textViewType = (TextView) itemView.findViewById(R.id.textViewType);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String type = listItemArrayList.get(getAdapterPosition()).getType();

                    Intent i = new Intent ();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_TEXT,type);
                    i.setType("text/path");
                    itemView.getContext().startActivity(i);

                }
            });
        }
    }
}
