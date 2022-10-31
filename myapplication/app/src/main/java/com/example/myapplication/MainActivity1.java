package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity1 extends AppCompatActivity {

    private Button mvbtn1;
    private Button mvbtn2;
    private Button mvbtn3;



    public MainActivity1(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mvbtn1 = (Button)findViewById(R.id.mvbtn1);

        mvbtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity2.class);
                startActivity(intent);
            }
        });

        mvbtn2 = (Button)findViewById(R.id.mvbtn2);

        mvbtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity3.class);
                startActivity(intent);
            }
        });

        mvbtn3 = (Button)findViewById(R.id.mvbtn3);

        mvbtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity4.class);

                startActivity(intent);
            }
        });

}
        }