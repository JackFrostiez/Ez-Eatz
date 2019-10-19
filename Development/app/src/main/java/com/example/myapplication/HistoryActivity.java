package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    /*
        a lock that prevents multiple viewMores activated when only one is allowed at a time
    */
    Boolean _viewMorePressed = false;

    /*
        up to 10 recent searched places
    */
    LocationItem[] items = {
            new LocationItem(R.id.item_1, "MCDONALDS", "WO, Peteours, Dorney Road 9214", "$$$$", "5.5 out of 10", "9.3 miles"),

            new LocationItem(R.id.item_2, "WENDYS", "OH, Marriam, Wowee Street 20", "$$", "5.9 out of 10", "1.3 miles"),

            new LocationItem(R.id.item_3, "PHOVANA", "WA, Pherral, Reario Street 822, E3", "$$$$$", "9.5 out of 10", "11.8 miles"),

            new LocationItem(R.id.item_4, "JUMBA JUICE", "NY, Amherst, Audabon Creek 45", "$$", "5.1 out of 10", "18.4 miles"),

            new LocationItem(R.id.item_5, "ALTER'S PIZZA", "MA, Waterson, Styke Road 73", "$$$", "2.8 out of 10", "23.3 miles"),

            new LocationItem(R.id.item_6, "DAVE'S CYBER FOODS", "NY, Queens, Downy Street 54", "$$", "9.1 out of 10", "19.3 miles"),

            new LocationItem(R.id.item_7, "YUMMY YUM YUM", "NY, Brooklyn, Delly Road 20", "$$$", "7.1 out of 10", "10.4 miles"),

            new LocationItem(R.id.item_8, "TASTE OF CHINA", "NY, Bronx, SkyHigh Street 421", "$", "3.9 out of 10", "7.3 miles"),

            new LocationItem(R.id.item_9, "INSOMNIA COOKIES", "NY, Bronx, Hilly Street 91", "$$$$$$", "4.3 out of 10", "1.9 miles"),

            new LocationItem(R.id.item_10, "FERRACINO", "CA, Falta, Silly Valley 91", "$$$$$$$$$", "9.5 out of 10", "65.2 miles"),

    };

    Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//        _b = new Bundle();
        _intent = new Intent(this, MapsActivity.class);

        TextView view;

        for(int i = 0; i < items.length; i++){
            view = (TextView) findViewById(items[i].getID());
            view.setText(items[i].getTitle());
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
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        _viewMorePressed = data.getBooleanExtra("viewMorePressed", true);
    }

    /**
     * Note: _viewMorePressed is a lock that prevents multiple result pages from spamming clicking multiple recent searches
     * viewMore brings the user to result page after clicking on one of the recent searches
     * @param view: The recent location object
     */
    public void viewMore(View view) {

//        System.out.println("History: " + _viewMorePressed);

        if(_viewMorePressed){
            System.out.println("Already viewing!");
            return;
        }
        _viewMorePressed = true;

        TextView thisItem = (TextView) findViewById(view.getId());

        for (int i = 0; i < items.length; i++) {
            LocationItem info = items[i];
            if (info.getID() == thisItem.getId()) {
                System.out.println("info: " + info.getTitle());

                _intent.putExtra("title", info.getTitle());
                _intent.putExtra("address", info.getAddress());
                _intent.putExtra("money_rating", info.getMoneyRating());
                _intent.putExtra("place_rating", info.getPlaceRating());
                _intent.putExtra("distance", info.getDistance());
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
}


