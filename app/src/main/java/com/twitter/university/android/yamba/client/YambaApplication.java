package com.twitter.university.android.yamba.client;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.twitter.university.android.yamba.service.YambaContract;


public class YambaApplication extends Application {
    private static final String TAG = "APP";

    private final BroadcastReceiver postCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BuildConfig.DEBUG) { Log.d(TAG, "post complete"); }
            postComplete(
                intent.getBooleanExtra(YambaContract.Service.PARAM_POST_SUCCEEDED, false));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(
            postCompleteReceiver,
            new IntentFilter(YambaContract.Service.ACTION_POST_COMPLETE),
            YambaContract.Service.PERMISSION_RECEIVE_POST_COMPLETE,
            null);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(postCompleteReceiver);
    }

    void postComplete(boolean succeeded) {
        Toast.makeText(
            this,
            succeeded ? R.string.success : R.string.fail,
            Toast.LENGTH_LONG)
            .show();
    }
}
