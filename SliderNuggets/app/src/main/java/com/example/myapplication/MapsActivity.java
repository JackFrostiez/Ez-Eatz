package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String _fromWhere;
    private String _title, _address, _money_rating, _place_rating, _distance;
    private Intent _intent;
//    private Bundle _b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        _intent = getIntent(); // gets the previously created _intent

        _title = _intent.getStringExtra("title"); // will return value of key "title"'
        _address = _intent.getStringExtra("address"); // will return value of key "address"
        _money_rating = _intent.getStringExtra("money_rating"); // will return value of key "money_rating"
        _place_rating = _intent.getStringExtra("place_rating"); // will return value of key "place_rating"
        _distance = _intent.getStringExtra("distance"); // will return value of key "distance"

        TextView details = (TextView) findViewById(R.id.title);

        if(_title == null){
            //then do nothing
        }
        else{
            details.setText(_title);
            details = (TextView) findViewById(R.id.address);
            details.setText(_address);
            details = (TextView) findViewById(R.id.money_rating);
            details.setText(_money_rating);
            details = (TextView) findViewById(R.id.place_rating);
            details.setText(_place_rating);
            details = (TextView) findViewById(R.id.distance);
            details.setText(_distance);

        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void goBack(View view) {
//        System.out.println("Maps: " +_intent.getBooleanExtra("viewMorePressed", true));
        if(_intent.getStringExtra("fromWhere") == null){
            System.out.println("from main menu AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            finish();
        }
        else if(_intent.getStringExtra("fromWhere").equals("history") ){
            _intent.putExtra("viewMorePressed", false);
            setResult(RESULT_OK, _intent);
            System.out.println("from history AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            finish();
        }
        else if(_intent.getStringExtra("fromWhere").equals("bookmark") ){
            _intent.putExtra("viewMorePressed", false);
            setResult(RESULT_OK, _intent);
            System.out.println("from bookmark AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            finish();
        }
    }

}
