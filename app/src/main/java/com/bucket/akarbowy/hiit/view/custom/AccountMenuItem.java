package com.bucket.akarbowy.hiit.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class AccountMenuItem extends LinearLayout {
    public AccountMenuItem(Context context) {
        this(context, null);
    }

    public AccountMenuItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccountMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = inflate(context, R.layout.account_menu_item, this);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        TextView title = (TextView) v.findViewById(R.id.title);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AccountMenuItem,
                0, 0);

        try {
            Drawable drawable = a.getDrawable(R.styleable.AccountMenuItem_myicon);
            if (drawable != null)
                icon.setImageDrawable(drawable);

            title.setText(a.getString(R.styleable.AccountMenuItem_name));

        } finally {
            a.recycle();
        }
    }
}
