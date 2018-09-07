package com.example.android.calcquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        TextView instructions = (TextView) findViewById(R.id.instructions);

        String instruct = "";
        StringBuilder sb = new StringBuilder();
        InputStream is = this.getResources().openRawResource(R.raw.instructions);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        if(is!=null)
        {
            try
            {
                instruct=br.readLine();
                while(instruct!=null)
                {
                    sb.append(instruct);
                    sb.append("\n");
                    instruct=br.readLine();
                }
                instructions.setText(sb);
                is.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
