package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

public class RankActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button compBtn, top3Btn;

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


                }
            }
        });

        compBtn = findViewById(R.id.competitorsBtn);
        compBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankActivity.this, CompetitorsActivity.class);
                startActivity(intent);
            }
        });

        top3Btn = findViewById(R.id.top3Btn);
        top3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top3Btn.setTextColor(getResources().getColor(R.color.black));
                Intent intent = new Intent(RankActivity.this, Top3Activity.class);
                startActivity(intent);
            }
        });
    }
}