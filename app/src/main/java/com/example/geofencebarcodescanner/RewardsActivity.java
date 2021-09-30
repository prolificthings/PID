package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RewardsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<Rewards> rewardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.mReward);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mReward:

                    case R.id.mRank:
                        startActivity(new Intent(getApplicationContext(),RankActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.mLocation:
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;


                }
            }
        });


        recyclerView = findViewById(R.id.rewardList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rewardList = new ArrayList<Rewards>();
        rewardList.add(new Rewards("Jugnoo","Get 30% back upto ₹75.Offer valid once per user.","Expires on 25th August",R.drawable.jugnoo));
        rewardList.add(new Rewards("Shopclues","Congrats! ₹300 off on Shopclues.Min spend of ₹1199.","Expires on 31th August",R.drawable.shopclues));
        rewardList.add(new Rewards("Lensfit","Grab now! Flat ₹550 off on Sunglasses.","Expires on 15th August",R.drawable.lensfit));
        rewardList.add(new Rewards("Khadim's","Shop for ₹2500/- and above and get 30% off.","Expires on 15th September",R.drawable.khadims));
        rewardList.add(new Rewards("HungerRush","Get flat ₹50 back on min order of ₹250.","Expires on 30th September",R.drawable.hungerrush));



        adapter = new RewardsAdapter(this,rewardList);
        recyclerView.setAdapter(adapter);



    }
}