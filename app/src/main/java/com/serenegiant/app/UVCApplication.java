/*
******************* Copyright (c) ***********************\
**
**         (c) Copyright 2016, 蒋朋, china,. sd
**                  All Rights Reserved
**
**
**                       _oo0oo_
**                      o8888888o
**                      88" . "88
**                      (| -_- |)
**                      0\  =  /0
**                    ___/`---'\___
**                  .' \\|     |// '.
**                 / \\|||  :  |||// \
**                / _||||| -:- |||||- \
**               |   | \\\  -  /// |   |
**               | \_|  ''\---/''  |_/ |
**               \  .-\__  '-'  ___/-. /
**             ___'. .'  /--.--\  `. .'___
**          ."" '<  `.___\_<|>_/___.' >' "".
**         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
**         \  \ `_.   \_ __\ /__ _/   .-` /  /
**     =====`-.____`.___ \_____/___.-`___.-'=====
**                       `=---='
**
**
**     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
**
**               佛祖保佑         永无BUG
**
**
**                   南无本师释迦牟尼佛
**

**----------------------版本信息------------------------
** 版    本: V0.1
**
******************* End of Head **********************\
*/

package com.serenegiant.app;

import android.app.Application;

/**
 * file name: UVCApplication
 * Creator: 蒋朋
 *    Date: 16-9-14 15:04
 *  E-mail: jp19891017@gmail.com
 *     Url: https://jp1017.github.io/
 * Describe:
 *  Modify: aso
 *    Date：
 *    Note：
 */

public class UVCApplication extends Application {
    public static UVCApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static UVCApplication getInstance() {
        return sInstance;
    }

    public static class Result
    {

        public static class Name {
            public static final String VALUE = "ResultName";     // name of extra string in caller intent with scan result request that pass name of the result string
            public static final String DEFAULT = "QRCode"/*"QRName"*/;  // default name of the scan result string in intent extra string
        };; /* Name */

        public static class Intent {
            public static final String NAME = "com.serenegiant.usbcamerazxing.SCAN";
        }; /* Intent */

    }; /* Result */
}
