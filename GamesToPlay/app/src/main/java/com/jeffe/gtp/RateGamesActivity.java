package com.jeffe.gtp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * The rate games activity as described in the project spec
 * 
 */
public class RateGamesActivity extends AbsGameListActivity {
    private static final String TAG = RateGamesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rategames);
    }

    protected Fragment getPlaceholderFragment() {
        return new PlaceholderFragment();
    }

    @Override
    protected int getGameListFragId() {
        return R.id.lvfrag_rategames;
    }

    /**
     * A placeholder fragment containing the list view
     */
    public static class PlaceholderFragment extends Fragment {
        private RateGames_ListAdapter mAdapter;

        public PlaceholderFragment() {
            mAdapter = new RateGames_ListAdapter();
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
    }
}
