package com.jeffe.gtp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The main activity as described in the project spec
 * 
 */
public class MainActivity extends AppCompatActivity {

    private class LaunchActivityClickHandler implements View.OnClickListener {
        private Intent mIntent;

        private LaunchActivityClickHandler(Intent i) {
            mIntent = i;
        }

        @Override
        public void onClick(View v) {
            MainActivity.this.startActivity(mIntent);
        }

    }

    private LaunchActivityClickHandler mListGamesClickHandler;
    private LaunchActivityClickHandler mRateGamesClickHandler;
    private Button mListGamesButton;
    private Button mRateGamesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        mListGamesButton = (Button) findViewById(R.id.listgames);
        mRateGamesButton = (Button) findViewById(R.id.rategames);

        mListGamesClickHandler = new LaunchActivityClickHandler(new Intent(
                this, ListGamesActivity.class));
        mRateGamesClickHandler = new LaunchActivityClickHandler(new Intent(
                this, RateGamesActivity.class));

        mListGamesButton.setOnClickListener(mListGamesClickHandler);
        mRateGamesButton.setOnClickListener(mRateGamesClickHandler);
    }
}
