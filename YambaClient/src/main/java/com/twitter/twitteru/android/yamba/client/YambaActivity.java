package com.twitter.twitteru.android.yamba.client;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public abstract class YambaActivity extends Activity {
    private final int layout;
    private final String tag;

    protected YambaActivity(String tag, int layout) {
        this.tag = tag;
        this.layout = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout);

        ActionBar aBar = getActionBar();
        aBar.setHomeButtonEnabled(true);
        //aBar.setDisplayHomeAsUpEnabled(true);

        if (BuildConfig.DEBUG) { Log.d(tag, "created"); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yamba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_timeline:
                nextPage(TimelineActivity.class);
                break;
            case R.id.menu_tweet:
                nextPage(TweetActivity.class);
                break;
            case android.R.id.home:
            case R.id.menu_about:
                about();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void nextPage(Class<?> klass) {
        Intent i = new Intent(this, klass);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    private void about() {
        Toast.makeText(this, R.string.about_yamba, Toast.LENGTH_LONG).show();
    }
}
