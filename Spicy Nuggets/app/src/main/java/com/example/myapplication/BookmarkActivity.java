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
            new LocationItem(R.id.item_1, "CHINA FINE TASTE", "WA, Yeowo, Dally Road 114", "$", "2.5 out of 10", "7.3 miles"),

            new LocationItem(R.id.item_2, "JOEMAMA JUICE", "NY, Amherst, Audabon Creek 45", "$$$$$", "5.1 out of 10", "32.4 miles"),

            new LocationItem(R.id.item_3, "CHIPOTLE", "Ma, Waterson, Agario Street 65, E3", "$$$$$$", "8.3 out of 10", "14.8 miles"),

            new LocationItem(R.id.item_4, "DUFFS", "HO, Moure, Simp Sins Road", "$$", "7.1 out of 10", "11.4 miles"),

            new LocationItem(R.id.item_5, "NO FLEXY ZONE", "MA, Waterson, Strea Road 4830", "$$$", "8.8 out of 10", "32.8 miles"),

            new LocationItem(R.id.item_6, "SALLY'S CAT CAFE", "NY, Queens, Loofa Pkway 327", "$$$$", "5.2 out of 10", "84.4 miles"),

            new LocationItem(R.id.item_7, "GIN GIN", "NI, Jaollo, Houal Ave 6676", "$$$$$$", "6.3 out of 10", "74.2 miles"),

            new LocationItem(R.id.item_8, "POPPAH MOLLY", "DE, Jay, Kalelid 356", "$$", "7.4 out of 10", "41.2 miles"),

            new LocationItem(R.id.item_9, "ALTER'S PIZZA", "MA, Arshe, Meallow Ave 831", "$$$", "4.3 out of 10", "63.3 miles"),

            new LocationItem(R.id.item_10, "BLYATMANNS", "RU, Shien, Vellage Bouys Street 2281", "$$$$", "9.5 out of 10", "44.2 miles"),

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

