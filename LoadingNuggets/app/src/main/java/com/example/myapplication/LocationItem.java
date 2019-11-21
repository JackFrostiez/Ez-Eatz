package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class LocationItem {
    private int ID;
    private double LATITUDE;
    private double LONGITUDE;
    private String TITLE,
//            ADDRESS,
            MONEY_RATING,
            PLACE_RATING;
//            DISTANCE;

    LocationItem (){
        TITLE = null;
//        ADDRESS = "";
        MONEY_RATING = null;
        PLACE_RATING = null;
//        DISTANCE = "";
    }

    void setID(int viewID){ ID = viewID; }

    void setTitle(String title){
        TITLE = title;
    }

    void setMoneyRating(String money_rating){
        MONEY_RATING = money_rating;
    }

    void setPlaceRating(String place_rating){
        PLACE_RATING = place_rating;
    }

    int getID(){ return ID; }

    void setLATITUDE(double LATITUDE) { this.LATITUDE = LATITUDE; }

    void setLONGITUDE(double LONGITUDE) { this.LONGITUDE = LONGITUDE; }

    String getTitle(){
        return TITLE;
    }

    String getMoneyRating(){ return MONEY_RATING; }

    String getPlaceRating(){
        return PLACE_RATING;
    }

    double getLATITUDE() { return LATITUDE; }

    double getLONGITUDE() { return LONGITUDE; }

//    void setAddress(String address){ ADDRESS = address; }
//
//    void setDistance(String distance){ DISTANCE = distance; }
//
//    String getAddress(){ return ADDRESS; }
//
//    String getDistance(){ return DISTANCE; }

}
