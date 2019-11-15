package com.example.testplacesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String apiKey = "insert API Key";
    TextView textView;
    SharedPreferences sharedPref;

    final MainActivity main = this;
    Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String keywords = "University%20at%20Buffalo",
                fields = "name,place_id,geometry/location",
                location = "43.0008093" + "," + "-78.7889697",
                radius = "30",
                type = "restaurant",
                keyword = "food";

//        String locationbias = "point:lat,lng";

        String searchRequest ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" +
                keywords + "&inputtype=textquery&fields=" +
                fields + "&locationbias=" + "&key=" +
                apiKey +"\n";

        String NearbySearchRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                location + "&radius=" +
                radius + "&type=" +
                type + "&keyword=" +
                keyword + "&key=" +
                apiKey;

//        String NearbySearchRequest = getResources().getString(R.string.SearchResults);

        HttpReq req = new HttpReq(NearbySearchRequest, this);
        req.execute();

    }

    private void setHashMap(Map<String, String> val){
        map = val;
    }
}