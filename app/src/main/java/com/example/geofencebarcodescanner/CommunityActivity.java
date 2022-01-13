package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommunityActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ListView listView;
    DatabaseReference RootRef;
    private ArrayAdapter<String> arrrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_grp_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.mCreateGrp){
            RequestNewGrp();
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void RequestNewGrp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CommunityActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Group Name: ");

        final EditText groupNameField = new EditText(CommunityActivity.this);
        groupNameField.setTextCursorDrawable(null);
        groupNameField.setHint("    e.g Friends Forever");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName = groupNameField.getText().toString();

                if(TextUtils.isEmpty(groupName)) {
                    Toast.makeText(CommunityActivity.this, "Please enter Group name", Toast.LENGTH_SHORT).show();
                }
                else{
                    CreateNewGroup(groupName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(String groupName) {
        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(CommunityActivity.this, groupName + " group is created successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        RootRef = FirebaseDatabase.getInstance().getReference();

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.mCommunity);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mCommunity:

                    case R.id.mLocation:
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.mRank:
                        startActivity(new Intent(getApplicationContext(),RankActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.mReward:
                        startActivity(new Intent(getApplicationContext(),RewardsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                }
            }
        });



        RetrieveAndDisplayGroups();

        listView = findViewById(R.id.listView);
        arrrayAdapter = new ArrayAdapter<String>(CommunityActivity.this, android.R.layout.simple_list_item_1, list_of_groups);
        listView.setAdapter(arrrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentGroupName = adapterView.getItemAtPosition(position).toString();

                Intent groupChatIntent = new Intent(CommunityActivity.this, GroupChatActivity.class);
                groupChatIntent.putExtra("groupName",currentGroupName);
                startActivity(groupChatIntent);
            }
        });


    }

    private void RetrieveAndDisplayGroups() {
        RootRef.child("Groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> set = new HashSet<>();              //set = userinput name
                Iterator iterator = snapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}