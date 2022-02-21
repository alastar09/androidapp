package ru.biis.biissale;
//rade_pr1@gtrhrthtr.ru
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.*;


import androidx.annotation.RequiresApi;

public class Callbiis {

    private String id;//Long
    private String title;
    private String date;
    private String client;
    private String count;
    private String commentid;

    public Callbiis(String id, String title, String date, String client, String count, String commentid) {
        this.id     = id;
        this.title  = title;
        this.date   = date;
        this.client = client;
        this.count  = count;
        this.commentid = commentid;
    }

    public String getId() {
        return id;
    }
    public String getText() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getClient() {
        return client;
    }
    public String getCount() {
        return count;
    }
    public String getCommentid() {
        if (!commentid.isEmpty()) {
            return commentid;
        } else {
            return "0";
        }
    }
    /*public String ParsDate(){
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        String hours=date.substring(11,13);
        String minutes=date.substring(14,16);
        String seconds=date.substring(17,19);
        String parsdate=day+"."+month+"."+year+"    "+hours+":"+minutes+":"+seconds;
        return parsdate;
    }*///my
    //@RequiresApi(api = Build.VERSION_CODES.O)
    public String ParsDate2(){

        if (date!="--.--.----"){
        ZonedDateTime result1 = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

        TimeZone time_zone_default= TimeZone.getDefault();
        //ZoneId defZone = time_zone_default.toZoneId();
        ZoneId defZone=ZoneId.systemDefault();
        ZonedDateTime result = result1.withZoneSameInstant(defZone);
        //ZonedDateTime result = result.withZoneSameInstant();
        LocalDateTime localDateTime = result.toLocalDateTime();

        // Custom format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Format LocalDateTime
        String formattedDateTime = localDateTime.format(formatter);


        return formattedDateTime;}
        else{String a="discon server";
            return a;}
    }
    public static String ParsDate3(String date2){

        if (date2!="--.--.----"){
            ZonedDateTime result1 = ZonedDateTime.parse(date2, DateTimeFormatter.ISO_DATE_TIME);

            TimeZone time_zone_default= TimeZone.getDefault();
            //ZoneId defZone = time_zone_default.toZoneId();
            ZoneId defZone=ZoneId.systemDefault();
            ZonedDateTime result = result1.withZoneSameInstant(defZone);
            //ZonedDateTime result = result.withZoneSameInstant();
            LocalDateTime localDateTime = result.toLocalDateTime();

            // Custom format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            // Format LocalDateTime
            String formattedDateTime = localDateTime.format(formatter);


            return formattedDateTime;}
        else{String a="discon server";
            return a;}
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Callbiis callbiis = (Callbiis) o;

        if (!id.equals(callbiis.id)) return false;
        if (!title.equals(callbiis.title)) return false;
        if (!client.equals(callbiis.client)) return false;
        if (!count.equals(callbiis.count)) return false;
        if (!commentid.equals(callbiis.commentid)) return false;
        return date != null ? date.equals(callbiis.date) : callbiis.date == null;
    }

}
