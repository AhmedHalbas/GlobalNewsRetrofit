package com.example.android.globalnewsretrofit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.globalnewsretrofit.API.ApiClient;
import com.example.android.globalnewsretrofit.API.ApiInterface;
import com.example.android.globalnewsretrofit.Models.Article;
import com.example.android.globalnewsretrofit.Models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "3d5ac9894af14a73a79ec7019e027a42";
    private String countryName ;
    private List<Article> articles =  new ArrayList<>();
    private GlobalNewsAdapter globalNewsAdapter;
    private ListView listView;
    private Spinner spinner;
    private String[] countries = {"Egypt","United Arab Emirates","United Kingdom","United States","Germany","Australia"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list);
        spinner=findViewById(R.id.spinner);



        if (!isNetworkConnected())
        {

            listView.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);

            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle("INFORMATION");
            builder.setMessage("NO INTERNET CONNECTION");
            builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();



        }




        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loadJson();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(MainActivity.this, "No Country Selected", Toast.LENGTH_SHORT).show();

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Article currentArticle = articles.get(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentArticle.getArticleLink()));
                startActivity(intent);


            }
        });


    }



    private void loadJson()
    {
        switch(spinner.getSelectedItem().toString())
        {
            case "Egypt":
            {
                countryName="eg";
                break;
            }


            case "United Arab Emirates":
            {
                countryName="ae";
                break;
            }


            case "United Kingdom":
            {
                countryName="gb";
                break;
            }


            case "United States":
            {
                countryName="us";
                break;
            }


            case "Germany":
            {
                countryName="de";
                break;
            }

            case "Australia":
            {
                countryName="au";
                break;
            }






        }


        final ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<News> call;

        call=apiInterface.getNews(countryName,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                if(response.isSuccessful() && response.body().getArticle() !=null)
                {

                    if(!articles.isEmpty())
                    {
                        articles.clear();
                    }

                    articles=response.body().getArticle();
                    globalNewsAdapter= new GlobalNewsAdapter(MainActivity.this,articles);
                    listView.setAdapter(globalNewsAdapter);
                    globalNewsAdapter.notifyDataSetChanged();


                }



                else
                {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });



    }




    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        return isConnected;

    }





}
