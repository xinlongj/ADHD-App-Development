package com.example.joker.air_microsoftband;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by joker on 4/19/16(110).
 */
public class Constant {
    public static boolean checkEditText() {
        if(username == null || username.length() <=0 || date == null || date.length() <= 0 || actType == null || actType.length() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String createDataFolder() {
        if(username == null || username.length() < 1 || date == null || date.length() < 1) {
            return null;
        }
        String folderPath = Environment.getExternalStorageDirectory() + "/AIR/" + username + "_" + date + "/";
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();

        }
        dataFolderPath = folderPath;
        int i = 1;

        for(; ; i++) {
            accFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "Accelerometer.txt";
            File accFile = new File(accFilePath);
            if (!accFile.exists()) {
                gyrFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "Gyroscope.txt";
                disFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "Distance.txt";
                heartRateFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "HeartRate.txt";
                pedFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "Pedometer.txt";
                skinTempFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "SkinTemperature.txt";
                uvFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "UltraViolet.txt";
                bandContactFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "BandContact.txt";
                calFilePath = dataFolderPath + actType + "(" + i + ")" + "_" + "Calories.txt";
                break;
            } else {
            }
        }
        return username + "_" + date + "/" + actType + "(" + i + ")";
    }

    public static final int TYPE_STATUS       = 0x01;
    public static final int TYPE_ACC          = 0x02;
    public static final int TYPE_GYR          = 0x03;
    public static final int TYPE_DIS          = 0x04;
    public static final int TYPE_HEART_RATE   = 0x05;
    public static final int TYPE_PED          = 0x06;
    public static final int TYPE_SKIN_TEMP    = 0x07;
    public static final int TYPE_UV           = 0x08;
    public static final int TYPE_BAND_CONTACT = 0x09;
    public static final int TYPE_CAL          = 0x10;

    public static String username = null;
    public static String date = null;
    public static String actType = null;
    public static String dataFolderPath = null;
    public static String accFilePath = null;
    public static String gyrFilePath = null;
    public static String disFilePath = null;
    public static String heartRateFilePath = null;
    public static String pedFilePath = null;
    public static String skinTempFilePath = null;
    public static String uvFilePath = null;
    public static String bandContactFilePath = null;
    public static String calFilePath = null;
}