package com.example.majorReview.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class ReviewTime {

    public static String LocalDateTimeFormatting (LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = time.format(formatter);
        return formattedDate;
    }

    public static Long LocalDateTimeDifference(LocalDateTime time1, LocalDateTime time2){
        long hours = ChronoUnit.HOURS.between(time1, time2);
        return hours;
    }


}
