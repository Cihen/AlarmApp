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

        String str = intent.getExtras().getString("extra");
        Log.d("Receiver nhan key ", str);
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("extra", str);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(myIntent);
        else
            context.startService(myIntent);
    }
}
