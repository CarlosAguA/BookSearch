package com.example.android.booksearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
            " https://www.googleapis.com/books/v1/volumes?q=subject+";

    // ListView bookListView ; caso d3e ponerlo como en UDACITY GITHUB

    ImageButton searchButton;
    EditText inputSearch ;

    public static final String LOG_TAG = BookActivity.class.getName();

    public static final String RESULTS = "&maxResults=15" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        searchButton =(ImageButton) findViewById(R.id.searchButton);
        inputSearch = (EditText) findViewById(R.id.inputSearch) ;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = BOOKS_REQUEST_URL + formatSearch( inputSearch.getText().toString() )  ;
                Log.i(LOG_TAG, query ) ;

                // Start the AsyncTask to fetch the earthquake data
                googleBooks = new InternetConnection() ;
                googleBooks.execute(query) ;

            }
        });
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

    private String formatSearch (String query){

        return query.replace(" ", "+") + RESULTS ;
    }



}
