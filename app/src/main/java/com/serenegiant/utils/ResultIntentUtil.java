package com.serenegiant.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import com.serenegiant.app.UVCApplication;

public class ResultIntentUtil {

    static private String inner_tag = null;

    public static void setTag(String tag) {
        inner_tag = tag;
    }

    /**
     * analyze result of the caller intent parameters:
     * is the program called as an external client?
     * @param intent, tag
     * @return Boolean
     * ==> setTag(tag) need called before this method
     * ==> store intent in the keeptIntent inner static variable
     */
    public static Boolean isCalled4Result(Intent intent)
    {
        if (intent == null)
            Log.w("UVCCameraZxing", inner_tag + ": We don't have a Caller Intent");
        else {
            Log.w("UVCCameraZxing", inner_tag + ": We have a Caller Intent");
            if (intent.getAction().equals("com.serenegiant.usbcamerazxing.SCAN"))
            {
                Log.w("UVCCameraZxing", inner_tag + ": We called from external program for fetching result");
                keptIntent = intent;
                return true;
            }
            else Log.w("UVCCameraZxing", inner_tag + ": We called from system launcher");
        }; /* else if intent == null */
        return false;
    }; /* isCalled4Result */


    public static Intent createResult(@NonNull Intent callIntent, @NonNull String code)
    {
        keptIntent = callIntent;
        return createResult(code);
//        String name = callIntent.getStringExtra("ResultName");
//        if (name == null)
//            name = "QRCode";
//        if (name.isEmpty())
//            name = "QRCode";
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND);
////        intent.putExtra("QRCode", code);
//        intent.putExtra(name, code);
////        intent.setData(Uri.parse("www.sppp.qu/rda"));
//        //setResult(RESULT_OK, intent);
//        //finish();
//        return intent;
    }; /* createResult */


    static private Intent keptIntent;

    /**
     * Create result intent with String code.
     * Use keeped intent in keeptIntent inner static variable
     * @param code
     * @return Intent
     * ==> need called isCalled4Result(intent) before call this
     */
    public static Intent createResult(@NonNull String code)
    {
        String name = keptIntent.getStringExtra("ResultName");
        if (name == null)
            name = "QRCode";
        if (name.isEmpty())
            name = "QRCode";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(name, code);
        return intent;
    }; /* createResult */


}; /* ResultIntentUtil */
