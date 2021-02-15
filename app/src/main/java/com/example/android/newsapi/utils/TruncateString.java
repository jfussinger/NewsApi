package com.example.android.newsapi.utils;

public class TruncateString {

    public static String truncateExtra(String content) {
        if (content == null)
            return "";
        return content.replaceAll("(\\[\\+\\d+ chars])", "");
    }
}
