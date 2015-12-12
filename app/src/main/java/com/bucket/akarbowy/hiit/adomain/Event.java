package com.bucket.akarbowy.hiit.adomain;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by akarbowy on 10.12.2015.
 */
@ParseClassName("Event")
public class Event extends ParseObject {


    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public long getDateTimeInMillis(){
        return getLong("datetime");
    }

    public void setDateTime(long dateTime) {
        put("datetime", dateTime);
    }

    public String getLocalization() {
        return getString("description");
    }

    public void setLocalization(String localization) {
        put("localization", localization);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public ParseUser getAuthorId() {
        return getParseUser("author");
    }

    public void setAuthor() {
        put("author", ParseUser.getCurrentUser());
    }

    public static ParseQuery<Event> getQuery() {
        return ParseQuery.getQuery(Event.class);
    }


}