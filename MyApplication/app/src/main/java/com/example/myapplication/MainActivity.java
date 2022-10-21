package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button movebutton;
    private Button mvbtn1;
    private Button mvbtn2;
    private Button mvbtn3;



    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movebutton = (Button)findViewById(R.id.mvbtn1);

        movebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity2.class);
                startActivity(intent);
            }
        });

        movebutton = (Button)findViewById(R.id.mvbtn2);

        movebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity3.class);
                startActivity(intent);
            }
        });

        movebutton = (Button)findViewById(R.id.mvbtn3);

        movebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), mainactivity3.class);
                startActivity(intent);
            }
        });

}
        }