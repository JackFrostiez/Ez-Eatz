package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//Custom HTTP Handler
public class HttpReq extends AsyncTask<Void, Void, Void> {

    String _url;
    MapsActivity _m;
    Gson _gson = new Gson();

    //SharedPreference Object that has the defaultSharedPreference so the whole app can share data
    SharedPreferences sharedPref;


    public HttpReq(String url, MapsActivity mapsAct){
        _m = mapsAct;
        _url = url;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(_m);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        System.out.println("DOING SOMETHINGGGGGGGGGGGGGGG");

        URLConnection request;
        InputStreamReader input;

        try{
            URL url = new URL(_url);
            request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            input = new InputStreamReader((InputStream) request.getContent());
            JsonElement json = jp.parse(input); //Convert the input stream to a json element

            JsonObject root = json.getAsJsonObject(); //May be an array, may be an object.
            System.out.println("root: " + root);

            JsonArray results = root.getAsJsonArray("results");
            System.out.println(results);

            if(results.size() == 0){
                throw new NoDataException();
            }

            //setup the list of places
            ArrayList<LocationItem> places = new ArrayList<LocationItem>();

            //gets each place's block of data
            for(JsonElement place : results){

                //create a place object to set its data and then be added to the list afterwards
                LocationItem item = new LocationItem();

                JsonObject placeInfo = place.getAsJsonObject();

                //in this place's  data, look for name category
                System.out.println(placeInfo.get("name"));
                item.setTitle(placeInfo.get("name").toString().replace("\"", ""));

                //get location data
                JsonObject placeLocation = placeInfo.
                        getAsJsonObject("geometry").
                        getAsJsonObject("location");

                //pull out lat/lng from location data
                item.setLATITUDE(placeLocation.get("lat").getAsDouble());
                item.setLONGITUDE(placeLocation.get("lng").getAsDouble());
                System.out.println("LatLng: " +
                        placeLocation.get("lat") + "," +
                        placeLocation.get("lng"));

                //get place rating if possible
                JsonElement rating = placeInfo.get("rating");
                if(rating != null){
                    item.setPlaceRating(rating.getAsString());
                    System.out.println("rating: " + rating);

                }
                else{
                    item.setPlaceRating("N/A");
                    System.out.println("N/A");
                }

                //get price level rating if possible
                JsonElement price_level = placeInfo.get("price_level");
                if(price_level != null){
                    item.setMoneyRating(price_level.getAsString());
                    System.out.println("money: " + price_level);
                }
                else{
                    item.setMoneyRating("N/A");
                    System.out.println("N/A");
                }

                System.out.println("====================================");
                places.add(item);

            }

            //before we store, first convert the list to Json string representation
            //because sharedPref only stores primitive data
            String strFormat = _gson.toJson(places);

            //finally store the retrieved data
            store("NearbyPlacesData", strFormat);

            input.close();
        }
        catch(IOException io){
            System.out.println("IT DON'T WORKKKKKKKK");
            cancel(true);

        }
        catch(NoDataException nde){
            System.out.println("THERE IS NO DATAAAAAAAAAA");
            cancel(true);
            return null;
        }

        System.out.println("DONEEEEEEEE");
        cancel(true);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    void store(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    ArrayList<LocationItem> getData(String key) {
        return _gson.fromJson(sharedPref.getString(key, null), new TypeToken<ArrayList<LocationItem>>(){}.getType());
    }


}

class NoDataException extends Exception {
    public NoDataException() {
        //do nothing
    }
}