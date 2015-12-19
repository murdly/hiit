package com.bucket.akarbowy.hiit.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.bucket.akarbowy.hiit.R;

/**
 * Created by akarbowy on 19.12.2015.
 */
public class YesNoDialog extends DialogFragment {

    private OnActionListener mOnActionListener;

    public interface OnActionListener {
        void onPositiveButton();
    }

    public static YesNoDialog newInstance(OnActionListener onClickListener) {
        YesNoDialog dialog = new YesNoDialog();
        dialog.setOnTitleSetListener(onClickListener);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.dialog_msg_cancel));
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), null);
        builder.setPositiveButton(getResources().getString(R.string.event_cancel), mPositiveButtonListener);

        return builder.create();
    }

    private DialogInterface.OnClickListener mPositiveButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mOnActionListener.onPositiveButton();
        }
    };

    private void setOnTitleSetListener(OnActionListener onClickListener) {
        mOnActionListener = onClickListener;
    }
}