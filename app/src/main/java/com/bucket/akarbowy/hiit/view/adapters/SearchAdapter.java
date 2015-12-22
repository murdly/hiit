package com.bucket.akarbowy.hiit.view.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.model.TechnologyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by arkadiuszkarbowy on 21/12/15.
 */

public class SearchAdapter extends ArrayAdapter<TechnologyModel> {

    public SearchAdapter(Context context, ArrayList<TechnologyModel> data) {
        super(context, R.layout.listview_search_technology_item, data);
    }

    @DebugLog
    public void setResultsList(List<TechnologyModel> resultsList) {
        clear();
        addAll(resultsList);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        @Bind(R.id.name)
        TextView name;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    @DebugLog
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_search_technology_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TechnologyModel technology = getItem(position);
        holder.name.setText(technology.getTitle());
        return convertView;
    }
}