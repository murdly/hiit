package com.bucket.akarbowy.hiit.view.enums;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.bucket.akarbowy.hiit.R;

import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 26.11.2015.
 */
public enum TechDrawable {
    CSHARP(R.drawable.ic_tech_csharp, R.drawable.account_bg, "Ph5AVQk3bE"),
    ANDROID(R.drawable.ic_tech_android, R.drawable.account_bg, "G46cDuXqVD"),
    IOS(R.drawable.ic_tech_ios, R.drawable.account_bg, "RuPy952wTc"),
    UNKNOWN(R.drawable.ic_tech_unknow, R.drawable.event_bg, null);

    private int mThumbnailDrawableId;
    private int mBackgroundDrawableId;
    private String mTechnologyId;

    TechDrawable(int mThumbnailDrawableId, int mBackgroundDrawableId, String mTechnologyId) {
        this.mThumbnailDrawableId = mThumbnailDrawableId;
        this.mBackgroundDrawableId = mBackgroundDrawableId;
        this.mTechnologyId = mTechnologyId;
    }


    public static Drawable getThumbnail(Context context, String techId) {
        int resId = UNKNOWN.mThumbnailDrawableId;
        if (equals(CSHARP, techId))
            resId = CSHARP.mThumbnailDrawableId;
        else if (equals(ANDROID, techId))
            resId = ANDROID.mThumbnailDrawableId;
        else if (equals(IOS, techId))
            resId = IOS.mThumbnailDrawableId;
        return ContextCompat.getDrawable(context, resId);
    }

    public static int getThumbnailId(String techId) {
        int resId = UNKNOWN.mThumbnailDrawableId;
        if (equals(CSHARP, techId))
            resId = CSHARP.mThumbnailDrawableId;
        else if (equals(ANDROID, techId))
            resId = ANDROID.mThumbnailDrawableId;
        else if (equals(IOS, techId))
            resId = IOS.mThumbnailDrawableId;
        return resId;
    }

    public static Drawable getBackground(Context context, String techId) {
        int resId = UNKNOWN.mBackgroundDrawableId;
        if (equals(CSHARP, techId))
            resId = CSHARP.mBackgroundDrawableId;
        else if (equals(ANDROID, techId))
            resId = ANDROID.mBackgroundDrawableId;
        else if (equals(IOS, techId))
            resId = IOS.mBackgroundDrawableId;

        return ContextCompat.getDrawable(context, resId);
    }

    @DebugLog
    private static boolean equals(TechDrawable tech, String title) {
        return tech.mTechnologyId.equals(title);
    }
}
