package com.twitter.university.android.yamba.client;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public abstract class YambaActivity extends Activity {
    private final int layout;

    protected YambaActivity() { this(R.layout.activity_yamba); }

    protected YambaActivity(int layout) { this.layout = layout; }

    protected abstract Fragment getRootFragment();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yamba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_tweet) {
            nextPage(TweetActivity.class);
            return true;
        }

        if (id == R.id.menu_timeline) {
            nextPage(TimelineActivity.class);
            return true;
        }

        if ((id == R.id.menu_about) || (id == android.R.id.home)) {
            about();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout);

        ActionBar aBar = getActionBar();
        if (null != aBar) {
            aBar.setHomeButtonEnabled(true);
            //aBar.setDisplayHomeAsUpEnabled(true);
        }

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                .add(R.id.root, getRootFragment())
                .commit();
        }
    }

    private void nextPage(Class<?> page) {
        Intent i = new Intent(this, page);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    private void about() {
        Toast.makeText(this, R.string.about_yamba, Toast.LENGTH_LONG).show();
    }
}
