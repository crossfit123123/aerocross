package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainactivity3 extends AppCompatActivity {

    EditText writesex;
    EditText writeage;
    EditText writeheight;
    EditText writeweight;
    EditText writeactivitylevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity3);
        Button button = findViewById(R.id.add_btn2); //xml에서 생성한 id 매치
        writesex = findViewById(R.id.성별입력칸);
        writeage = findViewById(R.id.나이입력칸);
        writeheight = findViewById(R.id.신장입력칸);
        writeweight = findViewById(R.id.체중입력칸);
        writeactivitylevel=findViewById(R.id.활동지수입력칸);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("sex").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue(String.class);
                writesex.setText(value);
//                *문자열 받아오기*
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writesex.setText("error");
            }
        });

        myRef.child("user").child("age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value=(int)snapshot.getValue(Integer.class);
                writeage.setText(Integer.toString(value));

//                *문자열 받아오기*
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writeage.setText("error");
            }
        });

        myRef.child("user").child("height").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value=(int)snapshot.getValue(Integer.class);
                writeheight.setText(Integer.toString(value));

//                *문자열 받아오기*
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writeheight.setText("error");
            }
        });

        myRef.child("user").child("weight").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value=(int)snapshot.getValue(Integer.class);
                writeweight.setText(Integer.toString(value));

//                *문자열 받아오기*
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writeweight.setText("error");
            }
        });

        myRef.child("user").child("activitylevel").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value=(int)snapshot.getValue(Integer.class);
                writeactivitylevel.setText(Integer.toString(value));

//                *문자열 받아오기*
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writeactivitylevel.setText("error");
            }
        });


        //        SettingListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user group = snapshot.getValue(user.class);


                        String sex = writesex.getText().toString();
                        myRef.child("user").child("sex").setValue(sex);

                        int age =Integer.parseInt(writeage.getText().toString());
                        myRef.child("user").child("age").setValue(age);

                        int height =Integer.parseInt(writeheight.getText().toString());
                        myRef.child("user").child("height").setValue(height);

                        int weight =Integer.parseInt(writeweight.getText().toString());
                        myRef.child("user").child("weight").setValue(weight);

                        int activitylevel =Integer.parseInt(writeactivitylevel.getText().toString());
                        myRef.child("user").child("activitylevel").setValue(activitylevel);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(mainactivity3.this, MainActivity1.class);
                startActivity(intent);
            }
        });

    }
}
