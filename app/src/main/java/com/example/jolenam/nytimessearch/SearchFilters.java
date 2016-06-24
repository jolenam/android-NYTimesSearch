package com.example.jolenam.nytimessearch;

import java.io.Serializable;

/**
 * Created by jolenam on 6/23/16.
 */
public class SearchFilters implements Serializable {
    String month;
    String day;
    String year;
    String sortType;
    boolean checkedArts;
    boolean checkedFashion;
    boolean checkedSports;

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getYear() {
        return year;
    }


    public boolean isCheckedSports() {
        return checkedSports;
    }

    public String getSortType() {
        return sortType;
    }

    public boolean isCheckedArts() {
        return checkedArts;
    }

    public boolean isCheckedFashion() {
        return checkedFashion;
    }

    public SearchFilters(String month, String day, String year, String sortType, boolean checkedArts, boolean checkedFashion, boolean checkedSports) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.sortType = sortType;
        this.checkedArts = checkedArts;
        this.checkedFashion = checkedFashion;
        this.checkedSports = checkedSports;
    }
}
