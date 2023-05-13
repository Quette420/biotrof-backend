package ru.volgau.graduatework.biotrofbackend.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

public class DateHelper {

    public static Date getFirstDayOfYear(int year) {
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1).with(firstDayOfYear());
        return Date.from(firstDayOfYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getLastDayOfYear(int year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 1, 1).with(TemporalAdjusters.lastDayOfYear());
        return Date.from(lastDayOfYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
