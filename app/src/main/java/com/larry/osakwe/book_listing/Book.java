package com.larry.osakwe.book_listing;

/**
 * Created by Larry Osakwe on 6/24/2017.
 */

public class Book {

    private String title;
    private String author;
    private String image;


    public Book(String title, String author, String image) {
        this.title = title;
        this.author = author;
        this.image =  image;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }
}
