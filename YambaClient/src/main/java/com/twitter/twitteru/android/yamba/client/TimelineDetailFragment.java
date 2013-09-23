package com.twitter.twitteru.android.yamba.client;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twitter.twitteru.android.yamba.service.YambaContract;


public class TimelineDetailFragment extends Fragment {
    private static final String TAG = "DETAILS";

    public static Intent marshallDetails(Context ctxt, long ts, String user, String status) {
        Intent i = new Intent(ctxt, TimelineDetailActivity.class);
        i.putExtra(YambaContract.Timeline.Columns.TIMESTAMP, ts);
        i.putExtra(YambaContract.Timeline.Columns.USER, user);
        i.putExtra(YambaContract.Timeline.Columns.STATUS, status);
        return i;
    }

    public static Fragment newInstance(Bundle args) {
        Fragment details = new TimelineDetailFragment();
        details.setArguments(args);
        return details;
    }


    private View details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "created"); }

        View v = inflater.inflate(R.layout.fragment_timeline_detail, parent, false);
        details = v.findViewById(R.id.timeline_details);

        setDetails(getArguments());

        return v;
    }

    public void setDetails(Bundle args) {
        if ((null == args) || (null == details)) { return; }

        ((TextView) details.findViewById(R.id.timeline_detail_timestamp))
            .setText(DateUtils.getRelativeTimeSpanString(
                args.getLong(YambaContract.Timeline.Columns.TIMESTAMP, 0L)));
        ((TextView) details.findViewById(R.id.timeline_detail_user)).setText(
                args.getString(YambaContract.Timeline.Columns.USER));
        ((TextView) details.findViewById(R.id.timeline_detail_status)).setText(
                args.getString(YambaContract.Timeline.Columns.STATUS));
    }
}
