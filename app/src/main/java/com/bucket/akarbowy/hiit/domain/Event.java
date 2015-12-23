package com.bucket.akarbowy.hiit.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by akarbowy on 10.12.2015.
 */
@ParseClassName("Event")
public class Event extends ParseObject {
    public static final String EVENT_COL_DATETIME = "datetime";

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public long getDateTimeInMillis() {
        return getLong(EVENT_COL_DATETIME);
    }

    public void setDateTime(long dateTime) {
        put(EVENT_COL_DATETIME, dateTime);
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

    public String getAuthorId() {
        return getParseUser("author").getObjectId();
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor() {
        put("author", ParseUser.getCurrentUser());
    }

    public boolean isCanceled(){
        return getBoolean("isCanceled");
    }

    public void setCanceled(boolean canceled){
        put("isCanceled", canceled);
    }

    public ParseRelation<ParseObject> getParticipantsRelation() {
        return getRelation("participants");
    }

    public static ParseQuery<Event> getQuery() {
        return ParseQuery.getQuery(Event.class);
    }
}
