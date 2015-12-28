package com.bucket.akarbowy.hiit.view.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.enums.TechDrawable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arkadiuszkarbowy on 21/12/15.
 */

public class HistoryEventsAdapter extends ArrayAdapter<EventModel> {

    public HistoryEventsAdapter(Context context, ArrayList<EventModel> data) {
        super(context, R.layout.listview_history_event_item, data);
    }

    public void setEventsList(List<EventModel> resultsList) {
        clear();
        addAll(resultsList);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.datetime)
        TextView datetime;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listview_history_event_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EventModel eventModel = getItem(position);
        holder.icon.setImageDrawable(TechDrawable.getThumbnail(getContext(), eventModel.getTechnologyId()));
        holder.title.setText(eventModel.getTitle());
        holder.datetime.setText(String.format("%s, %s", eventModel.getDateAsString(),
                eventModel.getTimeAsString()));
        return convertView;
    }
}