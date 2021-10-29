package com.example.geofencebarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.location.LocationListener;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1001;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private static int UPDATE_INTERVAL = 5000; //5secs
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    BottomNavigationView bottomNavigationView;

    RelativeLayout rll;

    DatabaseReference dbRef;
    GeoFire geoFire;

    Marker currentMarker;

    Button scan;

    ImageView rewardImg;
    ScrollView scrollView;

    LineChart barChart;
    LineData barData;
    LineDataSet barDataSet;
    ArrayList<Entry> barEntryList;
    ArrayList<BarDetails> barEntries = new ArrayList<>();
    ArrayList<String> labels;

    private ChildEventListener mChildEventListener;
    private DatabaseReference mLocs;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rll = findViewById(R.id.rewardRll);
        scrollView = findViewById(R.id.scrollView);

        ((WorkAroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkAroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        ChildEventListener mChildEventListener;
        mLocs = FirebaseDatabase.getInstance().getReference("Location");
        mLocs.push().setValue(marker);

        dbRef = FirebaseDatabase.getInstance().getReference("My Location");
        geoFire = new GeoFire(dbRef);

        setLocation();

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.mLocation);



        barChart = findViewById(R.id.barChart);
        barEntryList = new ArrayList<>();
        labels = new ArrayList<>();
        getEntries();
        for(int i=0; i<barEntries.size(); i++){
            String month = barEntries.get(i).getMonth();
            int scan = barEntries.get(i).getScans();
            barEntryList.add(new BarEntry(i,scan));
            labels.add(month);
        }

        int colorArray[] = {R.color.green, R.color.golden, R.color.red, R.color.golden, R.color.green, R.color.red,
                R.color.green, R.color.golden, R.color.red, R.color.red, R.color.red};

        barDataSet = new LineDataSet(barEntryList,"Monthly scans");
        barData = new LineData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(colorArray,MapsActivity.this);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);
        barDataSet.setLineWidth(3.0f);
        Description description = new Description();
        description.setText("Months");
        barChart.setDescription(description);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(0);
        barChart.animateY(2000);
        barChart.invalidate();

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mLocation:

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


        //rll.setVisibility(View.GONE);


        scan= findViewById(R.id.scanBtn);
        scan.setVisibility(INVISIBLE);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent scanIntent = new Intent(MapsActivity.this,ScanActivity.class);
                startActivity(scanIntent);
                finish();       */

                IntentIntegrator intentIntegrator =  new IntentIntegrator(MapsActivity.this);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(scan.class);
                intentIntegrator.initiateScan();
            }
        });
    }


    private void getEntries(){
        barEntries = new ArrayList<>();
        barEntries.add(new BarDetails("Jan",20));
        barEntries.add(new BarDetails("Feb",40));
        barEntries.add(new BarDetails("Mar",30));
        barEntries.add(new BarDetails("Apr",15));
        barEntries.add(new BarDetails("May",38));
        barEntries.add(new BarDetails("June",54));
        barEntries.add(new BarDetails("July",0));
        barEntries.add(new BarDetails("Aug",27));
        barEntries.add(new BarDetails("Sept",22));
        barEntries.add(new BarDetails("Oct",8));
        barEntries.add(new BarDetails("Nov",0));
        barEntries.add(new BarDetails("Dec",0));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data);

        if (intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }

            });
            rll.setVisibility(View.VISIBLE);
            rll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rll.setVisibility(View.INVISIBLE);
                }
            });

            builder.show();
        } else{
            Toast.makeText(getApplicationContext(), "You did not scan anything", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (checkPlayServices()){
                        buildGoogleApiClient();
                        createLocationrequest();
                        displayLocation();
                    }
                }
                break;
        }
    }

    private void setLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request runtime permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {      //check if google play store is there or not
                buildGoogleApiClient();
                createLocationrequest();
                displayLocation();
            }
        }
    }

    private void displayLocation() {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null){
                double latitude = lastLocation.getLatitude();
                double longitude = lastLocation.getLongitude();

                geoFire.setLocation("You", new GeoLocation(latitude, longitude),
                        new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                if (currentMarker != null)
                                    currentMarker.remove();
                                currentMarker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude,longitude)).title("You"));

                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),18.3f));
                            }
                        });
            }
    }

    private void createLocationrequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            else {
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mLocs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    Locations loc = s.getValue(Locations.class);
                    LatLng location = new LatLng(loc.latitude,loc.longitude);
                    mMap.addMarker(new MarkerOptions().position(location)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dustbin));

                    mMap.addCircle(new CircleOptions()
                            .center(location)
                            .radius(5.0)//mtrs
                            .strokeColor(Color.GREEN)
                            .fillColor(Color.argb(100, 0, 200, 0))
                            .strokeWidth(5.0f)
                    );

                    GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(location.latitude,location.longitude),0.02f);          //0.5f = 0.5km= 500m

                    geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onKeyEntered(String key, GeoLocation location) {
                            sendNotification("NEHA",String.format("%s entered the bin area",key));
                            scan.setVisibility(VISIBLE);
                        }

                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onKeyExited(String key) {
                            sendNotification("NEHA", String.format("%s exited from the bin area",key));
                            scan.setVisibility(INVISIBLE);
                        }

                        @Override
                        public void onKeyMoved(String key, GeoLocation location) {
                            Log.d("MOVE",String.format("%s moved within the bin area[%f,%f]",key,location.latitude,location.longitude));
                        }

                        @Override
                        public void onGeoQueryReady() {

                        }

                        @Override
                        public void onGeoQueryError(DatabaseError error) {
                            Log.e("ERROR",""+error);
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String title, String content) {
            NotificationChannel channel = new NotificationChannel("myChannel","Hello",NotificationManager.IMPORTANCE_HIGH);
        Notification.Builder builder = new Notification.Builder(this,"myChannel")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content);
        NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        Intent intent = new Intent(this, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        manager.notify(new Random().nextInt(),notification);


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;
        displayLocation();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}