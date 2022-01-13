package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<Rank> ranks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.mRank);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mRank:

                    case R.id.mLocation:
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.mReward:
                        startActivity(new Intent(getApplicationContext(),RewardsActivity.class));
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

        recyclerView = findViewById(R.id.rank_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ranks = new ArrayList<Rank>();
        ranks.add(new Rank(1,"Manisha","262 scans","android.resource://" + getPackageName() + "/drawable/r2"));
        ranks.add(new Rank(2,"Avinash","259 scans","android.resource://" + getPackageName() + "/drawable/r1"));
        ranks.add(new Rank(3,"Nupur","255 scans","android.resource://" + getPackageName() + "/drawable/r3"));
        ranks.add(new Rank(4,"Satish","254 scans","android.resource://" + getPackageName() + "/drawable/r4"));
        ranks.add(new Rank(5,"Priyansh","240 scans","android.resource://" + getPackageName() + "/drawable/r7"));
        ranks.add(new Rank(6,"Jasmine","235 scans","android.resource://" + getPackageName() + "/drawable/r5"));
        ranks.add(new Rank(7,"Parveen","232 scans","android.resource://" + getPackageName() + "/drawable/r6"));
        ranks.add(new Rank(8,"Raj","230 scans","android.resource://" + getPackageName() + "/drawable/r1"));
        ranks.add(new Rank(9,"Somali","229 scans","android.resource://" + getPackageName() + "/drawable/r4"));
        ranks.add(new Rank(10,"Sritam","217 scans","android.resource://" + getPackageName() + "/drawable/r7"));

        adapter = new RankAdapter(this,ranks);
        recyclerView.setAdapter(adapter);




    }
}