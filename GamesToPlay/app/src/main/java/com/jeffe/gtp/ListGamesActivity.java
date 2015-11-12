package com.jeffe.gtp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jeffe.gtp.GTP_DataMgr.ListItem_ListGames;

/**
 * The list games activity as described in the project spec.
 * 
 */
public class ListGamesActivity extends AbsGameListActivity {
    private static final String TAG = ListGamesActivity.class.getSimpleName();

    PlaceholderFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFragment = new PlaceholderFragment();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listgames);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addgame) {
            Intent addGameIntent = new Intent(this,
                    AddGameListingActivity.class);
            startActivityForResult(addGameIntent,
                    AddGameListingActivity.REQUESTCODE_ADDGAME);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != AddGameListingActivity.REQUESTCODE_ADDGAME) {
            Log.w(TAG, "onActivityResult received invalid request code");
            return;
        }

        if (resultCode != RESULT_OK) {
            Log.w(TAG, "onActivityResult received invalid result code");
            return;
        }

        String title = data
                .getStringExtra(AddGameListingActivity.EXTRA_GAMETITLE);
        String console = data
                .getStringExtra(AddGameListingActivity.EXTRA_GAMECONSOLE);
        boolean finished = data.getBooleanExtra(
                AddGameListingActivity.EXTRA_GAMEFINISHED, false);

        ListItem_ListGames newItem = new ListItem_ListGames(title, console,
                R.drawable.ic_add_white_24dp, finished);
        GTP_DataMgr.instance().addGameListing(newItem);
        mFragment.notifyDataChanged();
    }

    protected Fragment getPlaceholderFragment() {
        return mFragment;
    }

    protected int getGameListFragId() {
        return R.id.lvfrag_listgames;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ListGames_ListAdapter mAdapter;

        public PlaceholderFragment() {
            mAdapter = new ListGames_ListAdapter();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.frag_list_a, container,
                    false);
            ListView lvFrag = (ListView) rootView.findViewById(R.id.lvFragA);
            lvFrag.setAdapter(mAdapter);
            Log.d(TAG, "set list adapter object: " + mAdapter);
            return rootView;
        }

        public void notifyDataChanged() {
            mAdapter.notifyDataSetChanged();
        }
    }
}
