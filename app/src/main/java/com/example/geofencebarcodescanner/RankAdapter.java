package com.example.geofencebarcodescanner;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.viewHolder> {
    Context context;
    ArrayList<Rank> ranks;

    public RankAdapter(Context context, ArrayList<Rank> list) {
        this.context = context;
        ranks = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.no.setText(Integer.toString(ranks.get(position).getRankNo()));
        holder.name.setText(ranks.get(position).getName());
        holder.scans.setText(ranks.get(position).getScans());
        holder.image.setImageURI(Uri.parse(ranks.get(position).getImg()));
    }



    @Override
    public int getItemCount() {
        return ranks.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView no, name, scans;
        ImageView image;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.rankNo);
            name  = itemView.findViewById(R.id.rankerName);
            scans = itemView.findViewById(R.id.rankScans);
            image = itemView.findViewById(R.id.rankholder);
        }
    }

}
