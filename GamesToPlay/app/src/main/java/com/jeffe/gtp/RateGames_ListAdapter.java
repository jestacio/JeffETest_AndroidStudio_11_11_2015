package com.jeffe.gtp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jeffe.gtp.GTP_DataMgr.ListItem_RateGames;

/**
 * 
 * Implementation of the list adapter for the rate games activity.
 * 
 * The star rating is implemented using a spinner. Star bitmaps are also
 * displayed based on the rating
 * 
 */
public class RateGames_ListAdapter extends BaseAdapter {
    private static final String TAG = RateGames_ListAdapter.class
            .getSimpleName();
    private static final boolean DEBUG_VERBOSE = false;

    static class ViewHolder {
        ImageView imgGameImg;
        ImageView imgStar1;
        ImageView imgStar2;
        ImageView imgStar3;
        ImageView imgStar4;
        ImageView imgStar5;
        Spinner spinnerRating;
        TextView txtGameTitle;
        TextView txtGameConsole;
        SpinnerItemSelectedListener spinnerItemSelectedListener;
        int listPosition;

        int getSpinnerRating() {
            int rating = spinnerRating.getSelectedItemPosition() - 1;
            return rating;
        }

        void setViewsForSpinnerRating() {
            int rating = getSpinnerRating();

            imgStar1.setAlpha(0.0f);
            imgStar2.setAlpha(0.0f);
            imgStar3.setAlpha(0.0f);
            imgStar4.setAlpha(0.0f);
            imgStar5.setAlpha(0.0f);
            switch (rating) {
            case 1:
                imgStar1.setAlpha(1.0f);
                break;
            case 2:
                imgStar1.setAlpha(1.0f);
                imgStar2.setAlpha(1.0f);
                break;
            case 3:
                imgStar1.setAlpha(1.0f);
                imgStar2.setAlpha(1.0f);
                imgStar3.setAlpha(1.0f);
                break;
            case 4:
                imgStar1.setAlpha(1.0f);
                imgStar2.setAlpha(1.0f);
                imgStar3.setAlpha(1.0f);
                imgStar4.setAlpha(1.0f);
                break;
            case 5:
                imgStar1.setAlpha(1.0f);
                imgStar2.setAlpha(1.0f);
                imgStar3.setAlpha(1.0f);
                imgStar4.setAlpha(1.0f);
                imgStar5.setAlpha(1.0f);
                break;
            case 0:
            default:
                break;
            }
        }
    }

    private class SpinnerItemSelectedListener implements
            AdapterView.OnItemSelectedListener {

        private ViewHolder mHolder;

        private SpinnerItemSelectedListener() {

        }

        public SpinnerItemSelectedListener(ViewHolder holder) {
            this();
            mHolder = holder;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                int position, long id) {
            // TODO: animate the change in rating
            int rating = mHolder.getSpinnerRating();
            mHolder.setViewsForSpinnerRating();

            ListItem_RateGames[] items = GTP_DataMgr.instance()
                    .getListItems_RateGames();
            ListItem_RateGames item = items[mHolder.listPosition];
            if (rating != items[mHolder.listPosition].starRating) {
                GTP_DataMgr.instance().updateRating(item.title, item.console,
                        item.imgResId, rating);
                notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }

    public RateGames_ListAdapter() {

    }

    @Override
    public int getCount() {
        ListItem_RateGames[] items = GTP_DataMgr.instance()
                .getListItems_RateGames();
        int retval = items == null ? 0 : items.length;
        if (DEBUG_VERBOSE) {
            Log.d(TAG, "list items count=" + retval);
        }
        return retval;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View itemView = convertView;

        // use view holder pattern for list view
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.layout_rategamesrow, parent,
                    false);
            holder = new ViewHolder();
            holder.imgGameImg = (ImageView) itemView
                    .findViewById(R.id.imgGameImg);
            holder.imgStar1 = (ImageView) itemView.findViewById(R.id.imgStar1);
            holder.imgStar2 = (ImageView) itemView.findViewById(R.id.imgStar2);
            holder.imgStar3 = (ImageView) itemView.findViewById(R.id.imgStar3);
            holder.imgStar4 = (ImageView) itemView.findViewById(R.id.imgStar4);
            holder.imgStar5 = (ImageView) itemView.findViewById(R.id.imgStar5);
            holder.spinnerRating = (Spinner) itemView
                    .findViewById(R.id.spinnerGameRating);
            holder.txtGameTitle = (TextView) itemView
                    .findViewById(R.id.txtGameTitle);
            holder.txtGameConsole = (TextView) itemView
                    .findViewById(R.id.txtGameConsole);

            // Create an ArrayAdapter using the string array and a default
            // spinner
            // layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(parent.getContext(),
                            R.array.strArrRatings,
                            android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            // use custom spinner item layout for right aligned text
            adapter.setDropDownViewResource(R.layout.layout_spnratingitem);
            holder.spinnerRating.setAdapter(adapter);
            holder.setViewsForSpinnerRating();
            holder.spinnerRating
                    .setOnItemSelectedListener(new SpinnerItemSelectedListener(
                            holder));

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        ListItem_RateGames[] items = GTP_DataMgr.instance()
                .getListItems_RateGames();
        if (items == null) {
            Log.w(TAG, "items == null!");
            return itemView;
        }

        if (items[position] == null) {
            Log.w(TAG, "items[" + position + "] == null!");
            return itemView;
        }

        if (DEBUG_VERBOSE) {
            Log.d(TAG, String.format(
                    "items[%d].starRating=%d, selection=%d title=%s console%s",
                    position, items[position].starRating,
                    items[position].starRating + 1, items[position].title,
                    items[position].console));
        }

        holder.listPosition = position;
        holder.spinnerRating.setSelection(items[position].starRating + 1);
        holder.setViewsForSpinnerRating();

        holder.imgGameImg.setImageResource(items[position].imgResId);
        holder.txtGameTitle.setText(items[position].title);
        holder.txtGameConsole.setText(items[position].console);

        return itemView;
    }
}
