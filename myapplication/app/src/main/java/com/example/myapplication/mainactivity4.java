package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Switch;

public class mainactivity4 extends AppCompatActivity  {
    TextView totalcost;
    TextView limitcost;
    TextView circletext;
    Switch switchView;


    private Button create;
    // 알림제거버튼
    private Button remove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity4);


        totalcost=findViewById(R.id.aaa2);
        limitcost=findViewById(R.id.aa2);

        create = findViewById(R.id.create);
        remove = findViewById(R.id.remove);

        circletext = findViewById(R.id.textView5);

        ProgressBar prb=findViewById(R.id.progressbar);
//        prb.setProgress(80);

        switchView = findViewById(R.id.switch1);
        switchView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    create = null;
                }
                else {
//                    remove = ;
                }
            }
                                              });

        create.performClick();
        create.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                createNotification();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeNotification();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("expenditure").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
////              String value=snapshot.getValue(String.class);
//                eatcal.setText(snapshot.getValue(String.class));
//                *문자열 받아오기*
//                int value = (int)snapshot.getValue(Integer.class); // 저장된 값을 숫자로 받아오기
////                    myRef.child("user").child("totalcost").setValue(value); // 저장
//                totalcost.setText(Integer.toString(value));
                user group = snapshot.getValue(user.class);

                totalcost.setText(Integer.toString(group.getTotalcost()));
                limitcost.setText(Integer.toString(group.getLimitcost()));

//                int total= group.getTotalcost();
//                int limit= group.getLimitcost();
                if(group.getTotalcost()>=group.getLimitcost())
                  {createNotification();}

                int percent= (int)(100*group.getTotalcost()/group.getLimitcost());
//                int percent= group.getTotalcost()/group.getLimitcost()*100;
                prb.setProgress(percent);
                String text =Integer.toString(percent)+"%";
                circletext.setText(text);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                totalcost.setText("error");
            }
        });

//        myRef.child("user").child("limitcost").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//////              String value=snapshot.getValue(String.class);
////                eatcal.setText(snapshot.getValue(String.class));
////                *문자열 받아오기*
//                int value = (int)snapshot.getValue(Integer.class); // 저장된 값을 숫자로 받아오기
////                    myRef.child("user").child("totalcost").setValue(value); // 저장
//                limitcost.setText(Integer.toString(value));
//
//
//
//                String cost2 = limitcost.getText().toString();
//                int temp2 = Integer.parseInt(cost2);
//                System.out.println(temp2);
//                if(temp2>5000)
//                {
//                    create.performClick();
//                    ////***///
//                }
//
//
//
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                totalcost.setText("error");
//            }
//        });


    }



    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림 제목");
        builder.setContentText("알람 세부 텍스트");

        builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }

    private void removeNotification() {

        // Notification 제거
        NotificationManagerCompat.from(this).cancel(1);
    }


}