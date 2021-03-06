
package com.bucket.akarbowy.hiit.exception;

import android.content.Context;

import com.bucket.akarbowy.hiit.R;

import java.text.ParseException;

/**
 * Created by akarbowy on 10.12.2015.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.error_msg_default);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.error_msg_noconnection);
        } else if (exception instanceof FormNotValidException) {
            message = context.getString(R.string.error_msg_not_valid_form);
        } else if (exception instanceof ParseException) {
            message = context.getString(R.string.error_msg_server_error);
        } else if (exception instanceof SubInvolvedException) {
            message = context.getString(R.string.error_msg_sub_involved);
        }

        return message;
    }
}
