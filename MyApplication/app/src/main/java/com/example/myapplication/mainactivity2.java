package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainactivity2 extends AppCompatActivity {

    private Button movebutton;
    private Button mvbtn5;
    private Button mvbtn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity2);

        movebutton = (Button)findViewById(R.id.mvbtn5);

        movebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity5.class);
                startActivity(intent);
            }
        });

        movebutton = (Button)findViewById(R.id.mvbtn6);

        movebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity6.class);
                startActivity(intent);
            }
        });
    }
}