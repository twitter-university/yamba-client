package com.twitter.twitteru.android.yamba.client;

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

import com.twitter.twitteru.android.yamba.service.YambaServiceHelper;


public class StatusFragment extends Fragment {
    public static final String TAG = "STATUS";

    private int okColor;
    private int warnColor;
    private int errColor;

    private int statusLenMax;
    private int warnMax;
    private int errMax;

    private EditText statusView;
    private TextView countView;
    private Button submitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "created"); }
        super.onCreate(savedInstanceState);

        Resources rez = getResources();
        okColor = rez.getColor(R.color.green);
        statusLenMax = rez.getInteger(R.integer.status_limit);
        warnColor = rez.getColor(R.color.yellow);
        warnMax = rez.getInteger(R.integer.warn_limit);
        errColor = rez.getColor(R.color.red);
        errMax = rez.getInteger(R.integer.err_limit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "view created"); }

        View v = inflater.inflate(R.layout.fragment_status, parent, false);

        countView = (TextView) v.findViewById(R.id.status_count);

        submitButton = (Button) v.findViewById(R.id.status_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) { post(); }
        });

        statusView = (EditText) v.findViewById(R.id.status_status);
        statusView.addTextChangedListener(
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
        int n = statusView.getText().length();

        submitButton.setEnabled(checkStatusLen(n));

        n = statusLenMax - n;

        int color;
        if (n > warnMax) { color = okColor; }
        else if (n > errMax) { color = warnColor; }
        else  { color = errColor; }

        countView.setText(String.valueOf(n));
        countView.setTextColor(color);
    }

    void post() {
        String status = statusView.getText().toString();
        if (BuildConfig.DEBUG) { Log.d(TAG, "posting: " + status); }
        if (!checkStatusLen(status.length())) { return; }

        statusView.setText("");
        YambaServiceHelper.post(getActivity(), status);
    }

    private boolean checkStatusLen(int n) {
        return (errMax < n) && (statusLenMax > n);
    }
}
