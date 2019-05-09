package com.example.android.globalnewsretrofit.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {


    @SerializedName("articles")

    private List<Article> article;


    public List<Article> getArticle() {
        return article;
    }



}
