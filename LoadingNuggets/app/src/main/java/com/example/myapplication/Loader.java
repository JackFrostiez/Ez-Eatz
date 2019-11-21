package com.example.myapplication;

import android.os.AsyncTask;
import android.view.View;

//Custom HTTP Handler
public class Loader extends AsyncTask<Void, Void, Void> {

    View _pBar;

    public Loader(View pBar){
        _pBar = pBar;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }


}
