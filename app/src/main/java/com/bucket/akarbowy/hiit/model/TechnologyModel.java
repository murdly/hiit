package com.bucket.akarbowy.hiit.model;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class TechnologyModel {
    private final String id;
    private String title;


    public TechnologyModel(String id) {
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

}