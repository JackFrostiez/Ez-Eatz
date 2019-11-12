package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import android.widget.Toast;
import java.util.List;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.Places;



import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient; //fetching current location of device
    private PlacesClient placesClient;  //Looks for suggestions in map
    private List<AutocompletePrediction> predictionList;

    private Location mLastknownLocation;
    private LocationCallback locationCallBack;
    private Location currentLocation;
    private Marker currentLocationMarker;
    LocationListener locationListener;
    private TextView responseView;
    private FieldSelector fieldSelector;



    private String _title, _address, _money_rating, _place_rating, _distance;
    private Intent _intent;
    LocationManager locationManager;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);

        // Set view objects
        List<Place.Field> placeFields =
                FieldSelector.getPlaceFields(
                        Field.ADDRESS_COMPONENTS,
                        Field.OPENING_HOURS,
                        Field.PHONE_NUMBER,
                        Field.UTC_OFFSET,
                        Field.WEBSITE_URI);
        fieldSelector =
                new FieldSelector(
                        findViewById(R.id.use_custom_fields),
                        findViewById(R.id.custom_fields_list),
                        placeFields);
        responseView = findViewById(R.id.response);
        setLoading(false);

        // Set listeners for programmatic Find Current Place
        findViewById(R.id.find_current_place_button).setOnClickListener((view) -> findCurrentPlace());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //manages the location
//        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
//        //Checks if the network provider is enabled
//        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                    0, 10, new LocationListener() {
//
//                        boolean isFirstTime = true;
//
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            double latitude = location.getLatitude(); //get the latitude
//                            double longitude = location.getLongitude(); // get the longitude
//
////                    if (isFirstTime) {
//                            LatLng latLng = new LatLng(latitude, longitude);
//                            Geocoder geocoder = new Geocoder(getApplicationContext());
//                            try {
//                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                                String str = addressList.get(0).getLocality();
//                                str += addressList.get(0).getUrl();
//                                mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
//
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
////                        isFirstTime = false;
////                    }
//                        }
//
//
//
//                        @Override
//                        public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String s) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String s) {
//
//                        }
//                    });
//
//        }
//        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    double latitude = location.getLatitude(); //get the latitude
//                    double longitude = location.getLongitude(); // get the longitude
//
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    Geocoder geocoder = new Geocoder(getApplicationContext());
//                    try {
//                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                        String str = addressList.get(0).getLocality();
//                        str += addressList.get(0).getUrl();
//                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String s) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String s) {
//
//                }
//            });
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,10, locationListener);

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

    }
    /**
     * Fetches a list of {@link PlaceLikelihood} instances that represent the Places the user is
     * most
     * likely to be at currently.
     */
    private void findCurrentPlace() {
        if (ContextCompat.checkSelfPermission(this, permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    this,
                    "Both ACCESS_WIFI_STATE & ACCESS_FINE_LOCATION permissions are required",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        // Note that it is not possible to request a normal (non-dangerous) permission from
        // ActivityCompat.requestPermissions(), which is why the checkPermission() only checks if
        // ACCESS_FINE_LOCATION is granted. It is still possible to check whether a normal permission
        // is granted or not using ContextCompat.checkSelfPermission().
        if (checkPermission(ACCESS_FINE_LOCATION)) {
            findCurrentPlaceWithPermissions();
        }
    }

    /**
     * Fetches a list of {@link PlaceLikelihood} instances that represent the Places the user is
     * most
     * likely to be at currently.
     */
    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    private void findCurrentPlaceWithPermissions() {
        setLoading(true);

        FindCurrentPlaceRequest currentPlaceRequest =
                FindCurrentPlaceRequest.newInstance(getPlaceFields());
        Task<FindCurrentPlaceResponse> currentPlaceTask =
                placesClient.findCurrentPlace(currentPlaceRequest);

        currentPlaceTask.addOnSuccessListener(
                (response) ->
                        responseView.setText(StringUtil.stringify(response, isDisplayRawResultsChecked())));

        currentPlaceTask.addOnFailureListener(
                (exception) -> {
                    exception.printStackTrace();
                    responseView.setText(exception.getMessage());
                });

        currentPlaceTask.addOnCompleteListener(task -> setLoading(false));
    }

        //////////////////////////
        // Helper methods below //
        //////////////////////////

    private List<Place.Field> getPlaceFields() {
        if (((CheckBox) findViewById(R.id.use_custom_fields)).isChecked()) {
            return fieldSelector.getSelectedFields();
        } else {
            return fieldSelector.getAllFields();
        }
    }

    private boolean checkPermission(String permission) {
        boolean hasPermission =
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }
        return hasPermission;
    }

    private boolean isDisplayRawResultsChecked() {
        return ((CheckBox) findViewById(R.id.display_raw_results)).isChecked();
    }

    private void setLoading(boolean loading) {
        findViewById(R.id.loading).setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }






    //        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //manages the location
//        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
//        //Checks if the network provider is enabled
//        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                    0, 10, new LocationListener() {
//
//                        boolean isFirstTime = true;
//
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            double latitude = location.getLatitude(); //get the latitude
//                            double longitude = location.getLongitude(); // get the longitude
//
////                    if (isFirstTime) {
//                            LatLng latLng = new LatLng(latitude, longitude);
//                            Geocoder geocoder = new Geocoder(getApplicationContext());
//                            try {
//                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                                String str = addressList.get(0).getLocality();
//                                str += addressList.get(0).getUrl();
//                                mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
//
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
////                        isFirstTime = false;
////                    }
//                        }
//
//
//
//                        @Override
//                        public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String s) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String s) {
//
//                        }
//                    });
//
//        }
//        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    double latitude = location.getLatitude(); //get the latitude
//                    double longitude = location.getLongitude(); // get the longitude
//
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    Geocoder geocoder = new Geocoder(getApplicationContext());
//                    try {
//                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                        String str = addressList.get(0).getLocality();
//                        str += addressList.get(0).getUrl();
//                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String s) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String s) {
//
//                }
//            });
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,10, locationListener);
//        }


//    _intent = getIntent(); // gets the previously created _intent
//
//    _title = _intent.getStringExtra("title"); // will return value of key "title"'
//    _address = _intent.getStringExtra("address"); // will return value of key "address"
//    _money_rating = _intent.getStringExtra("money_rating"); // will return value of key "money_rating"
//    _place_rating = _intent.getStringExtra("place_rating"); // will return value of key "place_rating"
//    _distance = _intent.getStringExtra("distance"); // will return value of key "distance"
//
//    TextView details = (TextView) findViewById(R.id.title);
//
//        if(_title == null){
//        //then do nothing
//    }
//        else{
//            details.setText(_title);
//            details = (TextView) findViewById(R.id.address);
//            details.setText(_address);
//            details = (TextView) findViewById(R.id.money_rating);
//            details.setText(_money_rating);
//            details = (TextView) findViewById(R.id.place_rating);
//            details.setText(_place_rating);
//            details = (TextView) findViewById(R.id.distance);
//            details.setText(_distance);
//
//        }


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
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


    }
    //    map.setOnClickListener(new View.OnClickListener()){
//        @Override
//        public void onClick(View mapView) {
//            LatLng latLng = new LatLng(mLastknownLocation.getLatitude(),
//                    mLastknownLocation.getLongitude());
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//        }
//    }
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







