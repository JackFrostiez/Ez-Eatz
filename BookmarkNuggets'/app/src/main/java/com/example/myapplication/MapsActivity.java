package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //UI stuff
    private GoogleMap mMap;
    private String _fromWhere;
    private Intent _intent;
    private TextView _searchButton;
    private ProgressBar _progressBar;

    //Location stuff
    private LocationManager _locationManager;
    private Location _currentLocation;
    private LatLng _currentCoords;
    private boolean _gps_enabled, _network_enabled;

    //data stuff
    private SharedPreferences _sharePref; //to get access to the app's data
    private ArrayList<LocationItem> _places; //list of found places
    private Queue<LocationItem> _history;
    private Queue<LocationItem> _bookmark;
    private LocationItem _currentPlace;
    private Gson _gson = new Gson();
    private int _currentPlaceCursor = 0;
    private int _radius; //in meters

    String _location = "43.0008093" + "," + "-78.7889697", //needs user's location
            _type = "restaurant", //default
            _keyword = "food"; //default

    //String apiKey = "insert API Key";
    String _apiKey = "AIzaSyBfcTVZbv54YYhMkYn_uldBmtEmI49kLI0";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        _sharePref = PreferenceManager.getDefaultSharedPreferences(this);

        // Sets up the Google Maps
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SupportMapFragment googleMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //checks permissions are given
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            finish(); //You can't be here if you don't have permissions to use this service at all
        }

        //next, check if location service is enabled
        //locationManager handles location stuff
        _locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        _gps_enabled = _locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        _network_enabled = _locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        _searchButton = (TextView) findViewById(R.id.nextButton);

        //Checks if either network provider or gps on
        if (_network_enabled) {
            System.out.println("Getting initial location...");
            _locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerSetUp());
            _currentLocation = _locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            loadData();

