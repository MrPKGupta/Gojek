package com.example.gojek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public final class Utils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DAY_FORMAT = "EEEE";

    private Utils() {
        //Private Constructor
    }

    public static String getDayFromDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat(DAY_FORMAT, Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateString);
            return dayFormat.format(date);
        } catch (ParseException e) {
            Timber.d("Error in parsing date.");
        }
        return "";
    }
}
