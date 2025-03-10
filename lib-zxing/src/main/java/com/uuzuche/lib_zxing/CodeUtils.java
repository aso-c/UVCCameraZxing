package com.uuzuche.lib_zxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Vector;

/**
 * QR code scanning tools
 */
public class CodeUtils {
    private final static int COLOR_WECHAT = 0xff009900;
    private final static int COLOR_ALIPAY = 0xff4a86e8;

    public static void analyzeBitmap(Bitmap bitmap, AnalyzeCallback analyzeCallback) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();

        // Decoded parameters
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>(2);
        // Encoding types that can be parsed
        Vector<BarcodeFormat> decodeFormats = new Vector<>();
        // Set the scannable type here, I chose to support all of them here
        decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        // Set the continued character encoding format to UTF8
        // hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        // Set parsing configuration parameters
        multiFormatReader.setHints(hints);

        // Start to decode image resources
        Result rawResult = null;
        try {
            rawResult = multiFormatReader.decodeWithState(
                    new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(bitmap))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rawResult != null) {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeSuccess(bitmap, rawResult.getText());
            }
        } else {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeFailed();
            }
        }
    }


    /**
     * Analyze QR code image tools according to the image path
     *
     * @param analyzeCallback Parsing callback
     */

    public static void analyzeBitmap(String path, AnalyzeCallback analyzeCallback) {

        /**
         * First determine the size of the picture. If the picture is too large,
         * perform the cropping operation of the picture to prevent OOM
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 400);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        mBitmap = BitmapFactory.decodeFile(path, options);

        analyzeBitmap(mBitmap, analyzeCallback);
    }

    /**
     * 生成二维码图片
     *
     * @param text 二维码字符串
     * @param w 宽度
     * @param h 高度
     * @param logo 显示图标
     * @param isWechat 是否是微信收款
     * @return 二维码图片
     */
    public static Bitmap createImage(String text, int w, int h, Bitmap logo, boolean isWechat) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        try {
            Bitmap scaleLogo = getScaleLogo(logo, w, h);

            int offsetX = w / 2;
            int offsetY = h / 2;

            int scaleWidth = 0;
            int scaleHeight = 0;
            if (scaleLogo != null) {
                scaleWidth = scaleLogo.getWidth();
                scaleHeight = scaleLogo.getHeight();
                offsetX = (w - scaleWidth) / 2;
                offsetY = (h - scaleHeight) / 2;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (x >= offsetX && x < offsetX + scaleWidth && y >= offsetY && y < offsetY + scaleHeight) {
                        int pixel = scaleLogo.getPixel(x - offsetX, y - offsetY);
                        if (pixel == 0) {
                            if (bitMatrix.get(x, y)) {
                                pixel = COLOR_ALIPAY;
                                if (isWechat) {
                                    pixel = COLOR_WECHAT;
                                }
                            } else {
                                pixel = 0xffffffff;
                            }
                        }
                        pixels[y * w + x] = pixel;
                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * w + x] = COLOR_ALIPAY;
                            if (isWechat) {
                                pixels[y * w + x] = COLOR_WECHAT;
                            }
                        } else {
                            pixels[y * w + x] = 0xffffffff;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap getScaleLogo(Bitmap logo, int w, int h) {
        if (logo == null) return null;
        Matrix matrix = new Matrix();
        float scaleFactor = Math.min(w * 1.0f / 5 / logo.getWidth(), h * 1.0f / 5 / logo.getHeight());
        matrix.postScale(scaleFactor, scaleFactor);
        return Bitmap.createBitmap(logo, 0, 0, logo.getWidth(), logo.getHeight(), matrix, true);
    }

    /**
     * 解析二维码结果
     */
    public interface AnalyzeCallback {

        void onAnalyzeSuccess(Bitmap mBitmap, String result);

        void onAnalyzeFailed();
    }

}
