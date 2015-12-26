package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.view.enums.MainTab;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class NotificationsActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, NotificationsActivity.class);

        return callingIntent;
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_notifications;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(MainActivity.SWITCH_TAB, MainTab.ACCOUNT.getPosition());
        intent.putExtras(bundle);

        return intent;
    }
}
