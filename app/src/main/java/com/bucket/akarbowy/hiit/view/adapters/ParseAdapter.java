package com.bucket.akarbowy.hiit.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by akarbowy on 23.12.2015.
 */
public class ParseAdapter<T extends ParseObject> extends ParseQueryAdapter<T> {


    public ParseAdapter(Context context, QueryFactory<T> queryFactory) {
        super(context, queryFactory);
    }

    public int getItemPositionById(String objectId) {
        for (int i = 0; i < getCount(); i++) {
            T object = getItem(i);
            if(object.getObjectId().equals(objectId))
                return i;
        }
        return 0;
    }

    @Override
    public View getItemView(T object, View v, ViewGroup parent) {
        if(v == null)
            v = View.inflate(getContext(), R.layout.spinner_technology_item, null);

        super.getItemView(object, v, parent);

        ((TextView)v.findViewById(R.id.name)).setText(object.getString("title"));
        return v;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }


}
