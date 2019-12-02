package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;

public class HistoryActivity extends AppCompatActivity {

    /*
        a lock that prevents multiple viewMores activated when only one is allowed at a time
    */
    Boolean _viewMorePressed = false;

    //UI
    TextView _view;



    //Data
    SharedPreferences _sharePref;
    Intent _intent;
    Gson _gson;
    ArrayList<LocationItem> _history;
    Queue<LocationItem> _historyData;
    int[] _IDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        _IDs = new int[]{
            R.id.item_1,
            R.id.item_2,
            R.id.item_3,
            R.id.item_4,
            R.id.item_5,
            R.id.item_6,
            R.id.item_7,
            R.id.item_8,
            R.id.item_9,
            R.id.item_10
        };

        _sharePref = PreferenceManager.getDefaultSharedPreferences(this);
        _intent = new Intent(this, MapsActivity.class);
        _gson = new Gson();

        _historyData = getData("History");
        _history = new ArrayList<LocationItem>();
//        temp commented to do later
//        Upon creating, display the data for each view
        if(_historyData == null){
            //do nothing because there's nothing in history recorded
            System.out.println("NO HISTORY");
        }
        else{
            int sizeOfHistory = _historyData.size();
            for(int i = 0; i < sizeOfHistory; i++){
                _view = (TextView) findViewById(_IDs[i]);
                _history.add(_historyData.poll());
                _view.setText(_history.get(i).getTitle());
            }
        }

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
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        _viewMorePressed = data.getBooleanExtra("viewMorePressed", true);
    }

    /**
     * Note: _viewMorePressed is a lock that prevents multiple result pages from spamming clicking multiple recent searches
     * viewMore brings the user to result page after clicking on one of the recent searches
     * Should only just call MapActivity to display the location and only the location, no search.
     * @param view: The recent location object
     */
    public void viewMore(View view) {

        //temp commented to deal w/ later
        if(_viewMorePressed){
            System.out.println("Already viewing!");
            return;
        }
        _viewMorePressed = true;

        TextView thisItem = (TextView) findViewById(view.getId());

        //REQUIRES THE LOCATIONITEM OBJECT NOT THE FUCKING TEXTVIEW LOOOOOOOL
        for (int i = 0; i < _history.size(); i++) {
            LocationItem placeInfo = _history.get(i);
            System.out.println("info: " + placeInfo.getTitle());
            if (view.getId() == placeInfo.getID()) {
                System.out.println("FOUND THE PRESSED VIEW HISTORY ITEM");
                _intent.putExtra("title", placeInfo.getTitle());
                _intent.putExtra("lat", placeInfo.getLATITUDE());
                _intent.putExtra("lng", placeInfo.getLONGITUDE());
                _intent.putExtra("money_rating", placeInfo.getMoneyRating());
                _intent.putExtra("place_rating", placeInfo.getPlaceRating());
                _intent.putExtra("fromWhere", "history");
                break;
            }
        }

        _intent.putExtra("viewMorePressed", true);

        startActivityForResult(_intent, 0);
    }

    public void goMenu(View view) {
        finish();
    }

    private void storeData(String key, ArrayList<LocationItem> arr) {
        if(_history == null){
            return;
        }
        SharedPreferences.Editor editor = _sharePref.edit();
        String value = _gson.toJson(arr);
        editor.putString(key, value);
        editor.apply();


    }

    private Queue<LocationItem> getData(String key) {
        return _gson.fromJson(_sharePref.getString(key, null), new TypeToken<Queue<LocationItem>>(){}.getType());
    }
}


