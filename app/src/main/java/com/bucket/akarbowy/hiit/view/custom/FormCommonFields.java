package com.bucket.akarbowy.hiit.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bucket.akarbowy.hiit.R;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class FormCommonFields extends LinearLayout {
    public FormCommonFields(Context context) {
        this(context, null);
    }

    public FormCommonFields(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormCommonFields(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.common_event_form_fields, this);
    }
}
