package com.example.android.globalnewsretrofit.Models;

import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("urlToImage")
    private String articleImage;

    @SerializedName("title")
    private String articleTitle;

    @SerializedName("author")
    private String articleAuthor;

    @SerializedName("source")
    private Source articleSource;

    @SerializedName("url")
    private String articleLink;


    public String getArticleImage() {
        return articleImage;
    }



    public String getArticleTitle() {
        return articleTitle;
    }


    public String getArticleAuthor() {
        return articleAuthor;
    }


    public Source getArticleSource() {
        return articleSource;
    }


    public String getArticleLink() {
        return articleLink;
    }


}
