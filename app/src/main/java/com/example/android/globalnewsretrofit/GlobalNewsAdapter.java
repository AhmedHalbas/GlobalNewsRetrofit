package com.example.android.globalnewsretrofit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.globalnewsretrofit.Models.Article;

import java.util.List;

public class GlobalNewsAdapter extends ArrayAdapter<Article> {


    public GlobalNewsAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        ImageView articleImage = listItemView.findViewById(R.id.article_image);
        TextView articleTitle = listItemView.findViewById(R.id.article_title);
        TextView articleAuthor = listItemView.findViewById(R.id.article_author);
        TextView articleSource = listItemView.findViewById(R.id.article_source);
        ImageButton shareImageButton = listItemView.findViewById(R.id.share);


        final Article currentArticle = getItem(position);


        Glide.with(getContext()).load(currentArticle.getArticleImage()).fitCenter().into(articleImage);
        articleTitle.setText(currentArticle.getArticleTitle());
        articleSource.setText("Source:"+currentArticle.getArticleSource().getSourceName());
        articleAuthor.setText("Author:"+currentArticle.getArticleAuthor());



        shareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = currentArticle.getArticleLink();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        return listItemView;
    }
}
