package com.twitter.twitteru.android.yamba.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.twitter.twitteru.android.yamba.client.BuildConfig;
import com.twitter.twitteru.android.yamba.client.R;
import com.twitter.twitteru.android.yamba.client.TimelineActivity;


public class TimelineUpdateReceiver extends BroadcastReceiver {
    private static final String TAG = "TLUPDATE";
    private static final int NOTIFICATION_ID = 7;
    private static final int NOTIFICATION_INTENT_ID = 13;

    @Override
    public void onReceive(Context ctxt, Intent intent) {
        Resources rez = ctxt.getResources();
        String notifyTitle = rez.getString(R.string.notify_title);
        String notifyMessage = rez.getString(R.string.notify_message);

        int count = intent.getIntExtra(YambaContract.Service.PARAM_COUNT, -1);
        if (BuildConfig.DEBUG) { Log.d(TAG, "timeline update: " + count); }

        if (0 >= count) { return; }

        PendingIntent pi = PendingIntent.getActivity(
            ctxt,
            NOTIFICATION_INTENT_ID,
            new Intent(ctxt, TimelineActivity.class),
            0);

        ((NotificationManager) ctxt.getSystemService(Context.NOTIFICATION_SERVICE))
            .notify(
                NOTIFICATION_ID,
                new Notification.Builder(ctxt)
                    .setContentTitle(notifyTitle)
                    .setContentText(count + " " + notifyMessage)
                    .setAutoCancel(true)
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pi)
                    .build());  // works as of version 16
    }
}
