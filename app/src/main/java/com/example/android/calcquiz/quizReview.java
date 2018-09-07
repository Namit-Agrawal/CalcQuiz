package com.example.android.calcquiz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class quizReview extends AppCompatActivity {

    private String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_review);

        //Create a textView to display question, user's answers
        //and correct answers
        TextView t = (TextView) findViewById(R.id.sumbitMessage);

        //Get all the information from MainActivity to be processed
        if(getIntent().getExtras()!=null)
            value = getIntent().getExtras().getString("Message");
        Questions[] quest = (Questions[]) getIntent().getSerializableExtra("myArray");
        String[] guesses = getIntent().getStringArrayExtra("guesses");
        StringBuilder list = new StringBuilder();

        //Create the format in which the information will be displayed
        for(int x = 0; x<quest.length; x++)
        {
            list.append("Question: ");
            list.append(quest[x].getQuestion());
            list.append("\n");
            list.append("Your answer: ");
            list.append(guesses[x]);
            list.append("\n");
            list.append("Correct answer: ");
            list.append(quest[x].getCorrectAns());
            list.append("\n\n");
        }
        t.setText(value+"\n\n"+"Quiz Review"+"\n"+list.toString()+"\n\n"+"Click the solution button for a more detailed overview."+"\n\n");

    }

    //This method allows the user to share their score through email
    public void shareResults(View v)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        //set email subject and message
        intent.putExtra(Intent.EXTRA_SUBJECT, "Calculus Quiz Results");
        intent.putExtra(Intent.EXTRA_TEXT, value);
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
    }

    //This method allows the user to view detailed video solution on selected
    //questions on Youtube by using an intent
    private void showSolutions(String id)
    {
        //Use app and web Intent just in case user doesn't have youtube app
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v="+id));

        //try app first, if it doesn't work, go to web
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    //This method shows derivative solution 1
    public void showDerivativeSolution1(View v)
    {
        showSolutions("i_TyTVYoM9Q");
    }

    //This method shows derivative solution 2
    public void showDerivativeSolution2(View v)
    {
        showSolutions("LUcpMn-yL6c");
    }

    //This method shows integral solution 1
    public void showIntegralSolution1(View v)
    {
        showSolutions("O9p9ojtZby4");
    }

    //This method shows limit solution 1
    public void showLimitSolution1(View v)
    {
        showSolutions("7xhJ5qRdrOE");
    }

    //This method shows limit solution 2
    public void showLimitSolution2(View v)
    {
        showSolutions("Tbe6RqjtSSY");
    }

    //This method shows limit solution 3
    public void showLimitSolution3(View v)
    {
        showSolutions("W2hnfGQf3n4");
    }

    //This method shows all Topic solution 8
    public void showAllTopicSolution8(View v)
    {
        showSolutions("GY4YNMa_Mf8");
    }

    //This method shows all Topic solution 9
    public void showAllTopicSolution9(View v)
    {
        showSolutions("CbFLswp1sLk");
    }

    //This method shows derivative solution 6
    public void showDerivativeSolution6(View v)
    {
        showSolutions("3jYPa6_9RXI");
    }
}
