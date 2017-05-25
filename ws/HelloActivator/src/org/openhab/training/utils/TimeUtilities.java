package org.openhab.training.utils;

public class TimeUtilities {

    public static String getCurrentTimeStamp() {
        long millis = System.currentTimeMillis();
        String time = Long.toString(millis);
        return time;
    }
}
