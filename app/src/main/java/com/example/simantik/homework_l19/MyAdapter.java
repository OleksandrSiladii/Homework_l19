package com.example.simantik.homework_l19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by simantik on 23.07.2015.
 */
public class MyAdapter extends BaseAdapter {

    private ArrayList<NotificationModel> mData;
    private LayoutInflater mLayoutInflater;


    public MyAdapter(Context _context, ArrayList<NotificationModel> _data) {
                mData = _data;
        mLayoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder viewHolder;

        if (itemView == null) {

            itemView = mLayoutInflater.inflate(R.layout.list_item_notification, null, false);

            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) itemView.findViewById(R.id.title);
            viewHolder.subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            viewHolder.message = (TextView) itemView.findViewById(R.id.message);

            itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }

        NotificationModel notificationModel = mData.get(position);
        viewHolder.title.setText(notificationModel.title);
        viewHolder.subtitle.setText(notificationModel.subtitle);
        viewHolder.message.setText(notificationModel.message);

        return itemView;
    }

    public class ViewHolder {
        public TextView title;
        public TextView subtitle;
        public TextView message;
    }
}