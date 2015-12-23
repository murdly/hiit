package com.bucket.akarbowy.hiit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class TechnologyModel implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
    }

    protected TechnologyModel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<TechnologyModel> CREATOR = new Parcelable.Creator<TechnologyModel>() {
        public TechnologyModel createFromParcel(Parcel source) {
            return new TechnologyModel(source);
        }

        public TechnologyModel[] newArray(int size) {
            return new TechnologyModel[size];
        }
    };
}