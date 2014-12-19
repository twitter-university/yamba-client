package com.twitter.university.android.yamba.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.twitter.university.android.yamba.client.BuildConfig;


public final class YambaServiceHelper {
    private static final String TAG = "HELPER";

    private final Context ctxt;

    public  YambaServiceHelper(Context ctxt) {
        this.ctxt = ctxt.getApplicationContext();
    }

    public void post(String tweet) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "posting: " + tweet); }
        Intent i = new Intent(YambaContract.Service.ACTION_EXECUTE);
        i.setPackage(YambaContract.Service.PACKAGE);
        i.putExtra(YambaContract.Service.PARAM_OP, YambaContract.Service.OP_POST);
        i.putExtra(YambaContract.Service.PARAM_TWEET, tweet);
        ctxt.startService(i);
    }
}
