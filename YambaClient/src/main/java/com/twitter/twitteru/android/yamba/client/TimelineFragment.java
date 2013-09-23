package com.twitter.twitteru.android.yamba.client;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.twitteru.android.yamba.service.YambaContract;


public class TimelineFragment extends ListFragment implements LoaderCallbacks<Cursor> {
    private static final String TAG = "TIMELINE";

    private static final int TIMELINE_LOADER = 666;

    private static final String[] PROJ = new String[] {
        YambaContract.Timeline.Columns.ID,
        YambaContract.Timeline.Columns.USER,
        YambaContract.Timeline.Columns.TIMESTAMP,
        YambaContract.Timeline.Columns.STATUS
    };

    private static final String[] FROM = new String[PROJ.length - 1];
    static { System.arraycopy(PROJ, 1, FROM, 0, FROM.length); }

    private static final int[] TO = new int[] {
        R.id.timeline_row_user,
        R.id.timeline_row_time,
        R.id.timeline_row_status
    };

    static class TimelineBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View v, Cursor c, int col) {
            if (R.id.timeline_row_time != v.getId()) { return false; }

            CharSequence s = "long ago";
            long t = c.getLong(col);
            if (0 < t) { s = DateUtils.getRelativeTimeSpanString(t); }
            ((TextView) v).setText(s);
            return true;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "loader requested"); }
       return new CursorLoader(
            getActivity(),
            YambaContract.Timeline.URI,
            PROJ,
            null,
            null,
            YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> l, Cursor c) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "load finished"); }
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(c);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> l) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "load reset"); }
       ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int p, long id) {
        Cursor c = (Cursor) l.getItemAtPosition(p);

        Intent i = TimelineDetailFragment.marshallDetails(
            getActivity(),
            c.getLong(c.getColumnIndex(YambaContract.Timeline.Columns.TIMESTAMP)),
            c.getString(c.getColumnIndex(YambaContract.Timeline.Columns.USER)),
            c.getString(c.getColumnIndex(YambaContract.Timeline.Columns.STATUS)));

        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "created"); }

        View v = super.onCreateView(inflater, container, savedInstanceState);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            getActivity(),
            R.layout.timeline_row,
            null,
            FROM,
            TO,
            0);

        adapter.setViewBinder(new TimelineBinder());
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

        return v;
    }
}
