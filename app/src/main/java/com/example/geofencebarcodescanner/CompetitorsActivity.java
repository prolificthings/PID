package com.example.geofencebarcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CompetitorsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<Competitor> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitors);

        recyclerView = findViewById(R.id.comp_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<Competitor>();
        list.add(new Competitor("149","Abhijeet Nayak","153"));
        list.add(new Competitor("148","Sumitra Kulkarni","148"));
        list.add(new Competitor("147","Rashmita Sahoo","143"));
        list.add(new Competitor("146","Anupama Sharma","140"));
        list.add(new Competitor("145","Damini Mishra","137"));
        list.add(new Competitor("144","Gouravi Dutta","135"));
        list.add(new Competitor("143","Harpreet Singh","132"));
        list.add(new Competitor("142","Jasmine","129"));
        list.add(new Competitor("141","Anwesha Priyadarshini","125"));
        list.add(new Competitor("140","Ahmed Khan","124"));
        list.add(new Competitor("139","Babli","122"));

        adapter = new CompetitorsAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }
}