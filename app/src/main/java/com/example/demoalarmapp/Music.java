package com.example.demoalarmapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import kotlin.BuilderInference;

public class Music extends Service {

    static final String TAG = Music.class.getSimpleName();
    static final String CHANNEL_ID = "AlarmNotification";

    MediaPlayer mediaPlayer;
    int id;

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager != null)
                manager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        String key = intent.getExtras().getString("extra");
        Log.d("Music nhan key", key);

        id = key.equals("on") ? 1 : 0;
        if(id == 1) {
            createNotificationChannel();

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("ALARM")
                    .setContentText("The alarm clock has gone off, wake up!")
                    .setSmallIcon(R.drawable.icon_alarm)
                    .build();

            startForeground(1, notification);

            mediaPlayer = MediaPlayer.create(this, R.raw.nhac_chuong_hkt);
            mediaPlayer.start();
            id = 0;
        } else {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");

        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        stopForeground(true);
    }
}
