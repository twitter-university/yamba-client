package com.twitter.twitteru.android.yamba.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.twitter.twitteru.android.yamba.client.BuildConfig;

public final class YambaServiceHelper {
    public static final String TAG = "SVC_HELP";

    private YambaServiceHelper() {}

    public static void post(Context ctxt, String tweet) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "posting: " + tweet); }
        Intent i = new Intent(YambaContract.Service.ACTION_EXECUTE);
        i.putExtra(YambaContract.Service.PARAM_OP, YambaContract.Service.OP_POST);
        i.putExtra(YambaContract.Service.PARAM_TWEET, tweet);
        ctxt.startService(i);
    }
}
