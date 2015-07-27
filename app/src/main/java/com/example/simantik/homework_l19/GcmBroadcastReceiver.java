package com.example.simantik.homework_l19;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by ZOG on 2/12/2015.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {

    DBHelper mDBHelper;


    @Override
    public void onReceive(Context _context, Intent _intent) {
        Log.d("tag19", "Got message: " + _intent);
        String message = _intent.getStringExtra("message");
        String title = _intent.getStringExtra("title");
        String subtitle = _intent.getStringExtra("subtitle");
        String tickerText = _intent.getStringExtra("tickerText");
        int vibrate = Integer.valueOf(_intent.getStringExtra("vibrate"));
        int sound = Integer.valueOf(_intent.getStringExtra("sound"));

        MainActivity.addNotificationToDB(message, title, subtitle, tickerText, vibrate, sound);
        MainActivity.showNotification(_context, message, title, subtitle, tickerText, vibrate, sound);

    }
}
