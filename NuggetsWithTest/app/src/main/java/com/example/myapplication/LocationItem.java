package com.example.myapplication;

public class LocationItem {
    private int ID;
    private String TITLE, ADDRESS, MONEY_RATING, PLACE_RATING, DISTANCE;

    LocationItem (int id, String t, String a, String mr, String pr, String d){
        ID = id;
        TITLE = t;
        ADDRESS = a;
        MONEY_RATING = mr;
        PLACE_RATING = pr;
        DISTANCE = d;
    }

    int getID(){ return ID; }

    String getTitle(){
        return TITLE;
    }

    String getAddress(){
        return ADDRESS;
    }

    String getMoneyRating(){
        return MONEY_RATING;
    }

    String getPlaceRating(){
        return PLACE_RATING;
    }

    String getDistance(){
        return DISTANCE;
    }

}
