package com.twitter.university.android.yamba.client;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaServiceHelper;


public class TweetFragment extends Fragment {
    private static final String TAG = "TWEET";

    public static TweetFragment getInstance() {
        return new TweetFragment();
    }

    private YambaServiceHelper helper;

    private int tweetMaxLen;
    private int tweetWarnLen;
    private int tweetMinLen;

    private int colorOk;
    private int colorWarn;
    private int colorError;

    private EditText tweetView;
    private TextView countView;
    private Button submitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "created"); }
        super.onCreate(savedInstanceState);

        helper = new YambaServiceHelper(getActivity());

        Resources rez = getResources();

        colorOk = rez.getColor(R.color.count_ok);
        tweetMaxLen = rez.getInteger(R.integer.tweet_limit);

        colorWarn = rez.getColor(R.color.count_warn);
        tweetWarnLen = rez.getInteger(R.integer.warn_limit);

        colorError = rez.getColor(R.color.count_err);
        tweetMinLen = rez.getInteger(R.integer.err_limit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "view created"); }

        View v = inflater.inflate(R.layout.fragment_tweet, parent, false);

        countView = (TextView) v.findViewById(R.id.tweet_count);

        submitButton = (Button) v.findViewById(R.id.tweet_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) { post(); }
        });

        tweetView = (EditText) v.findViewById(R.id.tweet_tweet);
        tweetView.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) { updateCount(); }

                @Override
                public void beforeTextChanged(CharSequence s, int b, int n, int a) { }

                @Override
                public void onTextChanged(CharSequence s, int b, int p, int n) { }
            });

        return v;
    }

    void updateCount() {
        int n = tweetView.getText().length();
        submitButton.setEnabled(canPost(n));

        n = tweetMaxLen - n;

        int color;
        if (n < tweetMinLen) { color = colorError; }
        else if (n < tweetWarnLen) { color = colorWarn; }
        else { color = colorOk; }
        countView.setTextColor(color);

        countView.setText(String.valueOf(n));
    }

    void post() {
        String tweet = tweetView.getText().toString();
        if (BuildConfig.DEBUG) { Log.d(TAG, "posting: " + tweet); }

        if (!canPost(tweet.length())) { return; }

        submitButton.setEnabled(false);
        tweetView.setText(null);

        helper.post(tweet);
    }

    private boolean canPost(int n) {
        return (tweetMinLen < n) && (tweetMaxLen >= n);
    }
}
