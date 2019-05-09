package com.example.android.globalnewsretrofit.Models;

import com.google.gson.annotations.SerializedName;

public class Source {


    @SerializedName("name")
    private String sourceName;


    public String getSourceName() {
        return sourceName;
    }

}
