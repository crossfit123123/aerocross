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
    EditText writelimitcost;
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
        writelimitcost=findViewById(R.id.지출량한도입력칸);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("user_information").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user group = snapshot.getValue(user.class);
                writesex.setText(group.getSex());
                writeage.setText(Integer.toString(group.getAge()));
                writeheight.setText(Integer.toString(group.getHeight()));
                writeweight.setText(Integer.toString(group.getWeight()));
                writeactivitylevel.setText(Integer.toString(group.getActivitylevel()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writesex.setText("error");
                writeage.setText("error");
                writeheight.setText("error");
                writeweight.setText("error");
                writeactivitylevel.setText("error");
            }
        });

        myRef.child("user").child("expenditure").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user group = snapshot.getValue(user.class);
                writelimitcost.setText(Integer.toString(group.getLimitcost()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            //        SettingListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("user").child("user_information").child("sex").setValue(writesex.getText().toString());
                myRef.child("user").child("user_information").child("age").setValue(Integer.parseInt(writeage.getText().toString()));
                myRef.child("user").child("user_information").child("height").setValue(Integer.parseInt(writeheight.getText().toString()));
                myRef.child("user").child("user_information").child("weight").setValue(Integer.parseInt(writeweight.getText().toString()));
                myRef.child("user").child("user_information").child("activitylevel").setValue(Integer.parseInt(writeactivitylevel.getText().toString()));

                myRef.child("user").child("expenditure").child("limitcost").setValue(Integer.parseInt(writelimitcost.getText().toString()));
                Intent intent = new Intent(mainactivity3.this, MainActivity1.class);
                startActivity(intent);
            }
        });

    }
}
