package com.example.testplacesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int i = 0;
    private TextView txtView;
    private Handler hdlr = new Handler();

    String apiKey = "insert API Key";

    SharedPreferences sharedPref;

    final MainActivity main = this;
    Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btnShow); // replace this button with the start! button. The loading will begin after u click it.
        init(); // instantiate initial progress bar

        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 3000);       // Alternative method to display progress commented out below


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                i = progressBar.getProgress();
//                new Thread(new Runnable() {
//                    public void run() {
//                        while (i < 100) {
//                            i += 1;
//                            // Update the progress bar and display the current value in text view
//                            hdlr.post(new Runnable() {
//                                public void run() {
//                                    progressBar.setProgress(i);
//                                   // txtView.setText(i+"/"+progressBar.getMax());
//                                }
//                            });
//                            try {
//                                // Sleep for 100 milliseconds to show the progress slowly.
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//            }
//        });


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

    private void init() {
        this.progressBar = findViewById(R.id.pBar);   // pBar is the circle layout ID in activity main.xml
    }

    private void setHashMap(Map<String, String> val){
        map = val;
    }
}