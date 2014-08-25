package com.twitter.university.android.yamba.client;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


public class TimelineActivity extends YambaActivity {
    public static final String FRAGMENT_DETAILS = "TimelineActivity.DETAILS";


    private boolean usingFragments;

    public TimelineActivity() {
        super(R.layout.activity_timeline);
    }

    @Override
    public void startActivityFromFragment(Fragment frag, Intent i, int code) {
        if (usingFragments) { showDetails(i.getExtras()); }
        else { super.startActivityFromFragment(frag, i, code); }
    }

    @Override
    protected Fragment getRootFragment() { return new TimelineFragment(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usingFragments = null != findViewById(R.id.fragment_details);
        if (usingFragments) { addDetailFragment(); }
    }

    private void addDetailFragment() {
        FragmentManager mgr = getFragmentManager();

        if (null != mgr.findFragmentByTag(FRAGMENT_DETAILS)) { return; }

        FragmentTransaction xact = mgr.beginTransaction();
        xact.add(
            R.id.fragment_details,
            TimelineDetailFragment.newInstance(null),
            FRAGMENT_DETAILS);
        xact.commit();
    }

    private void showDetails(Bundle args) {
        FragmentTransaction xact = getFragmentManager().beginTransaction();
        xact.replace(
            R.id.fragment_details,
            TimelineDetailFragment.newInstance(args),
            FRAGMENT_DETAILS);
        xact.addToBackStack(null);
        xact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        xact.commit();
    }
}
