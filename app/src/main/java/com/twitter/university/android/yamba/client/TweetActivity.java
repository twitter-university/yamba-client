package com.twitter.university.android.yamba.client;

import android.app.Fragment;


public class TweetActivity extends YambaActivity {
    @Override
    protected Fragment getRootFragment() {
        return TweetFragment.getInstance();
    }
}
