package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.geofencebarcodescanner.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;
    FirebaseAuth mAuth;
    String currentDate,currentTime, grpName, userName, msg;
    FirebaseDatabase db;
    DatabaseReference dbChild;
    Intent intent;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<chatModel> chat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();


        grpName = intent.getExtras().get("groupName").toString();
        Toast.makeText(this, grpName, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dbChild = db.getReference().child("Groups").child(grpName);

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());

        layoutManager = new LinearLayoutManager(this);
        binding.chatRecycle.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(chat, this);

        dbChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chat.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    chatModel model = dataSnapshot.getValue(chatModel.class);
                    chat.add(model);
                }
                binding.chatRecycle.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.getReference().child("Users").child(Objects.requireNonNull(mAuth.getUid()))
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

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = binding.commentTxt.getText().toString();

                chatModel model = new chatModel();
                model.setUserName(userName);
                model.setChatBody(msg);
                model.setChatDate(currentDate);
                model.setChatTime(currentTime);

                db.getReference().child("Groups")
                        .child(grpName)
                        .push()
                        .setValue(model);

                binding.commentTxt.setText("");
            }
        });





    }
}