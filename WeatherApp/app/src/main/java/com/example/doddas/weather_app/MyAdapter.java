package com.example.doddas.weather_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolders> {
   Context ct;
   ArrayList<MyData> myData;
    public MyAdapter(Context ct, ArrayList<MyData> myData) {
        this.ct=ct;
        this.myData=myData;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolders(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
    holder.date.setText(myData.get(position).getDate());
    holder.high.setText(myData.get(position).getHigh());
    holder.low.setText(myData.get(position).getLow());
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView date,high,low;
        public ViewHolders(View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.t1);
           high=(TextView)itemView.findViewById(R.id.t2);
           low=(TextView)itemView.findViewById(R.id.t3);

        }
    }
}
