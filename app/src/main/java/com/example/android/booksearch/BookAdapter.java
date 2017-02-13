package com.example.android.booksearch;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paviliondm4 on 2/12/2017.
 */

public class BookAdapter  extends ArrayAdapter<Book> {

    private Book currentBook;

    public BookAdapter (Activity context, List<Book> book ){
        super(context,0 ,book);

    }

    @NonNull
    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_book_item, parent, false);
        }

        currentBook = getItem(position);

        // Find the TextView with view ID tv1 for setting title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tv1) ;
        // Display the magnitude of the current earthquake in that TextView
        titleTextView.setText(currentBook.getTitle()) ;

        // Find the TextView with view ID tv2 for setting author
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.tv2) ;
        // Display the magnitude of the current earthquake in that TextView
        authorTextView.setText( formatAuthor(currentBook.getAuthor()));



        // Find the TextView with view ID magnitude tv3 for setting description
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.tv3) ;
        // Display the magnitude of the current earthquake in that TextView
        descriptionTextView.setText(currentBook.getDescription()) ;


        return listItemView ;
    }

    private String formatAuthor (String authors){

        return authors.replace("[", "").replace("]", "").replace("\"","").replace(",",", ") ;
    }


}