package com.megacitycabservice.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String formatDate(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a"); // AM/PM format
        if (timestamp != null) {
            return sdf.format(timestamp);
        }
        return null;
    }
}
