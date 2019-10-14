package com.example.androidsurvey;

public class Questions {

    private String nQuestions [] = {
            "Method of Tansportation",
            "Select a food type",
            "Distance",
            "Price",

    };

    private String nAnswers [][] = {
            {"Walking", "Car", "Bike", "Personal Jet"},
            {"Vegetarian", "Vegan", "Tasty", "Kosher"},
            {"< 1 Mile", "< 5 Miles", "< 10 Miles", "> 10 Miles"},
            {"$"," $$ ", "$$$", "$$$$"},
    };

    public String getQuestion(int a) {
        String question = nQuestions[a];
        return question;
    }

    public String getAnswer(int a,int b) {
        String Answer1 = nAnswers[a][b];
        return Answer1;
    }



}
