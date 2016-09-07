package com.bzilaji.tmdbclient;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date createDateFromString(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
