package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {


    /**
     * This communicates with the slider in the app
     */
    SeekBar _distanceBar;

    /**
     * SharedPreference Object that has the defaultSharedPreference so the whole app can share data
     */
    SharedPreferences sharedPref;

    TextView _meter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        _distanceBar = (SeekBar)findViewById(R.id.seekBar);

        if(sharedPref.getInt("Slider", 0) != 0){
            _distanceBar.setProgress(sharedPref.getInt("Slider", 0));
            _meter = (TextView)findViewById(R.id.meter);
            _meter.setText(Integer.toString(sharedPref.getInt("Slider", 0)) + " miles");

        }
        _distanceBar.setMax(100);

        _distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                TextView meter = (TextView)findViewById(R.id.meter);

                meter.setText(Integer.toString(progressChangedValue) + " miles");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity.this, "Current distance is : " + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                //finally save the user's set radius
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("Slider", progressChangedValue);
                editor.apply();
            }
        });

    }

    public void goMenu(View view) {
        finish();
    }
}
