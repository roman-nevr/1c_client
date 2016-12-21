package com.example.dmitry.a1c_client.misc;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by roma on 16.06.2016.
 */
public class utils {
    public static final String LOG_TAG = "salary";
    public static void log(String message){
        Log.d(LOG_TAG, message);
    }
    public static void toastShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void toastLong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void snackBarShort(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
    public static void snackBarLong(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
    public static void nop(){
        /*dummy operation*/
    }
}
