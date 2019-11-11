package com.example.myapplication;

public class LocationItem {
    private int ID;
    private String TITLE, ADDRESS, MONEY_RATING, PLACE_RATING, DISTANCE;

    LocationItem (int id){
        ID = id;
        TITLE = "";
        ADDRESS = "";
        MONEY_RATING = "";
        PLACE_RATING = "";
        DISTANCE = "";
    }


    void setTitle(String title){
        TITLE = title;
    }

    void setAddress(String address){
        ADDRESS = address;
    }

    void setMoneyRating(String money_rating){
        MONEY_RATING = money_rating;
    }

    void setPlaceRating(String place_rating){
        PLACE_RATING = place_rating;
    }

    void setDistance(String distance){
        DISTANCE = distance;
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
