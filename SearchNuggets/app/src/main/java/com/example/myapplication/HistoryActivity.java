package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    /*
        a lock that prevents multiple viewMores activated when only one is allowed at a time
    */
    Boolean _viewMorePressed = false;

    ArrayList<LocationItem> items;

    Intent _intent;

    //SharedPreference Object that has the defaultSharedPreference so the whole app can share data
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        _intent = new Intent(this, MapsActivity.class);


        TextView view;

        //get the viewed list of places
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //temp commented to do later
        //Upon creating, display the data for each view
//        for(int i = 0; i < items.size(); i++){
//
//        }

    }

    /**
     * Note: An error highlighting the method name could be okay and should be ignored
     * onActivityresult returns an intent with data that was performed in another activity
     * @param requestCode: An integer to distinguish what was the activity about
     * @param resultCode: An integer to distinguish the type of result
     * @param data: Contains key-values passed from another activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        _viewMorePressed = data.getBooleanExtra("viewMorePressed", true);
    }

    /**
     * Note: _viewMorePressed is a lock that prevents multiple result pages from spamming clicking multiple recent searches
     * viewMore brings the user to result page after clicking on one of the recent searches
     * @param view: The recent location object
     */
    public void viewMore(View view) {

        //temp commented to deal w/ later
//        if(_viewMorePressed){
//            System.out.println("Already viewing!");
//            return;
//        }
//        _viewMorePressed = true;
//
//        TextView thisItem = (TextView) findViewById(view.getId());
//
//        for (int i = 0; i < items.size(); i++) {
//            LocationItem info = items.get(i);
//            if (info.getID() == thisItem.getId()) {
//                System.out.println("info: " + info.getTitle());
//
//                _intent.putExtra("title", info.getTitle());
//                _intent.putExtra("money_rating", info.getMoneyRating());
//                _intent.putExtra("place_rating", info.getPlaceRating());
//                _intent.putExtra("fromWhere", "history");
//                break;
//            }
//        }
//
//        _intent.putExtra("viewMorePressed", true);
//
//        startActivityForResult(_intent, 0);
    }

    public void goMenu(View view) {
        finish();
    }
}


