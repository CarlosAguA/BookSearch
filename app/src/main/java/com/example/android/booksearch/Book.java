package com.example.android.booksearch;

/**
 * Created by Paviliondm4 on 2/12/2017.
 */

public class Book {
    private String author ;
    private String title ;
    private String description;
    private int picture ;

    public Book (String title , String author, String description ){
        this.author = author ;
        this.title = title ;
        this. description = description ;
    }

    public String getAuthor(){
        return author ;
    }

    public String getTitle(){
        return title ;
    }

    public String getDescription(){
        return description ;
    }
}