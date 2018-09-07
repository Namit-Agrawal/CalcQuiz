package com.example.android.calcquiz;

import java.io.Serializable;

//Class to make a question
//Serializable implemented to allow objects to transffered and manipulated in
//different activities
public class Questions implements Serializable{


    private String question;
    private String anschoice1;
    private String anschoice2;
    private String anschoice3;
    private String anschoice4;
    private String correctAns;

    //Constructor
    public Questions(String question, String choice1, String choice2, String choice3, String choice4, String correct)
    {
        anschoice1=choice1;
        anschoice2=choice2;
        anschoice3=choice3;
        anschoice4=choice4;
        this.question=question;
        correctAns=correct;
    }

    //Accessor method to get the question
    public String getQuestion()
    {
        return question;
    }

    //Accessor method to get answerChoice1
    public String getAnswerChoice1()
    {
        return anschoice1;
    }

    //Accessor method to get answerChoice2
    public String getAnswerChoice2()
    {
        return anschoice2;
    }

    //Accessor method to get answerChoice3
    public String getAnswerChoice3()
    {
        return anschoice3;
    }

    //Accessor method to get answerChoice4
    public String getAnswerChoice4()
    {
        return anschoice4;
    }

    //Accessor method to get correct answer
    public String getCorrectAns()
    {
        return correctAns;
    }




}
