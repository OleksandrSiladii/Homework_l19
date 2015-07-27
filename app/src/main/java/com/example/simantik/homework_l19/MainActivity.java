package com.example.simantik.homework_l19;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String TAG = "myTag";

    GoogleCloudMessaging gcm;
    String regid;

    private static final int NOTIFICATION_ID = 1234567890;

    private ListView mLvNotifications;
    static DBHelper mDBHelper;
    MyAdapter mMyAdapter;
    private ArrayList<NotificationModel> mALNotificationModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLvNotifications = (ListView) findViewById(R.id.lv_notifications_MA);
        mLvNotifications.setOnItemClickListener(this);
        mALNotificationModels = new ArrayList<NotificationModel>();

        if (!GcmPushHelper.checkPlayServices(this)) return;

        gcm = GoogleCloudMessaging.getInstance(this);
        regid = GcmPushHelper.getRegistrationId(this);
        if (regid.isEmpty()) {
            Log.d(TAG, "regid is empty");
            GcmPushHelper.registerGcmAsync(this, gcm);
        }
        mDBHelper = new DBHelper(this);

        addNotificationsInListFromDB();
    }

    private void addNotificationsInListFromDB() {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            try {
                do {
                    String message = c.getString(c.getColumnIndex(DBHelper.MESSAGE));
                    String title = c.getString(c.getColumnIndex(DBHelper.TITLE));
                    String subtitle = c.getString(c.getColumnIndex(DBHelper.SUBTITLE));
                    String tickerText = c.getString(c.getColumnIndex(DBHelper.TICKER_TEXT));
                    int vibrate = c.getInt(c.getColumnIndex(DBHelper.VIBRATE));
                    int sound = c.getInt(c.getColumnIndex(DBHelper.SOUND));

                    mALNotificationModels.add(new NotificationModel(message, title, subtitle, tickerText, vibrate, sound));

                } while (c.moveToNext());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            mMyAdapter = new MyAdapter(this, mALNotificationModels);
            mLvNotifications.setAdapter(mMyAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNotificationToDB(final String _message, final String _title, final String _subtitle, final String _tickerText, final int _vibrate, final int _sound) {


        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.MESSAGE, _message);
        cv.put(DBHelper.TITLE, _title);
        cv.put(DBHelper.SUBTITLE, _subtitle);
        cv.put(DBHelper.TICKER_TEXT, _tickerText);
        cv.put(DBHelper.VIBRATE, _vibrate);
        cv.put(DBHelper.SOUND, _sound);

        db.insert(DBHelper.TABLE_NAME, null, cv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GcmPushHelper.checkPlayServices(this);
    }

    public static void showNotification(Context _context, String _message, String _title, String _subtitle, String _tickerText, int _vibrate, int _sound) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(_title)
                .setSubText(_subtitle)
                .setContentText(_message)
                .setTicker(_tickerText)
                .setAutoCancel(true);

        if (_vibrate == 1)
            builder.setVibrate(new long[]{100, 500, 200, 500, 200, 100, 100, 100, 100, 100, 100, 500, 200, 500});
        if (_sound == 1)
            builder.setSound(Uri.parse("android.resource://" + _context.getPackageName() + "/" + R.raw.mess));

        NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NotificationModel model = mALNotificationModels.get(position);
        showNotification(this, model.message, model.title, model.subtitle, model.tickerText, model.vibrate, model.sound);
    }
}