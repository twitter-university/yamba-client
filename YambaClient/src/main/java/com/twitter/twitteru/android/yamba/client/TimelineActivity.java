package com.twitter.twitteru.android.yamba.client;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


public class TimelineActivity extends YambaActivity {
    public static final String TAG = "TIMELINE_ACT";

    private static final String DETAILS_TAG = "TimelineActivity.DETAILS";


    private boolean usingFragments;

    public TimelineActivity() {
        super(TAG, R.layout.activity_timeline);
    }

    @Override
    public void startActivityFromFragment(Fragment frag, Intent i, int code) {
        if (usingFragments) { showDetails(i.getExtras()); }
        else { super.startActivityFromFragment(frag, i, code); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usingFragments = null != findViewById(R.id.fragment_timeline_details);

        if (usingFragments) { addDetailFragment(); }
    }

    private void addDetailFragment() {
        FragmentManager mgr = getFragmentManager();

        if (null != mgr.findFragmentByTag(DETAILS_TAG)) { return; }

        FragmentTransaction xact = mgr.beginTransaction();
        xact.add(
                R.id.fragment_timeline_details,
                TimelineDetailFragment.newInstance(null),
                DETAILS_TAG);
        xact.commit();
    }

    private void showDetails(Bundle args) {
        FragmentTransaction xact = getFragmentManager().beginTransaction();
        xact.replace(
                R.id.fragment_timeline_details,
                TimelineDetailFragment.newInstance(args),
                DETAILS_TAG);
        xact.addToBackStack(null);
        xact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        xact.commit();
    }
}
