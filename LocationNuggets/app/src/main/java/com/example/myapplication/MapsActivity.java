package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String _fromWhere;
    private String _title, _address, _money_rating, _place_rating, _distance;
    private Intent _intent;
    LocationManager locationManager;
//    private Bundle _b;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SupportMapFragment googleMap =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //manages the location

        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        //Checks if the network provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude(); //get the latitude
                    double longitude = location.getLongitude(); // get the longitude

                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality();
                        str += addressList.get(0).getUrl();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f));
                    }catch (IOException e){
                        e.printStackTrace();
                    }


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude(); //get the latitude
                    double longitude = location.getLongitude(); // get the longitude

                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality();
                        str += addressList.get(0).getUrl();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f));
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }


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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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