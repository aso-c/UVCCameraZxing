package com.serenegiant.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import com.serenegiant.app.UVCApplication;

public class ResultIntentUtil {

    /**
     * analyse of the caller intent parameters: is the program
     * called as an external client?
     * @param intent, tag
     * @return Boolean
     */
    public static boolean isCalled4Result(Intent intent)
    {
        if (intent != null)
            if (intent.getAction().equals("com.serenegiant.usbcamerazxing.SCAN"))
                return true;
        return false;

    }; /* isCalling4Result */

    static private String inner_tag = null;

    public static void setTag(String tag) {
        inner_tag = tag;
    }

    /**
     * analyse & logging analyse result of the caller intent parameters:
     * is the program called as an external client?
     * @param intent, tag
     * @return Boolean
     */
//    public static void isCalled4ResultLog(Intent intent)
    public static Boolean isCalled4ResultLog(Intent intent)
    {
        if (intent == null)
            Log.w("UVCCameraZxing", inner_tag + ": We don't have a Caller Intent");
        else {
            Log.w("UVCCameraZxing", inner_tag + ": We have a Caller Intent");
            if (intent.getAction().equals("com.serenegiant.usbcamerazxing.SCAN"))
//            if (isCalled4Result(intent))
            {
                Log.w("UVCCameraZxing", inner_tag + ": We called from external program for fetching result");
                return true;
            }
            else Log.w("UVCCameraZxing", inner_tag + ": We called from system launcher");
        }; /* else if intent == null */
        return false;
    }; /* isCalled4ResultLog */

    /**
     * analyse & logging analyse result of the caller intent parameters:
     * is the program called as an external client?
     * set inner_tag by tag
     * @param intent, tag
     * @return Boolean
     */
//    public static void isCalled4ResultLog(Intent intent, @NonNull String tag)
    public static boolean isCalled4ResultLog(Intent intent, @NonNull String tag)
    {
        setTag(tag);
        return isCalled4ResultLog(intent);
//        if (intent == null)
//            Log.w("UVCCameraZxing", tag + ": We don't have a Caller Intent");
//        else {
//            Log.w("UVCCameraZxing", tag + ": We have a Caller Intent");
////            if (intent.getAction().equals("com.serenegiant.usbcamerazxing.SCAN"))
//            if (isCalled4Result(intent))
//                Log.w("UVCCameraZxing", tag + ": We called from external program for fetching result");
//            else Log.w("UVCCameraZxing", tag + ": We called from system launcher");
//        }; /* else if intent == null */
    }; /* isCalled4ResultLog */


    public static Intent createResult(@NonNull Intent callIntent, @NonNull String code)
    {
        String name = callIntent.getStringExtra("ResultName");
        if (name == null)
            name = "QRCode";
        if (name.isEmpty())
            name = "QRCode";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra("QRCode", code);
        intent.putExtra(name, code);
//        intent.setData(Uri.parse("www.sppp.qu/rda"));
        //setResult(RESULT_OK, intent);
        //finish();
        return intent;
    }; /* createResult */

    static private Intent keptIntent;

    public static Intent createResult(@NonNull String code)
    {
        String name = keptIntent.getStringExtra("ResultName");
        if (name == null)
            name = "QRCode";
        if (name.isEmpty())
            name = "QRCode";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra("QRCode", code);
        intent.putExtra(name, code);
        return intent;
    }; /* createResult */


}; /* ResultIntentUtil */
