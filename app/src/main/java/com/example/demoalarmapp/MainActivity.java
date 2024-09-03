package com.example.demoalarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    Button btnSetAlarm, btnDeleteAlarm;
    TextView txtDisplayAlarm;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSetAlarm = (Button) findViewById(R.id.btn_set_alarm);
        btnDeleteAlarm = (Button) findViewById(R.id.btn_delete_alarm);
        txtDisplayAlarm = (TextView) findViewById(R.id.text_display_alarm);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                int curHour = timePicker.getHour();
                int curMinute = timePicker.getMinute();
                String strCurHour = String.valueOf(curHour);
                if(strCurHour.length() == 1)
                    strCurHour = "0" + strCurHour;
                String strCurMinute = String.valueOf(curMinute);
                if(strCurMinute.length() == 1)
                    strCurMinute = "0" + strCurMinute;

                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                txtDisplayAlarm.setText("The alarm you set is " + strCurHour + ":" + strCurMinute);
            }
        });

        btnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
                txtDisplayAlarm.setText("Alarm deleted !");
            }
        });
    }
}