package com.jeffe.gtp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Extend this class for activities that display a custom item layout for a list
 * of games... like an activity to list or rate games ;)
 */
public abstract class AbsGameListActivity extends AppCompatActivity {
    private final String TAG = AbsGameListActivity.class.getSimpleName();

    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            // add list fragment to activity if not restoring
            getSupportFragmentManager().beginTransaction()
                    .add(getGameListFragId(), getPlaceholderFragment())
                    .commit();
        }

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        case android.R.id.home:
            Log.d(TAG, "onOptionsItemSelected itemId=" + itemId);
            this.finish();
            return true; // return true to consume event/stop processing
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 
     * @return the game list frag id to add of sub class
     */
    protected abstract int getGameListFragId();

    /**
     * Define this to implement a custom list item layout
     * 
     * @return the custom fragment layout of sub class
     */
    protected abstract Fragment getPlaceholderFragment();
}
