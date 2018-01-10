package eu.schoolproject.util;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import eu.schoolproject.R;

/**
 * Created by BP on 29/12/2017.
 */

public class Util {

    public final static ThreadLocal<DateFormat> mFancyDateTimeFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm");
        }
    };

    public final static ThreadLocal<DateFormat> mFancyDateFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy");
        }
    };

    public static void showInfo(final @NonNull Context context, final @NonNull String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(context.getResources().getString(R.string.dialog_ok), null)
                .create();

        alertDialog.show();
    }
}
