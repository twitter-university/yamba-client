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
    public static final String TAG = "APP";

    private BroadcastReceiver postCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BuildConfig.DEBUG) { Log.d(TAG, "post complete"); }
            postComplete(intent.getIntExtra(
                YambaContract.Service.PARAM_POST_SUCCEEDED,
                0));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(
            postCompleteReceiver,
            new IntentFilter(YambaContract.Service.ACTION_POST_COMPLETE));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(postCompleteReceiver);
    }

    void postComplete(int count) {
        Toast.makeText(
            this,
            (0 < count) ? R.string.success : R.string.fail,
            Toast.LENGTH_LONG)
            .show();
    }
}
