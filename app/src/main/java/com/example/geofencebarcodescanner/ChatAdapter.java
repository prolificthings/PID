package com.example.geofencebarcodescanner;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.viewHolder>{
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    ArrayList<chatModel> chatList;
    Context context;

    public ChatAdapter(ArrayList<chatModel> list, Context context) {
        chatList = list;
        this.context = context;
    }

    String userName;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grp_item_u, parent, false);
            return new viewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grp_item_notu, parent, false);
            return new viewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        chatModel model = chatList.get(position);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            userName = snapshot.child("name").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        if (model.getUserName().equals(userName)){
            return VIEW_TYPE_MESSAGE_SENT;
        } else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        chatModel model = chatList.get(position);
        holder.body.setText(model.getChatBody());
        holder.date.setText(model.getChatDate());
        holder.time.setText(model.getChatTime());
        holder.name.setText(model.getUserName());
        
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name, body, date, time;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            body = itemView.findViewById(R.id.body);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
