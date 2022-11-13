package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                myRef.child("user").child("eatcalorie").setValue(Integer.parseInt(writecal.getText().toString()));
                myRef.child("user").child("eatcalbo").setValue(Integer.parseInt(writecalbo.getText().toString()));
                myRef.child("user").child("eatprotein").setValue(Integer.parseInt(writeprotein.getText().toString()));
                myRef.child("user").child("eatfat").setValue(Integer.parseInt(writefat.getText().toString()));

                Intent intent = new Intent(mainactivity3.this, mainactivity2.class);
                //입력한 input값을 intent로 전달한다.
                //액티비티 이동
                startActivity(intent);
            }
        });

    }


}
