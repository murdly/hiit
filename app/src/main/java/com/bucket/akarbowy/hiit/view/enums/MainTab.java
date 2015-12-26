package com.bucket.akarbowy.hiit.view.enums;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.bucket.akarbowy.hiit.R;

/**
 * Created by akarbowy on 26.11.2015.
 */
public enum MainTab {
    RSS (0, R.drawable.ic_rss, R.string.tab_rss),
    PARTICIPATE(1, R.drawable.ic_participate, R.string.tab_participate),
    ACCOUNT(2, R.drawable.ic_account, R.string.tab_account);

    private int mPosition;
    private int mDrawableId;
    private int mTitleId;

    MainTab(int position, int drawableId, int titleId) {
        this.mPosition = position;
        this.mDrawableId = drawableId;
        this.mTitleId = titleId;
    }

    public int getPosition(){
        return mPosition;
    }

    public static Drawable getIcon(Context context, int position){
        int resId = RSS.mDrawableId;
        if(PARTICIPATE.mPosition == position)
           resId = PARTICIPATE.mDrawableId;
        else if(ACCOUNT.mPosition == position)
           resId = ACCOUNT.mDrawableId;

      return ContextCompat.getDrawable(context, resId);
    }

    public static String getTitle(Context context, int position){
        int resId = RSS.mTitleId;
        if(PARTICIPATE.mPosition == position)
            resId = PARTICIPATE.mTitleId;
        else if(ACCOUNT.mPosition == position)
            resId = ACCOUNT.mTitleId;

        return context.getString(resId);
    }
}
