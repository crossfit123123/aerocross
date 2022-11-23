package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Startactivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startactivity);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("user").child("user_information").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_total group = snapshot.getValue(Data_total.class);
                if (group.getSex()==null || group.getAge() == 0 || group.getHeight() == 0 || group.getWeight()==0
                        )
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Startactivity.this, ManagePrivacy.class);
                            startActivity(intent);
//
                        }
                    }, 1000);
                else
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Startactivity.this, Main.class);
                            startActivity(intent);
//
                        }
                    }, 1000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}