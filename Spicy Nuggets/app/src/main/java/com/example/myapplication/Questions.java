package com.example.myapplication;

public class Questions {

    private String nQuestions [] = {

            "Question 2",
            "Question 3",
            "Question 4",
            "Question 5"
    };

    private String nAnswers [][] = {
            {"2Answer 1", "2Answer 2", "2Answer 3", "2Answer 4"},
            {"3Answer 1", "3Answer 2", "3Answer 3", "3Answer 4"},
            {"4Answer 1"," 4Answer 2", "4Answer 3", "4Answer 4"},
            {"5Answer 1", "5Answer 2", "5Answer 3", "5Answer 4"}
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
