package com.example.myapplication;

public class Questions {

    private String nQuestions [] = {
            "Any specific type of food restrictions?",
            "How far in meters?",
            "How expensive should this food place be? (one $ is around $10)",

    };

    private String nAnswers [][] = {
            {"Vegetarian", "Vegan", "Kosher", "None"},
            {"< 5000", "< 15000", "< 30000", "50000+"},
            {"$"," $$", "$$$", "$$$$+"},
    };

    public String[] getSurvey(){
        return nQuestions;
    }

    public int getSurveySize(){
        return nQuestions.length;
    }

    public String[][] getSurveyAnswers(){
        return nAnswers;
    }

    public String getQuestion(int a) {
        String question = nQuestions[a];
        return question;
    }

    public String getAnswer(int a,int b) {
        String Answer1 = nAnswers[a][b];
        return Answer1;
    }



}
