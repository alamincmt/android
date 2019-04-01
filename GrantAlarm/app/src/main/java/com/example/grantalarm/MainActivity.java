package com.example.grantalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_set_alarm;
    private EditText et_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_set_alarm = findViewById(R.id.btn_set_alarm);
        et_time = findViewById(R.id.et_time);

        final Date date = new Date();
        et_time.setText("" + date.toString());

        btn_set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentDate = new Date();
                Date alarmDateTime = new Date(et_time.getText().toString().trim());


                long milliseconds = (alarmDateTime.getTime() - currentDate.getTime());
                if(milliseconds > 1000){
                    Toast.makeText(MainActivity.this, "Alarm will play after: " + milliseconds + " milliseconds", Toast.LENGTH_LONG).show();
                    if(Build.VERSION.SDK_INT <= 22){
                        // user alarm manager to set alarm
                        AlarmManager alarmMgr;
                        PendingIntent alarmIntent;
                        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                        // Set the alarm to start at 8:30 a.m.
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(alarmDateTime.getTime());
//                        calendar.set(Calendar.HOUR_OF_DAY, 8);
//                        calendar.set(Calendar.MINUTE, 30);

                        // setRepeating() lets you specify a precise custom interval--in this case,
                        // 5 minutes.
                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                1000 * 60 * 5, alarmIntent);
                    }else{
                        // set alarm with jobscheduler
                                                
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Selected time diff is: " + milliseconds + "\nAlarm will not granted with negative value. ", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
