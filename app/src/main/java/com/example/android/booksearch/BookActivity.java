package com.example.android.booksearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import static com.example.android.booksearch.QueryUtils.LOG_TAG;

public class BookActivity extends AppCompatActivity {

    InternetConnection googleBooks;
    /** URL to query the Google Books for books information */
    private static final String BOOKS_REQUEST_URL =
            " https://www.googleapis.com/books/v1/volumes?q=subject+energy+informatics ";

    // ListView bookListView ; caso d3e ponerlo como en UDACITY GITHUB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Start the AsyncTask to fetch the earthquake data
        googleBooks = new InternetConnection() ;
        googleBooks.execute(BOOKS_REQUEST_URL) ;
    }

    private class InternetConnection extends AsyncTask <String, Void, List<Book> > {

        @Override
        protected List<Book> doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null ;
            }

            List<Book> result = QueryUtils.fetchBookData(urls[0]);

            return result;
        }


        @Override
        protected void onPostExecute(List<Book> books) {
            // Find a reference to the {@link ListView} in the layout
            ListView bookListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            final BookAdapter adapter = new BookAdapter( BookActivity.this , books );

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            bookListView.setAdapter(adapter);
        }
    }







}
