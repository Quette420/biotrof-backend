package ru.volgau.graduatework.biotrofbackend.utils;

public class StringHelper {

    private StringHelper() {

    }

    public static String removeQuantityData(String str) {
        return str.substring(0, str.indexOf("(") - 1);
    }
}
