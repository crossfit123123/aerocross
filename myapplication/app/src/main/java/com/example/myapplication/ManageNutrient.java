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

public class ManageNutrient extends AppCompatActivity {

    private Button movebutton6,resetbutton;

    TextView eatcal;
    TextView eatcalbo;
    TextView eatprotein;
    TextView eatfat;

    TextView reccal;
    TextView reccalbo;
    TextView recprotein;
    TextView recfat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managenutrient);

        eatcal=findViewById(R.id.섭취칼로리표시칸);
        eatcalbo=findViewById(R.id.섭취탄수화물표시칸);
        eatprotein=findViewById(R.id.섭취단백질표시칸);
        eatfat=findViewById(R.id.섭취지방표시칸);

        reccal=findViewById(R.id.권장칼로리표시칸);
        reccalbo=findViewById(R.id.권장탄수화물표시칸);
        recprotein=findViewById(R.id.권장단백질표시칸);
        recfat=findViewById(R.id.권장지방표시칸);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("nutrition").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_total group = snapshot.getValue(Data_total.class);
                eatcal.setText(Integer.toString(group.getEatcalorie()));
                eatcalbo.setText(Integer.toString(group.getEatcalbo()));
                eatprotein.setText(Integer.toString(group.getEatprotein()));
                eatfat.setText(Integer.toString(group.getEatfat()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("user").child("user_information").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_total group = snapshot.getValue(Data_total.class);
                int rccal;
                if (group.getSex().equals("남"))
                    rccal = (int) ((88.362 + (13.397 * group.getWeight()) + (4.799 * group.getHeight()) - (5.677 * group.getAge()))*group.getActivitylevel());
                else if(group.getSex().equals("여"))
                    rccal = (int) ((447.593 + (9.247 * group.getWeight()) + (3.098 * group.getHeight()) - (4.33 * group.getAge()))*group.getActivitylevel());
                else
                    rccal=0;
                reccal.setText(Integer.toString(rccal));

                int rccalbo = (int) (rccal * 0.5 / 4);
                reccalbo.setText(Integer.toString(rccalbo));
                int rcprotein = (int) (rccal * 0.3 / 4);
                recprotein.setText(Integer.toString(rcprotein));
                int rcfat = (int) (rccal * 0.2 / 8);
                recfat.setText(Integer.toString(rcfat));



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        movebutton6 = (Button)findViewById(R.id.mvbtn6);

        movebutton6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), DirectInputNutri.class);
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