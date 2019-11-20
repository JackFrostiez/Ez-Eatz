package com.example.testplacesapi;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

//Custom HTTP Handler
public class HttpReq extends AsyncTask<Void,Void,Void> {


    String _url;
    MainActivity _m;

    public HttpReq(String url, MainActivity mainAct){
        _m = mainAct;
        _url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        System.out.println("DOING SOMETHINGGGGGGGGGGGGGGG");

        URLConnection request;

        try{
            URL url = new URL(_url);
            request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            InputStreamReader input = new InputStreamReader((InputStream) request.getContent());
            JsonElement json = jp.parse(input); //Convert the input stream to a json element

            JsonObject root = json.getAsJsonObject(); //May be an array, may be an object.
            System.out.println(root);

            JsonArray results = root.getAsJsonArray("results");
            System.out.println(results);

            //gets each place's block of data
            for(JsonElement place : results){

                //in this place's  data, look for name category
                System.out.println(place.getAsJsonObject().get("name"));
            }


            input.close();
        }
        catch(IOException io){
            System.out.println("IT DON'T WORKKKKKKKK");
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        System.out.println("DONEEEEEEEE");

    }

}