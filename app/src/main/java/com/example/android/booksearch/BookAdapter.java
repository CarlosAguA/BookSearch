package com.example.android.booksearch;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paviliondm4 on 2/12/2017.
 */

public class BookAdapter  extends ArrayAdapter<Book> {

    private Book currentBook;
    private Context context;
    ViewHolder holder ;

    public BookAdapter (Activity context, List<Book> book ){
        super(context,0 ,book);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_book_item, parent, false);
            holder = new ViewHolder();

            //Find the Imageview with the view ID imageView for setting the picture of the book
            holder.bookImageView = (ImageView) listItemView.findViewById(R.id.imageView);

            // Find the TextView with view ID tv1 for setting title
            holder.titleTextView = (TextView) listItemView.findViewById(R.id.tv1) ;

            // Find the TextView with view ID tv2 for setting author
            holder.authorTextView = (TextView) listItemView.findViewById(R.id.tv2) ;

            // Find the TextView with view ID magnitude tv3 for setting description
            holder.descriptionTextView = (TextView) listItemView.findViewById(R.id.tv3) ;

            holder.positionTextView = (TextView) listItemView.findViewById(R.id.position) ;

            //Fixing bad "memory" of te view
            listItemView.setTag(holder);
        }

        currentBook = getItem(position);
        holder = (ViewHolder) listItemView.getTag();

        //Check if the currentBook has an empty imageUrl / path. If so, remove visibility of the
        //imageView. Otherwise use picasso library and show the image
        if (currentBook.getImageUrl() ==  null || currentBook.getImageUrl().isEmpty() ){
            holder.bookImageView.setImageResource(R.drawable.thumbnail);
        }else {
            //Implement library specific code lines for adding the image url
            Picasso.with(context)
                    .load(currentBook.getImageUrl())
                    .into(holder.bookImageView);
        }

        // Display the magnitude of the current book in that TextView
        holder.titleTextView.setText(currentBook.getTitle()) ;

        // Display the magnitude of the current book in that TextView
        holder.authorTextView.setText( formatAuthor(currentBook.getAuthor()));

        // Display the magnitude of the current book in that TextView
        holder.descriptionTextView.setText(currentBook.getDescription()) ;

        holder.positionTextView.setText("The position is: " + String.valueOf(position));



        return listItemView ;
    }

    /* Method that formats the author string in a proper way.
       Since the JSON ARRAY response returns a string like [J.k.Rowling,Hermione Granger,Draco Malfoy];
       this method removes the brackets and formats the author string in a proper way.
     */
    private String formatAuthor (String authors){

        return authors.replace("[", "").replace("]", "").replace("\"","").replace(",",", ") ;
    }

    static class ViewHolder{
        ImageView bookImageView ;
        TextView titleTextView ;
        TextView authorTextView ;
        TextView descriptionTextView;
        TextView positionTextView;
    }

}
