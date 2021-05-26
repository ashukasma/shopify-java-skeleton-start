package com.lucent.skeleton.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    public static boolean isNullOrEmpty(Integer s) {
        if (s == null) {
            return true;
        } else {
            return isNullOrEmpty(s.toString());
        }
    }

    public static boolean isNullOrEmpty(Double s) {
        if (s == null) {
            return true;
        } else {
            return isNullOrEmpty(s.toString());
        }
    }

    public static boolean isNull(String s) {
        return isNullOrEmpty(s) || "null".equalsIgnoreCase(s) || "undefined".equalsIgnoreCase(s);
    }

    /**
     * @param request
     * @param key
     * @return true if request.getParameter(key) is null or empty string or
     * "null"
     */
    public static boolean isNotNullOrEmpty(HttpServletRequest request, String key) {
        return !isNull(request, key);
    }

    /**
     * @param request
     * @param key
     * @return true if request.getParameter(key) is null or empty string or
     * "null"
     */
    public static boolean isNull(HttpServletRequest request, String key) {
        return isNullOrEmpty(request.getParameter(key)) || request.getParameter(key).equals("null");
    }

    public static String ConvertToUTCString(String lastUpdated, String timeZone){
        DateTime lastUpdateDta = new DateTime(lastUpdated).withZone(DateTimeZone.forID(timeZone));
        lastUpdateDta  = lastUpdateDta.toDateTime(DateTimeZone.UTC);
        lastUpdated = lastUpdateDta.toString();
        return lastUpdated;
    }

    public static Date ConvertToUTCDate(String lastUpdated, String timeZone){
        DateTime lastUpdateDta = new DateTime(lastUpdated).withZone(DateTimeZone.forID(timeZone));
        lastUpdateDta  = lastUpdateDta.toDateTime(DateTimeZone.UTC);
        return lastUpdateDta.toDate();
    }

    public static Date ConvertToUTCDate(Date lastUpdated, String timeZone){
        DateTime lastUpdateDta = new DateTime(lastUpdated).withZone(DateTimeZone.forID(timeZone));
        lastUpdateDta  = lastUpdateDta.toDateTime(DateTimeZone.UTC);
        return lastUpdateDta.toDate();
    }

    public static String ConvertUTCToISOStringWithStoreTimeZone(Date lastUpdated, String timeZone){
        DateTime dta = new DateTime(lastUpdated).withZone(DateTimeZone.UTC);
        dta  = dta.toDateTime(DateTimeZone.forID(timeZone));
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return fmt.print(dta);
    }

    public static String ConvertUTCToISOString(Date lastUpdated, String timeZone){
        DateTime dta = new DateTime(lastUpdated).withZone(DateTimeZone.UTC);
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return fmt.print(dta);
    }

    public static String generateCurrentDateTimeFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSZ");
        return dateFormat.format((new Date()));
    }
}