//            while(_locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null){
////            while(_currentLocation == null){
//
////                    System.out.println("Waiting for location retrieval...");
//                //waits for location gets updated by the locatiomManager's listener
//            }

        } else if (_gps_enabled) {
            _locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerSetUp());
            _currentLocation = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            while (_locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
//            while(_currentLocation == null){

//                    System.out.println("Waiting for location retrieval...");
                //waits for location gets updated by the locatiomManager's listener
                _currentLocation = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        //Set up the _progressBar to display loading to user
        _progressBar = findViewById(R.id.pBar);
        _progressBar.setVisibility(View.VISIBLE);
    }

    public LocationListener locationListenerSetUp(){

        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                System.out.println("IM BEING UPDATED YOOOOOO");
                _currentLocation = location;
//                double latitude = location.getLatitude(); //get the latitude
//                double longitude = location.getLongitude(); // get the longitude
//
//                LatLng latLng = new LatLng(latitude, longitude);
//                Geocoder geocoder = new Geocoder(getApplicationContext());
//                try {
//                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                    String str = "YOU";
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                    //automatically move the to updated location
////                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19.0f));
//
//                    MarkerOptions marker = new MarkerOptions().position(latLng).title(str);
//                    mMap.clear();
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                System.out.println("IM BEING UPDATED YOOOOOO");
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Toast.makeText(getApplicationContext(), "WHEN WILL THIS EVER POP UP", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(getApplicationContext(), "Trying to locate...", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderDisabled(String s) {
//                checkLocationOn("Your location seems to be disabled. Please re-enable it.");
            }
        };
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
        System.out.println("Starting map up...");
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                //do stuff here
                System.out.println("THE MAP IS READY");
                while(_currentLocation == null){

                    System.out.println("Waiting for location retrieval...");
                }
                _currentCoords = new LatLng(_currentLocation.getLatitude(), _currentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(_currentCoords,19.0f));

                _intent = getIntent();
                //check to see if it's just viewing a location or actually trying to search for places
                if(justViewMore()){
                    //then just display the searched result
                }
                else{
                    loadData();
                }
            }
        });
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void loadData(){
        //Load the user's data
        _radius = _sharePref.getInt("Radius", 300);
        _location = _currentLocation.getLatitude() + "," + _currentLocation.getLongitude();
        String NearbySearchRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                _location + "&radius=" +
                _radius + "&type=" +
                _type + "&keyword=" +
                _keyword + "&key=" +
                _apiKey;

        System.out.println(NearbySearchRequest);
        System.out.println("Radius: " + _radius);
        HttpReq req = new HttpReq(NearbySearchRequest, this);
        //commented out to prevent over API usage
        req.execute();

        //busy waiting for the request to be done
        while(!req.isCancelled()){
            //BUSY WAITING FOR THE REQUEST TO BE DONE AND THE DATA GETS PASSED
        }
        //busy wait for googleMap finishes set up

        _places = req.getData("NearbyPlacesData");
        _history = getHistory("History");
        _bookmark = getBookmarks("Bookmark");
        if(_history == null){
            _history = new LinkedList<LocationItem>();
        }
        if(_bookmark == null){
            _bookmark = new LinkedList<LocationItem>();
        }
        System.out.println("Got the data: " + _places);
        _progressBar.setVisibility(View.GONE);
        unlockButtons();
        updateUI(); //display the first place of the data
    }

    private void updateUI(){

        if(_places == null){
            noPlacesFound();
            return;
        }
        //get the place's info from the list
        _currentPlace = _places.get(_currentPlaceCursor);
        _currentCoords = new LatLng(_currentPlace.getLATITUDE(), _currentPlace.getLONGITUDE());


        TextView views = (TextView) findViewById(R.id.title);

        views.setText(_currentPlace.getTitle());

        views = (TextView) findViewById(R.id.money_rating);
        String money_level = "";
        if(!_currentPlace.getMoneyRating().equals("N/A") ){
            for(int i = 0; i < Integer.valueOf(_currentPlace.getMoneyRating()); i++){
                money_level = money_level + "$";
            }
            views.setText(money_level);

        }
        else{
            views.setText("N/A");
        }

        //update info display
        views = (TextView) findViewById(R.id.place_rating);
        views.setText(_currentPlace.getPlaceRating() + " / 5");

        views = (TextView) findViewById(R.id.theCurrentSpot);
        views.setText("Back to the found spot");

        System.out.println("Place: " + _currentPlace.getTitle());
        System.out.println( _currentCoords);

        views = (TextView) findViewById(R.id.theCurrentSpot);

        //record visited locations
        System.out.println(_history.size());
        if(_history.size() >= 10){ //if max, dequeue head
            _history.poll();
        }
        _history.add(_currentPlace);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_currentCoords,19.0f));
        mMap.addMarker(new MarkerOptions().position(_currentCoords).title(_currentPlace.getTitle()));

    }

    public void displayNextPlace(View view){

        _currentPlaceCursor += 1;

        if(_currentPlaceCursor == _places.size()){
            //end of the searched results, user should change settings or increase _radius
            //for now just close it if successfully go through
            noPlacesFound();
        }
        else{
            //one of them is on, so we can begin searching
            //grab the next place's coords
            updateUI();
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_currentCoords,19.0f));
        }
    }

    private void checkLocationOn(String message){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            finish();
                            dialog.cancel();

                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
    }

    private boolean noPlacesFound(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No places have been found. Please adjust your radius or preferences then try again.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        System.out.println("NO PLACES FOUND. GOING BACK TO MAIN MENU...");
                        storeData("History", _gson.toJson(_history));
                        storeData("Bookmarks", _gson.toJson(_bookmark));

                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

        return true;
    }

    @Override
    public void onBackPressed() {
        System.out.println("PRESSED NAV CANCEL");
        storeData("History", _gson.toJson(_history));
        storeData("Bookmarks", _gson.toJson(_bookmark));
        finish();

    }

    public void goBack(View view) {
        System.out.println("PRESSED CANCEL");
        storeData("History", _gson.toJson(_history));
        storeData("Bookmarks", _gson.toJson(_bookmark));
        finish();
    }

    //redirect user back to the found food place
    public void theCurrentPlace(View view) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_currentCoords,19.0f));
    }

    public void bookmarkPlace(View view){
        Toast.makeText(getApplicationContext(), "Bookmarked: " + _currentPlace.getTitle() +" !", Toast.LENGTH_SHORT).show();
        _bookmark.add(_currentPlace);
        displayNextPlace(view);
    }

    private void unlockButtons(){
        TextView buttons;

        buttons = (TextView) findViewById(R.id.nextButton);
        buttons.setEnabled(true);

        buttons = (TextView) findViewById(R.id.theCurrentSpot);
        buttons.setEnabled(true);

        buttons = (TextView) findViewById(R.id.theBookmark);
        buttons.setEnabled(true);

    }

    //takes in key, and value in converted gson string
    private void storeData(String key, String value) {
        if(_history == null){
            return;
        }
        SharedPreferences.Editor editor = _sharePref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private ArrayList<LocationItem> getPlaces(String key) {
        return _gson.fromJson(_sharePref.getString(key, null), new TypeToken<ArrayList<LocationItem>>(){}.getType());
    }

    private Queue<LocationItem> getHistory(String key) {
        return _gson.fromJson(_sharePref.getString(key, null), new TypeToken<Queue<LocationItem>>(){}.getType());
    }

    private Queue<LocationItem> getBookmarks(String key) {
        return _gson.fromJson(_sharePref.getString(key, null), new TypeToken<Queue<LocationItem>>(){}.getType());
    }

    private boolean justViewMore(){

        System.out.println(_intent.getStringExtra("fromWhere"));
        if(_intent.getStringExtra("fromWhere") == null){
            return false;
        }

        if(_intent.getStringExtra("fromWhere").equals("history") ){
            _intent.putExtra("viewMorePressed", false);
            setResult(RESULT_OK, _intent);
            System.out.println("from history AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            return true;
        }
        if(_intent.getStringExtra("fromWhere").equals("bookmark") ){
            _intent.putExtra("viewMorePressed", false);
            setResult(RESULT_OK, _intent);
            System.out.println("from bookmark AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            return true;
        }
//        else if(_intent.getStringExtra("fromWhere").equals("bookmark") ){
//            _intent.putExtra("viewMorePressed", false);
//            setResult(RESULT_OK, _intent);
//            System.out.println("from bookmark AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//            finish();
//        }
        return false;
    }

}
