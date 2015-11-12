package com.jeffe.gtp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffe.gtp.GTP_DataMgr.ListItem_ListGames;

/**
 * The list adapter implementation for the ListGamesActivity
 * 
 */
public class ListGames_ListAdapter extends BaseAdapter {
    private static final String TAG = ListGames_ListAdapter.class
            .getSimpleName();

    static class ViewHolder {
        ImageView imgGameImg;
        TextView txtGameTitle;
        CheckBox cbGameFinished;
        TextView txtGameConsole;
        int listPosition;
    }

    private class FinishedClickListener implements CheckBox.OnClickListener {
        ViewHolder mHolder;

        private FinishedClickListener() {

        }

        FinishedClickListener(ViewHolder holder) {
            this();
            mHolder = holder;
        }

        @Override
        public void onClick(View v) {

            ListItem_ListGames[] items = GTP_DataMgr.instance()
                    .getListItems_ListGames();
            ListItem_ListGames item = items[mHolder.listPosition];

            GTP_DataMgr.instance().updateFinished(item.title, item.console,
                    item.imgResId, mHolder.cbGameFinished.isChecked());
        }
    }

    public ListGames_ListAdapter() {

    }

    @Override
    public int getCount() {
        ListItem_ListGames[] items = GTP_DataMgr.instance()
                .getListItems_ListGames();
        int retval = items == null ? 0 : items.length;
        // Log.d(TAG, "list items count=" + retval);
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
            itemView = inflater.inflate(R.layout.layout_listgamesrow, parent,
                    false);
            holder = new ViewHolder();
            holder.imgGameImg = (ImageView) itemView
                    .findViewById(R.id.imgGameImg);
            holder.txtGameTitle = (TextView) itemView
                    .findViewById(R.id.txtGameTitle);
            holder.cbGameFinished = (CheckBox) itemView
                    .findViewById(R.id.cbGameFinished);
            holder.txtGameConsole = (TextView) itemView
                    .findViewById(R.id.txtGameConsole);
            holder.cbGameFinished.setOnClickListener(new FinishedClickListener(
                    holder));
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        ListItem_ListGames[] items = GTP_DataMgr.instance()
                .getListItems_ListGames();
        if (items == null) {
            Log.w(TAG, "items == null!");
            return itemView;
        }

        if (items[position] == null) {
            Log.w(TAG, "items[" + position + "] == null!");
            return itemView;
        }

        holder.listPosition = position;
        holder.imgGameImg.setImageResource(items[position].imgResId);
        holder.txtGameTitle.setText(items[position].title);
        holder.cbGameFinished.setChecked(items[position].finished);
        holder.txtGameConsole.setText(items[position].console);

        return itemView;
    }
}
