package com.example.tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class Java8Time {
    public static void main(String[] args) {
//        get current date and time
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("\t-\t-\t-\t-\t-");
        System.out.println("Current DateTime: " + currentTime);
        System.out.println("\t-\t-\t-\t-\t-");

        System.out.println("toLocalDate: " + currentTime.toLocalDate());
        System.out.println("\t-\t-\t-\t-\t-");

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("Month: " + month + " day: " + day + " seconds: " + seconds);
        System.out.println("\t-\t-\t-\t-\t-");

//        with day of month and year
        System.out.println(currentTime.withDayOfMonth(2).withYear(1995));
        System.out.println("\t-\t-\t-\t-\t-");

//        specific date
        System.out.println(LocalDate.of(1995, Month.FEBRUARY, 11));
        System.out.println("\t-\t-\t-\t-\t-");

//        specific hour and minutes
        System.out.println(LocalTime.of(18, 45));
        System.out.println("\t-\t-\t-\t-\t-");

//        parse time from string
        System.out.println(LocalTime.parse("18:45:15"));
        System.out.println("\t-\t-\t-\t-\t-");
    }
}
