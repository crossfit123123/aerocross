package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainactivity2 extends AppCompatActivity {

    private Button movebutton6,resetbutton;

    TextView eatcal;
    TextView eatcalbo;
    TextView eatprotein;
    TextView eatfat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity2);

        eatcal=findViewById(R.id.섭취칼로리표시칸);
        eatcalbo=findViewById(R.id.섭취탄수화물표시칸);
        eatprotein=findViewById(R.id.섭취단백질표시칸);
        eatfat=findViewById(R.id.섭취지방표시칸);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("nutrition").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user group = snapshot.getValue(user.class);
                eatcal.setText(Integer.toString(group.getEatcalorie()));
                eatcalbo.setText(Integer.toString(group.getEatcalbo()));
                eatprotein.setText(Integer.toString(group.getEatprotein()));
                eatfat.setText(Integer.toString(group.getEatfat()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        movebutton6 = (Button)findViewById(R.id.mvbtn6);

        movebutton6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), sudong.class);
                startActivity(intent);
            }
        });

        resetbutton= (Button)findViewById(R.id.reset);
        resetbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("user").child("nutrition").child("eatcalorie").setValue(0);
                myRef.child("user").child("nutrition").child("eatcalbo").setValue(0);
                myRef.child("user").child("nutrition").child("eatprotein").setValue(0);
                myRef.child("user").child("nutrition").child("eatfat").setValue(0);


            }
        });
    }





}