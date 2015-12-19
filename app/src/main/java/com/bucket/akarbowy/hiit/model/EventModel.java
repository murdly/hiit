package com.bucket.akarbowy.hiit.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class EventModel {
    private final String id;
    private String title;
    private String technologyId;
    private long dateTime;
    private String localization;
    private String description;
    private String authorId;

    public EventModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public long getDateTimeInMillis() {
        return dateTime;
    }

    public Date getDate() {
        Date date = new Date();
        date.setTime(dateTime);
        return date;
    }

    public String getDateAsString() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault());
        return mDateFormat.format(dateTime);
    }

    public void setDateTime(long dateInMillis) {
        this.dateTime = dateInMillis;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}