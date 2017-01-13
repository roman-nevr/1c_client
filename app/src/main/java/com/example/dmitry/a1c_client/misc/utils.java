package com.example.dmitry.a1c_client.misc;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roma on 16.06.2016.
 */
public class utils {
    public static final String LOG_TAG = "salary";

    public static void log(String message) {
        Log.d(LOG_TAG, message);
    }

    public static void toastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void snackBarShort(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBarLong(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void nop() {
        /*dummy operation*/
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void snackBarWithAction(View view, String message, String action, int maxLines, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(action, listener);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(15);
        snackbar.show();
    }

    public static void snackBarWithAction(View view, String message, String action, View.OnClickListener listener) {
        snackBarWithAction(view, message, action, 2, listener);
    }

    public static void delay(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static SimpleDateFormat fullFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static SimpleDateFormat shortFormat = new SimpleDateFormat("dd.MM HH:mm:ss");
    //0123456789012345678
    //2016-12-29T15:20:06
    public static Date parseOdataTime(String oDataTime) throws IllegalArgumentException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date result;
        try {
            result =  df.parse(oDataTime);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return result;
    }


}
