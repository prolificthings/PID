package com.example.geofencebarcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.viewHolder> {

    Context context;
    ArrayList<Rewards> rewardList;

    public RewardsAdapter(Context context, ArrayList<Rewards> list) {
        this.context = context;
        rewardList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.img.setImageResource(rewardList.get(position).getImg());
        holder.comp.setText(rewardList.get(position).getCompany());
        holder.offer.setText(rewardList.get(position).getOffer());
        holder.expire.setText(rewardList.get(position).getExpiry());
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView comp, offer, expire;
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            comp = itemView.findViewById(R.id.rewardCompany);
            offer = itemView.findViewById(R.id.rewardOffer);
            expire = itemView.findViewById(R.id.rewardExpiry);
            img = itemView.findViewById(R.id.rewardCompanyImg);
        }
    }
}
