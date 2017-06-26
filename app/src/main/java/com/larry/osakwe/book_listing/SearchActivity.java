package com.larry.osakwe.book_listing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    String searchTerm = getIntent().getExtras().getString("searchEntry");
    private String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


    }
}
