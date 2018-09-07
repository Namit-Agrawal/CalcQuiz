package com.example.android.calcquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class HomeScreen extends AppCompatActivity {

    private boolean isClassic, isTimed, pressed;
    private Button classic, timed;
    private String[] topics = {"Choose a topic","Derivatives", "Limits", "Integrals", "All topics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        classic = (Button) findViewById(R.id.classic);
        timed = (Button) findViewById(R.id.timed);

        //Create a spinner
        Spinner topicChoices = (Spinner) findViewById(R.id.topic);
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, topics);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topicChoices.setAdapter(adapt);
        topicChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            //Once an item is selected on list send intent to main Activity
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String var = (String)parent.getItemAtPosition(position);
                if(isClassic&&pressed)
                {
                    sentIntent(false, var);
                }
                else if(isTimed&&pressed)
                {
                    sentIntent(true, var);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //This method set classic mode to true and highlights the button
    public void classic(View v)
    {
        isClassic=true;
        isTimed=false;
        pressed=true;
        classic.setBackgroundResource(R.drawable.select_button);
        timed.setBackgroundResource(R.drawable.rounded_corner);
    }

    //This methods set timed mode to true and highlights the button
    public void timedQuiz(View v)
    {
        isClassic = false;
        isTimed=true;
        pressed=true;
        timed.setBackgroundResource(R.drawable.select_button);
        classic.setBackgroundResource(R.drawable.rounded_corner);
    }

    //This method sends and intent based on what mode and topic is chosen
    private void sentIntent(boolean var, String topicType)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("type", topicType);
        intent.putExtra("ifTimed", var);
        startActivity(intent);
    }

    //This method is triggered when user wants to see the instructions
    public void instructions(View v)
    {
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }
}
