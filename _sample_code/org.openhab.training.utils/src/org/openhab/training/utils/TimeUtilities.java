package org.openhab.training.utils;

import java.text.SimpleDateFormat;

public class TimeUtilities {

    public static String getCurrentTimeStamp() {

        long millis = System.currentTimeMillis();

        return (new SimpleDateFormat("hh" + ":" + "mm" + ":" + "ss").format(millis));
    }
}
