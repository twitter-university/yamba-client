package com.twitter.university.android.yamba.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.twitter.university.android.yamba.client.BuildConfig;


public final class YambaServiceHelper {
    private static final String TAG = "HELPER";

    private final Context ctxt;

    public  YambaServiceHelper(Context ctxt) {
        this.ctxt = ctxt.getApplicationContext();
    }

    private static class Poster extends AsyncTask<String, Void, Void> {
        private final ContentResolver resolver;

        public Poster(ContentResolver resolver) { this.resolver = resolver; }

        @Override
        protected Void doInBackground(String... tweet) {
            ContentValues cv = new ContentValues();
            cv.put(YambaContract.Posts.Columns.TWEET, tweet[0]);
            resolver.insert(YambaContract.Posts.URI, cv);
            if (BuildConfig.DEBUG) { Log.d(TAG, "posted: " + tweet[0]); }
            return null;
        }
    }


    public void post(String tweet) {
        new Poster(ctxt.getContentResolver()).execute(tweet);
    }
}
