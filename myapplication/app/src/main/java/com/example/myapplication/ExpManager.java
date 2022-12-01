package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class ExpManager extends AppCompatActivity  {
    TextView totalcost;
    TextView limitcost;
    TextView warningcost;
    TextView circletext;
    Switch switchView;


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Boolean truefalse;

    private Button create1;
    private Button create2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expmanager);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();


        totalcost=findViewById(R.id.aaa2);
        limitcost=findViewById(R.id.aa2);
        warningcost=findViewById(R.id.aaaa2);

        create1 = findViewById(R.id.create1);
        create2 = findViewById(R.id.create2);


        circletext = findViewById(R.id.textView5);

        ProgressBar prb=findViewById(R.id.progressbar);

        truefalse = pref.getBoolean("truefalse", false);
        switchView = findViewById(R.id.switch1);

        switchView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    editor.putBoolean("truefalse", true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "알람 on", Toast.LENGTH_SHORT).show();

                }
                else {
                    editor.putBoolean("truefalse", false);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "알람 off", Toast.LENGTH_SHORT).show();


                }
            }
                                              });

        create1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

            @Override
            public void onClick(View view) {

                createNotification1();
            }
        });

        create2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

            @Override
            public void onClick(View view) {

                createNotification2();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("user").child("expenditure").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Data_total group = snapshot.getValue(Data_total.class);

                totalcost.setText(Integer.toString(group.getTotalcost()));
                limitcost.setText(Integer.toString(group.getLimitcost()));
                warningcost.setText(Integer.toString(group.getWarningcost()));

                switchView.setChecked(truefalse);
                if(group.getTotalcost()>=group.getLimitcost())
                  {
                      if(switchView.isChecked()) {
                         create1.performClick();

                      }
                  }

                else if(group.getTotalcost()>=group.getWarningcost() && group.getTotalcost()<group.getLimitcost()) {
                    if (switchView.isChecked()) {
                        create2.performClick();
                    }
                }
                    int percent = (int) (100 * group.getTotalcost() / group.getLimitcost());
                    prb.setProgress(percent);
                    String text = Integer.toString(percent) + "%";
                    circletext.setText(text);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                totalcost.setText("error");
            }
        });
    }



    private void createNotification1() {

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, "default");

        builder1.setSmallIcon(R.mipmap.ic_launcher);
        builder1.setContentTitle("건 너 편");
        builder1.setContentText("지출량 경고 : 100%");

        builder1.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder1.setAutoCancel(true);



        // 알림 표시
        NotificationManager notificationManager1 = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager1.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager1.notify(1, builder1.build());

    }

    private void createNotification2() {

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "default");

        builder2.setSmallIcon(R.mipmap.ic_launcher);
        builder2.setContentTitle("건 너 편");
        builder2.setContentText("지출량 경고 : 80%");

        builder2.setColor(Color.YELLOW);
        // 사용자가 탭을 클릭하면 자동 제거
        builder2.setAutoCancel(true);



        // 알림 표시
        NotificationManager notificationManager2 = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager2.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager2.notify(2, builder2.build());

    }
}