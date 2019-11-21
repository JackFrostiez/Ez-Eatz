package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    /**
     * SharedPreference Object that has the defaultSharedPreference so the whole app can share data
     */
    SharedPreferences sharedPref;
    /**
     * mapper object that will hold the current keys made in the sharepref
     */
    Map<String,?> _map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //commented out temporarily for development
//        if(!sharedPref.getBoolean("SurveyTaken",false)){
//            Intent surveyIntent = new Intent(this, SurveyActivity.class);
//            startActivity(surveyIntent);

        //Users MUST grant permissions inorder to continue to use this map
        requestPermissions();

        //        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        _map = sharedPref.getAll();
//        System.out.println("HERE 1: "+ _map);
//        }

    }
    private void requestPermissions() {
        //means that it you should show some reason/rationale to the user for why permissions are necessary after they deny them
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permissions Required")
                    .setMessage("This feature requires location and storage permissions.")
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        }
                    })
                    .setNegativeButton("Close App", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAndRemoveTask(); //close app
                            dialog.dismiss();

                        }
                    })
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, " Permission was DENIED", Toast.LENGTH_SHORT).show();
//                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.the_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings);
                return true;
            case R.id.history:
                Intent history = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(history);
                return true;
            case R.id.bookmark:
                Intent bookmark = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(bookmark);
                return true;
            case R.id.survey:
                Intent survey = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(survey);
                return true;
        }
        return false;
    }

    public void CallSearch(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Your location needs to be enabled in order to use this feature.")
                        .setCancelable(false)
                        .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else{
                Intent maps = new Intent(this, MapsActivity.class);
                startActivity(maps);
            }
        } else {
            requestPermissions();
        }
    }

    public void callHistory(View view) {
        Intent history = new Intent(this, HistoryActivity.class);
        startActivity(history);
    }

    public void callBookmark(View view) {
        Intent bookmark = new Intent(this, BookmarkActivity.class);
        startActivity(bookmark);
    }
}

