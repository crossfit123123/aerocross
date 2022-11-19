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

public class sudong extends AppCompatActivity {

    EditText writecal;
    EditText writecalbo;
    EditText writeprotein;
    EditText writefat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudong);
        Button button = findViewById(R.id.add_btn); //xml에서 생성한 id 매치
        writecal = findViewById(R.id.칼로리입력칸);
        writecalbo = findViewById(R.id.탄수화물입력칸);
        writeprotein = findViewById(R.id.단백질입력칸);
        writefat = findViewById(R.id.지방입력칸);
//        SettingListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("user").child("nutrition").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user group = snapshot.getValue(user.class);

                        int cal = group.getEatcalorie();
                        int cal_temp = Integer.parseInt(writecal.getText().toString());
                        int sum1 = cal + cal_temp;

                        int calbo = group.getEatcalbo();
                        int calbo_temp = Integer.parseInt(writecalbo.getText().toString());
                        int sum2 = calbo + calbo_temp;

                        int protein = group.getEatprotein();
                        int protein_temp = Integer.parseInt(writeprotein.getText().toString());
                        int sum3 = protein + protein_temp;

                        int fat = group.getEatfat();
                        int fat_temp = Integer.parseInt(writefat.getText().toString());
                        int sum4 = fat + fat_temp;


                        myRef.child("user").child("nutrition").child("eatcalorie").setValue(sum1);
                        myRef.child("user").child("nutrition").child("eatcalbo").setValue(sum2);
                        myRef.child("user").child("nutrition").child("eatprotein").setValue(sum3);
                        myRef.child("user").child("nutrition").child("eatfat").setValue(sum4);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(sudong.this, mainactivity2.class);
                //입력한 input값을 intent로 전달한다.
                //액티비티 이동
                startActivity(intent);
            }
        });

    }
}
