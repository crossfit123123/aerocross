package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagePrivacy extends AppCompatActivity {

    EditText writeage;
    EditText writeheight;
    EditText writeweight;
    EditText writelimitcost;

    CheckBox checkBoxmale;
    CheckBox checkBoxfemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageprivacy);
        Button button = findViewById(R.id.add_btn2); //xml에서 생성한 id 매치
        writeage = findViewById(R.id.나이입력칸);
        writeheight = findViewById(R.id.신장입력칸);
        writeweight = findViewById(R.id.체중입력칸);
        writelimitcost = findViewById(R.id.지출량한도입력칸);

        checkBoxmale = (CheckBox) findViewById(R.id.chbmale);
        checkBoxfemale = (CheckBox) findViewById(R.id.chbfemale);

        final String[] act={"거의 없다(거의 좌식생활, 운동안함)","조금 있다(활동량이 보통이거나 주 1~3회 운동)"
                ,"보통(활동량이 다소 많거나 주 3~5회 운동)","꽤 있다(활동량이 많거나 주 6~7회 운동)"
        ,"아주 많다(활동량이 매우 많거나 거의 매일 하루 2번 운동)"};

        Spinner spinner = (Spinner)findViewById((R.id.activespinner));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,act);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("user_information").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_total group = snapshot.getValue(Data_total.class);

                if (group.getSex() == null) {
                    checkBoxmale.setChecked(false);
                    checkBoxfemale.setChecked(false);
                } else if (group.getSex().equals("남")) {
                    checkBoxmale.setChecked(true);
                    checkBoxfemale.setChecked(false);
                } else if (group.getSex().equals("여")) {
                    checkBoxfemale.setChecked(true);
                    checkBoxmale.setChecked(false);
                } else {
                    checkBoxmale.setChecked(false);
                    checkBoxfemale.setChecked(false);
                }


                if (group.getAge() == 0)
                    writeage.setText("");
                else
                    writeage.setText(Integer.toString(group.getAge()));
                if (group.getHeight() == 0)
                    writeheight.setText("");
                else
                    writeheight.setText(Integer.toString(group.getHeight()));
                if (group.getWeight() == 0)
                    writeweight.setText("");
                else
                    writeweight.setText(Integer.toString(group.getWeight()));

                switch ((int)(1000*group.getActivitylevel())) {
                    case 1200:
                        spinner.setSelection(0);
                        break;
                    case 1375:
                        spinner.setSelection(1);
                        break;
                    case 1550:
                        spinner.setSelection(2);
                        break;
                    case 1725:
                        spinner.setSelection(3);
                        break;
                    case 1900:
                        spinner.setSelection(4);
                        break;
                    default:
                        spinner.setSelection(2);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                writeage.setText("error");
                writeheight.setText("error");
                writeweight.setText("error");
            }
        });

        myRef.child("user").child("expenditure").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data_total group = snapshot.getValue(Data_total.class);
                if (group.getLimitcost() == 0)
                    writelimitcost.setText("");
                else
                    writelimitcost.setText(Integer.toString(group.getLimitcost()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                writelimitcost.setText("error");
            }
        });


        //        SettingListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                if (checkBoxmale.isChecked())
                    myRef.child("user").child("user_information").child("sex").setValue("남");
                else if (checkBoxfemale.isChecked())
                    myRef.child("user").child("user_information").child("sex").setValue("여");

                myRef.child("user").child("user_information").child("age").setValue(Integer.parseInt(writeage.getText().toString()));
                myRef.child("user").child("user_information").child("height").setValue(Integer.parseInt(writeheight.getText().toString()));
                myRef.child("user").child("user_information").child("weight").setValue(Integer.parseInt(writeweight.getText().toString()));
                myRef.child("user").child("expenditure").child("limitcost").setValue(Integer.parseInt(writelimitcost.getText().toString()));
                myRef.child("user").child("expenditure").child("warningcost").setValue(Integer.parseInt(writelimitcost.getText().toString())*0.8);

                int val;
                switch(spinner.getSelectedItem().toString()) {
                    case "거의 없다(거의 좌식생활, 운동안함)":
                        myRef.child("user").child("user_information").child("activitylevel").setValue(1.2);
                        break;
                    case "조금 있다(활동량이 보통이거나 주 1~3회 운동)":
                        myRef.child("user").child("user_information").child("activitylevel").setValue(1.375);
                        break;
                    case "보통(활동량이 다소 많거나 주 3~5회 운동)":
                        myRef.child("user").child("user_information").child("activitylevel").setValue(1.55);
                        break;
                    case "꽤 있다(활동량이 많거나 주 6~7회 운동)":
                        myRef.child("user").child("user_information").child("activitylevel").setValue(1.725);
                        break;
                    case "아주 많다(활동량이 매우 많거나 거의 매일 하루 2번 운동)":
                        myRef.child("user").child("user_information").child("activitylevel").setValue(1.9);
                        break;

                }
//
                Intent intent = new Intent(ManagePrivacy.this, Main.class);
                startActivity(intent);
            }
        });

    }
}

