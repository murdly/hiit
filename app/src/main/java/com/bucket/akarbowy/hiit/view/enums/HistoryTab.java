package com.bucket.akarbowy.hiit.view.enums;

import android.content.Context;

import com.bucket.akarbowy.hiit.R;

/**
 * Created by akarbowy on 26.12.2015.
 */
public enum HistoryTab {
    PARTICIPATED(0, R.string.historyTab_participated),
    ORGANIZED(1, R.string.historyTab_organized);

    private int mPosition;
    private int mTitleId;

    HistoryTab(int position, int titleId) {
        this.mPosition = position;
        this.mTitleId = titleId;
    }

    public int getPosition() {
        return mPosition;
    }


    public static String getTitle(Context context, int position) {
        int resId = PARTICIPATED.mTitleId;
        if (ORGANIZED.mPosition == position)
            resId = ORGANIZED.mTitleId;

        return context.getString(resId);
    }
}
