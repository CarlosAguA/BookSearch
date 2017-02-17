package com.example.android.booksearch;

/**
 * Created by Paviliondm4 on 2/12/2017.
 */

public class Book {
    private String author ;
    private String title ;
    private String description;
    private String imageUrl ;

    public Book (String title , String author, String description , String imageUrl){
        this.author = author ;
        this.title = title ;
        this.description = description ;
        this.imageUrl  = imageUrl ;
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

    public String getImageUrl(){
        return imageUrl ;
    }
}