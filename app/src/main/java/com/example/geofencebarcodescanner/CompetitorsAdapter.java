package com.example.geofencebarcodescanner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompetitorsAdapter extends RecyclerView.Adapter<CompetitorsAdapter.viewHolder> {

    ArrayList<Competitor> list;
    Context context;

    public CompetitorsAdapter(Context context, ArrayList<Competitor> people){
        this.context = context;
        list = people;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.competitor_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.rank.setText(list.get(position).getRank());
        holder.scan.setText(list.get(position).getScan());

        int pos = holder.getLayoutPosition();
        int centerItem = list.size()/2;
        if (pos==centerItem){
            holder.name.setTextColor(context.getResources().getColor(R.color.red));
            holder.rank.setTextColor(context.getResources().getColor(R.color.red));
            holder.scan.setTextColor(context.getResources().getColor(R.color.red));
            holder.name.setTypeface(null, Typeface.BOLD);
            holder.rank.setTypeface(null, Typeface.BOLD);
            holder.scan.setTypeface(null, Typeface.BOLD);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.golden));

       }

        /*if(list.get(position).getRank().equals("145")){
            holder.name.setTextColor(context.getResources().getColor(R.color.red));
            holder.rank.setTextColor(context.getResources().getColor(R.color.red));
            holder.scan.setTextColor(context.getResources().getColor(R.color.red));
        }       */



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name,rank,scan;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTxt);
            rank = itemView.findViewById(R.id.rankTxt);
            scan = itemView.findViewById(R.id.scansTxt);
        }
    }
}
