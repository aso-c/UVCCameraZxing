package com.serenegiant.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class ResultIntentUtil {

    /**
     * analyse of the caller intent parameters: is the program
     * called as an external client?
     * @param intent, tag
     * @return Boolean
     */
    public static boolean isCalling4Result(Intent intent)
    {
        if (intent != null)
            if (intent.getAction().equals("com.serenegiant.usbcamerazxing.SCAN"))
                return true;
        return false;

    }; /* isCalling4Result */


    /**
     * analyse & logging analyse result of the caller intent parameters:
     * is the program called as an external client?
     * @param intent, tag
     * @return Boolean
     */
    public static void isCalling4ResultLog(Intent intent, @NonNull String tag)
    {
        if (intent == null)
            Log.w("UVCCameraZxing", tag + ": We don't have a Caller Intent");
        else {
            Log.w("UVCCameraZxing", tag + ": We have a Caller Intent");
            if (intent.getAction().equals("com.serenegiant.usbcamerazxing.SCAN"))
                Log.w("UVCCameraZxing", tag + ": We called from external program for fetching result");
            else Log.w("UVCCameraZxing", tag + ": We called from system launcher");
        }; /* else if intent == null */
    }; /* isScannerClientCallerIntent */


    public static Intent createResult(@NonNull String code)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("QRCode", code);
        intent.setData(Uri.parse("www.sppp.qu/rda"));
        //setResult(RESULT_OK, intent);
        //finish();
        return intent;
    }; /* createResult */

}; /* ResultIntentUtil */
