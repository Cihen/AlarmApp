package com.example.demoalarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        Intent myIntent = new Intent(context, Music.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(myIntent);
        else
            context.startService(myIntent);
    }
}
