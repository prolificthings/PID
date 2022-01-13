package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

                    case R.id.mCommunity:
                        startActivity(new Intent(getApplicationContext(),CommunityActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;


                }
            }
        });


        recyclerView = findViewById(R.id.rewardList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        rewardList = new ArrayList<Rewards>();
        rewardList.add(new Rewards("Jugnoo","Voila! 30% back upto ₹75. Offer valid once per user","Valid till 31st Oct",R.drawable.jugnoo));
        rewardList.add(new Rewards("Shopclues","Congrats! ₹300 off on Shopclues. Min spend of ₹1199","Valid till 31st Oct",R.drawable.shopclues));
        rewardList.add(new Rewards("Lensfit","Grab now! Flat ₹550 off on Sunglasses","Valid till 15th Nov",R.drawable.lensfit));
        rewardList.add(new Rewards("Khadim's","Shop for ₹2500 and above and get 30% off","Valid till 15th Nov",R.drawable.khadims));
        rewardList.add(new Rewards("HungerRush","Get flat ₹50 back on min order of ₹250","Valid till 30th Nov",R.drawable.hungerrush));
        rewardList.add(new Rewards("PeterEngland","Get flat ₹150 back on min purchase of ₹999","Valid till 30th Nov",R.drawable.peter));




        adapter = new RewardsAdapter(this,rewardList);
        recyclerView.setAdapter(adapter);



    }
}