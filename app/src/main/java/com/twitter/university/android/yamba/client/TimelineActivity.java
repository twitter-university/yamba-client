package com.twitter.university.android.yamba.client;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


public class TimelineActivity extends YambaActivity {
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
    protected Fragment getRootFragment() {
        return TimelineFragment.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usingFragments = null != findViewById(R.id.details);
        if (usingFragments && (null == savedInstanceState)) { addDetailFragment(); }
    }

    private void addDetailFragment() {
        getFragmentManager().beginTransaction()
            .add(R.id.details, TimelineDetailFragment.newInstance(null))
            .commit();
    }

    private void showDetails(Bundle args) {
        FragmentTransaction xact = getFragmentManager().beginTransaction();
        xact.replace(
            R.id.details,
            TimelineDetailFragment.newInstance(args));
        xact.addToBackStack(null);
        xact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        xact.commit();
    }
}
