package com.example.android.calcquiz;

import android.content.Intent;

import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity  {

    //instance variables
    private TextView ques, time, questNum;
    private RadioButton a, b, c, d, selectedButton;
    private int[] guesses;
    private String topicType;
    private String[] guessesStr;
    private RadioGroup choices;
    private Questions[] quest;
    private int x;
    private Button next, submit, previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean ifTimed=false;

        //Make sure the extra values from intent have data
        if(getIntent().getExtras()!=null)
        {
            ifTimed = getIntent().getExtras().getBoolean("ifTimed");
            topicType = getIntent().getExtras().getString(("type"));
        }

        //If user wants to be timed
        timeQuiz(ifTimed);

        //initialize all views by finding thier Ids
        initializeVal();

        //make the appropriate questions based on user's choice of topic
        if(topicType.equals("Derivatives"))
            derivatives();
        else if(topicType.equals("Integrals"))
            integrals();
        else if(topicType.equals("Limits"))
            limits();
        else
            allType();

        if(x==0)
        {
            questions();
            questNum.setText("Question: " + (x + 1) + "/" + quest.length);
        }
    }

    //Method to time the quiz
    private void timeQuiz(boolean val)
    {
        if(val)
        {
            time = (TextView) findViewById(R.id.timer);
            time.setBackgroundResource(R.drawable.circle);
            //Set the timer to be 10 minutes
            CountDownTimer timer = new CountDownTimer(600*1000, 1000) {
                @Override
                public void onTick(long l) {
                    //Format the time to 00:00
                    if((l/1000)%60<10)
                        time.setText(""+l/1000/60+":0"+(l/1000)%60);
                    else
                        time.setText(""+l/1000/60+":"+(l/1000)%60);
                }

                @Override
                public void onFinish() {
                    submitQ();
                }
            };
            timer.start();
        }
    }

    private void initializeVal()
    {
        ques = (TextView) findViewById(R.id.question);
        a = (RadioButton) findViewById(R.id.a);
        b = (RadioButton) findViewById(R.id.b);
        c = (RadioButton) findViewById(R.id.c);
        d = (RadioButton) findViewById(R.id.d);
        questNum = (TextView) findViewById(R.id.questionNumber);
        choices = (RadioGroup) findViewById(R.id.choices);
        next = (Button) findViewById(R.id.next);
        submit = (Button) findViewById(R.id.submit);
        previous = (Button) findViewById(R.id.previous);
        guesses = new int[10];
        guessesStr = new String[10];
    }
    //This methods stores and highlights the user's answers
    public void ifCorrect(View v)
    {
        //Get the id of the checked button
        int id = choices.getCheckedRadioButtonId();
        guesses[x] = id;
        selectedButton = (RadioButton) findViewById(id);

        //Highlight the user's answer
        for(int x = 0; x<choices.getChildCount(); x++)
        {
            View temp1 = choices.getChildAt(x);
            if(temp1.getId()!=choices.getCheckedRadioButtonId())
            {
                temp1.setBackgroundResource(R.drawable.rounded_corner);
            }
        }
        selectedButton.setBackgroundResource(R.drawable.select_button);
        guessesStr[x] = selectedButton.getText().toString();

        if(x!=quest.length-1)
            next.setEnabled(true);
        previous.setEnabled(true);
    }

    //The method is used to navigate to the next question
    public void nextQues(View v)
    {
        //Increment x to go to next question, and clear all choices
        selectedButton.setBackgroundResource(R.drawable.rounded_corner);
        choices.clearCheck();
        x++;
        questNum.setText("Question: "+(x+1)+"/"+quest.length);

        //display the questions and answer choices
        questions();

        //Enable and disable appropriate navigation buttons
        if(x>=quest.length-1) {
            submit.setEnabled(true);
            next.setEnabled(false);
        }
        if(x>=1)
            previous.setEnabled(true);

    }

    //Method that submits the quiz after the timer has reached 0 seconds
    private void submitQ() {
        submitQuiz(new View(this));
    }

    //The method is used to navigate to the previous questions
    public void prevQues(View v)
    {
        //decrement x and display the questions and answer choices
        x--;
        questNum.setText("Question: "+(x+1)+"/"+quest.length);
        if(x==0)
            previous.setEnabled(false);
        if(x<quest.length-1)
            next.setEnabled(true);
        questions();
    }

    //This method is used to set the questions and answer choices on the interface
    //by using the appropriate views
    private void questions()
    {
        ques.setText(quest[x].getQuestion());
        a.setText(quest[x].getAnswerChoice1());
        b.setText(quest[x].getAnswerChoice2());
        c.setText(quest[x].getAnswerChoice3());
        d.setText(quest[x].getAnswerChoice4());
    }

    //THis method is used to submit the quiz
    public void submitQuiz(View v)
    {
        //get the correct array of answers based on user's topic of choice
        TypedArray answers = getResources().obtainTypedArray(R.array.all_choices);
        if(topicType.equals("Derivatives"))
            answers = getResources().obtainTypedArray(R.array.derivatives);
        else if(topicType.equals("Integrals"))
            answers=getResources().obtainTypedArray(R.array.integrals);
        else if(topicType.equals("Limits"))
            answers=getResources().obtainTypedArray(R.array.limits);

        //Determine which questions the user got correct
        int numCorr=0;
        for(int x = 0; x<quest.length; x++)
        {
            if(guesses[x]==answers.getResourceId(x,0))
                numCorr++;

            //Incase the user decided to skip question
            if(guesses[x]==0)
                guessesStr[x]="No answer";
        }
        answers.recycle();

        //Send the information to the quizReview activity to show user their answers and
        //the correct answers
        Intent intent = new Intent(this, quizReview.class);
        intent.putExtra("Message", "You got "+ numCorr+" correct out of "+quest.length);
        intent.putExtra("myArray", quest);
        intent.putExtra("guesses", guessesStr);
        startActivity(intent);
    }

    //This method creates a quiz inclusive of all topics
    private void allType()
    {
        quest= new Questions[10];
        quest[0]= new Questions("What is the derivative of 5x?", "5", "0","5x",
                "Undefined", "5");
        quest[1]= new Questions("What is the derivative of 10x\u00B2?", "20x\u00B2", "40x","20x",
                "0", "20x");
        quest[2]= new Questions("What is the integral of 12x\u00B2?",
                "20x\u00B2", "4x\u00B3","4x\u00B3 + C", "4x", "4x\u00B3 + C");
        quest[3]= new Questions("Which two mathematicians are jointly credited for creating calculus?",
                "Issac Newton and Daniel Bernoulli", "Gottfried Leibniz and Issac Newton",
                "Leonhard Euler and Gottfried Leibniz", "Daniel Bernoulli and Leonhard Euler",
                "Gottfried Leibniz and Issac Newton");
        quest[4]= new Questions("What is the limit as x approaches 1 of (x\u00B3-1)/(x-1)\u00B2?",
                "3", "0","6", "The limit does not exist", "The limit does not exist");
        quest[5]= new Questions("What is the limit as x approaches "+ DecimalFormatSymbols.getInstance().getInfinity()+
                " of 7/(x-1)\u00B2?", "3", "0","5", "The limit does not exist", "0");
        quest[6]= new Questions("What is the limit as x approaches -"+ DecimalFormatSymbols.getInstance().getInfinity()+"" +
                " of (x+7)/(3x+5)?", "3", "1/3","6", "The limit does not exist", "1/3");
        quest[7]= new Questions("For what values of x is the function (x\u00B2+3x+5)/(x\u00B2+3x-4) continous?",
                "All values of x except 1 and 4", "All values of x except -1 and 4",
                "All values of x except 1 and -4", "All values of x except -1 and -4",
                "All values of x except 1 and -4");
        quest[8]= new Questions("Find a line tangent to the graph y = x\u00B2+4 at x=2?", "2x", "4x","2x+2",
                "4x+4", "4x");
        quest[9]= new Questions("If the position of a car is modeled by the equation x(t)= 4t\u00B2+2t+4 where t is time, find" +
                " the accerlation of the car at t = 2?", "4m/s\u00B2", "2m/s\u00B2","6m/s\u00B2",
                "8m/s\u00B2", "8m/s\u00B2");
    }

    //This method creates a quiz of derivatives
    private void derivatives()
    {
        quest= new Questions[8];
        quest[0]= new Questions("What is the derivative of 5x?", "5", "0","5x", "Undefined",
                "5");
        quest[1]= new Questions("What is the derivative of 10x\u00B2?", "20x\u00B2", "40x","20x",
                "0", "20x");
        quest[2]= new Questions("What is the derivative of sin(x)?", "cos(x)", "-cos(x)", "sin(2x)",
                "-sin(2x)", "cos(x)");
        quest[3]= new Questions("What is the derivative of x(x-3)(x\u00B2+1)?", "4x\u00B3+12x\u00B2+3x",
                "4x\u00B3-9x\u00B2+2x-3", "-4x\u00B3+9x\u00B2+2x-3",
                "4x\u00B3-9x\u00B2+2x-3", "4x\u00B3-9x\u00B2+2x-3");
        quest[4] = new Questions("What is derivative of velocity?", "position", "jerk", "accerlation",
                "time", "accerlation");
        quest[5]= new Questions("If the position of a car is modeled by the equation x(t)= 4t\u00B2+2t+4 where t is time, " +
                "find the accerlation of the car at t = 2?", "4m/s\u00B2", "2m/s\u00B2","6m/s\u00B2",
                "8m/s\u00B2", "8m/s\u00B2");
        quest[6]= new Questions("What is the derivative of (x\u00B2+3x+2)\u00B3?", "3(x\u00B2+3x+2)\u00B3(2x+3)",
                "2(x\u00B2+3x+2)\u00B2(2x)","3(x\u00B2+3x+2)\u00B2(2x+3)","3(x²+3x+2)\u00B2+5",
                "3(x\u00B2+3x+2)\u00B2(2x+3)");
        quest[7]= new Questions("What is the derivative of sin\u00B3(x)?", "3sin(2x)cos(x)-3sin\u00B3(x)",
                "6cos\u00B3", "3sin\u00B2cos(x)-3sin\u00B3(x)", "6sin(2x)cos(x)-3sin\u00B3(x)",
                "3sin(2x)cos(x)-3sin\u00B3(x)");
    }

    //This method creates a quiz of integrals
    private void integrals()
    {
        quest = new Questions[8];
        quest[0]= new Questions("What is the integral of 12x\u00B2?",
                "20x\u00B2", "4x\u00B3","4x\u00B3 + C", "4x", "4x\u00B3 + C");
        quest[1]= new Questions("What is the integral of (6x\u00B2)/(6x\u00B3+5)?", "x\u00B2+C",
                 "1/3ln(6x\u00B3+5)+C", "1/3ln(6x\u00B3+5)", "1/4ln(6x\u00B3+5)", "1/3ln(6x\u00B3+5)+C");
        quest[2]= new Questions("What is integral of sin(x) from 0 to pi", "0", "1","2", "-1",
                "2");
        quest[3]= new Questions("What is the integral of xsin(x\u00B2)?", "-1/2cos(x)+C", "1/2cos(x)+C",
                "1/2cos(x\u00B2)+C", "-1/2cos(x\u00B2)+C", "-1/2cos(x\u00B2)+C");
        quest[4]= new Questions("What is the integral of tan\u00B3(x) from 0 to pi/4?", ".5-.5ln(2)",
                "1-ln(2)", "Not possible", ".5-ln(2)", ".5-.5ln(2)");
        quest[5]= new Questions("What is integral of velocity?", "jerk", "accerlation", "position",
                "time", "position");
        quest[6]= new Questions("What is the integral of x\u00B3 from 3 to 6?","275", "300.75",
                "189", "303.75", "303.75");
        quest[7]= new Questions("Given the velocity function of an object v(t)=3t\u00B2+5 in m/s, find the " +
                 "position of the object at t=4s?", "69m", "42m", "0m", "5m","69m");
    }

    //This method creates a quiz of limits
    private void limits()
    {
        quest = new Questions[10];
        quest[0]= new Questions("What is the limit as x approaches 1 of (x\u00B3-1)/(x-1)\u00B2?", "3", "0","6",
                "The limit does not exist", "The limit does not exist");
        quest[1]= new Questions("What is the limit as x approaches "+ DecimalFormatSymbols.getInstance().getInfinity()+" of 7/(x-1)\u00B2?",
                "3", "0","5", "The limit does not exist", "0");
        quest[2]= new Questions("What is the limit as x approaches -"+ DecimalFormatSymbols.getInstance().getInfinity()+" of (x+7)/(3x+5)?",
                "3", "1/3","6", "The limit does not exist", "1/3");
        quest[3]= new Questions("What is the limit as x approaches 0 of (sin(x))/(x)?", "0", "Undefined", "1",
                "-1", "1");
        quest[4]= new Questions("What is the limit as x approaches 0 of (sin(x\u00B2))/(xtan(x))?", "-1", "pi",
                "0", "1", "1");
        quest[5]= new Questions("What is the limit as x approaches 0 of (3+ln(x))/(x\u00B2+7)?", "0",
                "Undefined", "1", "1/2", "0");
        quest[6]= new Questions("What is the limit as x approaches 0 from the right of (xln(x))?", "Undefinied", "0",
                "1", "-1", "0");
        quest[7]= new Questions("What is the limit as x approaches 0 of (xtan(x))/(sin(3x))?", "Undefined", "0",
                "1", "pi", "0");
        quest[8]= new Questions("What is the limit as x approaches 0 of x\u00B2+5x+7?", "7", "2", "13",
                "10", "7");
        quest[9]= new Questions("What is the limit as x approaches 0 of (x\u00B3+2x)/(4x\u00B2+3x)?", "1/3", "2/3",
                "1", "4/3", "2/3");
    }
}