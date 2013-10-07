package com.twitter.university.android.yamba.client;

import android.os.Bundle;


public class TimelineDetailActivity extends YambaActivity {
    private static final String TAG = "DETAILS_ACT";

    public TimelineDetailActivity() {
        super(TAG, R.layout.activity_timeline_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TimelineDetailFragment) getFragmentManager()
            .findFragmentById(R.id.fragment_timeline_detail))
            .setDetails(getIntent().getExtras());
    }
}
