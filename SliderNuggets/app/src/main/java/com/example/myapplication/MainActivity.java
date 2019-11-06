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

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        _map = sharedPref.getAll();
        System.out.println("HERE 1: "+ _map);
        //commented out temporarily for development
        if(!sharedPref.getBoolean("SurveyTaken",false)){
//            System.out.println("HERE 1: " + sharedPref.getBoolean("SurveyTaken",false));
            Intent surveyIntent = new Intent(this, SurveyActivity.class);
            startActivity(surveyIntent);
        }

    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for location services")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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

    /** Called when the user taps the go buttonBackgroundRipple */
    public void callResult(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(MainActivity.this, "You have already granted this permission!",
            //        Toast.LENGTH_SHORT).show();
            Intent result = new Intent(this, MapsActivity.class);
//            result.putExtra("fromWhere", "main_menu");
            startActivity(result);
        } else {
            requestStoragePermission();
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