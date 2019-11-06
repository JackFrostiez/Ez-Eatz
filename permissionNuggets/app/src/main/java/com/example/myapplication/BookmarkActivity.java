package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookmarkActivity extends AppCompatActivity {

    /*
        a lock that prevents multiple viewMores activated when only one is allowed at a time
    */
    Boolean _viewMorePressed = false;

    /*
        up to 10 recent searched places
    */
    LocationItem[] items = {
            new LocationItem(R.id.item_1),
            new LocationItem(R.id.item_2),
            new LocationItem(R.id.item_3),
            new LocationItem(R.id.item_4),
            new LocationItem(R.id.item_5),
            new LocationItem(R.id.item_6),
            new LocationItem(R.id.item_7),
            new LocationItem(R.id.item_8),
            new LocationItem(R.id.item_9),
            new LocationItem(R.id.item_10)
    };

    Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
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
                _intent.putExtra("fromWhere", "bookmark");
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

