QR code recognition with USB serial camera 
=========

[![Build Status](https://travis-ci.org/jp1017/UVCCameraZxing.svg?branch=master)](https://travis-ci.org/jp1017/UVCCameraZxing)

## 2.40.5
Released sending a response intent with a recognized QR code string.
Code snippet for calling UVCCameraZXing for receive recognized QR-code string:

    Intent intent = new Intent("com.serenegiant.usbcamerazxing.SCAN");
    startActivityForResult(intent, QR.Request.Code);
    
Code example for receiving returned intent:

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QR.Request.Code) {
            if (resultCode == RESULT_OK) {
                String contents = intent.intent.getStringExtra("QRCode"));
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }


 

## 2.40.1
Base translation interfase to english

## 2.40
1. Upgrade libuvccamera library
2. Upgrade zxing library to 3.3.2
3. Generate color QR code

The usb camera recognizes the QR code, involving Android technology:

1. Used TextureView
2. Inter-process communication, aidl
3. Service use
4. QR code/bar code recognition, support selection of image recognition
5. USB connection

+ Run the project and connect the USB camera to automatically recognize the QR code

|Scan QR Code|Recognition Results|Select Album|
|:---:|:---:|:---:|
|<img src="./1.png" width="320"/>|<img src="./2.png" width="320"/>|<img src="./3.png" width="320"/>|



The barcode can also be recognized as well, please refer to my blog \[original author's blog - A.S.] for detailed usage: [USB camera preview recognition QR code](https://jp1017.github.io/2016/09/15/USB%E6%91%84%E5%83%8F%E5%A4%B4%E9%A2%84%E8%A7%88%E8%AF%86%E5%88%AB%E4%BA%8C%E7%BB%B4%E7%A0%81/)

reference：

+ https://github.com/saki4510t/UVCCamera/tree/master/usbCameraTest4
+ https://github.com/yipianfengye/android-zxingLibrary