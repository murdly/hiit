package com.bucket.akarbowy.hiit.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ListView;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class LimitedListView extends ListView {
    private static final int MAX_HEIGHT = 180;
    public LimitedListView(Context context) {
        super(context);
    }

    public LimitedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MAX_HEIGHT, getResources()
                .getDisplayMetrics());
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(px, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
