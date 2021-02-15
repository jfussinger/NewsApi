package com.example.android.newsapi.utils;

import com.example.android.newsapi.apiservice.APIService;
import com.example.android.newsapi.ui.search.SearchFragment;

import java.util.Locale;

public class Utils {

    private static final String[] CATEGORY = {"general", "business", "entertainment", "health",
            "science", "sports", "technology"};

    private static final String[] LANGUAGE = {"ar", "de", "en", "es", "fr",
            "he", "it", "nl", "no", "pt", "ru", "se", "ud", "zh"};

    private static final String[] COUNTRY = {"ae", "ar", "at", "au", "be", "bg", "br",
            "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id",
            "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no",
            "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr",
            "tw", "ua", "us", "ve", "za"};

    private String category;
    private String country = Locale.getDefault().getCountry().toLowerCase();
    private String language = null;
    private String keyword = SearchFragment.query;


    public String getCategory() {
        return category;
    }

    public void setCategory(APIService.Category category) {
        this.category = category.name();
        //this.category = category;
    }

    //public static String getLanguage(){
    public String getLanguage(){
        //Locale locale = Locale.getDefault();
        //String language = String.valueOf(locale.getLanguage());
        //return language.toLowerCase();
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    //public Static String getCountry(){
    public String getCountry(){
        //Locale locale = Locale.getDefault();
        //String country = String.valueOf(locale.getCountry()).toLowerCase();
        //return country.toLowerCase();
        return country;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}