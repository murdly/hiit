package com.bucket.akarbowy.hiit.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 10.12.2015.
 */
@ParseClassName("Technology")
public class Technology extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    @DebugLog
    public JSONArray getTags() {
        return getJSONArray("tag");
    }

    public static ParseQuery<Technology> getQuery() {
        return ParseQuery.getQuery(Technology.class);
    }
}
