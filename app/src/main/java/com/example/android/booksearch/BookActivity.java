package com.example.android.booksearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity {

    InternetConnection googleBooks;
    /** URL to query the Google Books for books information */
    private static final String BOOKS_REQUEST_URL =
            " https://www.googleapis.com/books/v1/volumes?q=subject+";

    private static final int BOOK_LOADER_ID = 1 ;

    ImageButton searchButton;
    EditText inputSearch ;
    TextView userInfo ;

    BookAdapter mAdapter ; //Global mAdapter that modifies on each bookListUpdating

    public static final String LOG_TAG = BookActivity.class.getName();

    /* Constant with the query that indicates maxResults*/
    public static final String RESULTS = "&maxResults=15" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        searchButton =(ImageButton) findViewById(R.id.searchButton);
        inputSearch = (EditText) findViewById(R.id.inputSearch) ;
        userInfo = (TextView) findViewById(R.id.info_tv);

        // Find a reference to the {@link ListView} in the layout
        ListView  bookListView = (ListView) findViewById(R.id.list) ;

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = BOOKS_REQUEST_URL + formatSearch( inputSearch.getText().toString() )  ;
                Log.i(LOG_TAG, query ) ;
                userInfo.setVisibility(View.GONE);

                // Start the AsyncTask to fetch the earthquake data
                googleBooks = new InternetConnection() ;
                googleBooks.execute(query) ;

            }
        });
    }

    /*
    InternetConnection is an extended asyncTask to perform an HTTPRequest to Google Books API server in background
    @String It passes a string with the established url for performing the query
    @List<Book>  doInBackground method will return a List<Book> with the parsed data from server
     */
    private class InternetConnection extends AsyncTask <String, Void, List<Book> > {

        @Override
        protected void onPreExecute() {

            checkInternetConenction();
        }

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

            //If the result from doInBackground() (List<Book> object) is null, return and do nothing.
            if (books == null) {
                return;
            }

            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Books}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (books != null && !books.isEmpty()) {
                mAdapter.addAll(books);
            }
        }
    }

    /*
    This method formats the search query of the editText. In addtion it  appends maxAmount of results
     */
    private String formatSearch (String query){

        return query.replace(" ", "+") + RESULTS ;
    }

    /*
    This method uses a ConnectivityManager instance for checking the state of the Internet connection
    It will show a toast in case no Internet connection is found
     */
    private void checkInternetConenction() {

        ConnectivityManager cm =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {

            Toast.makeText(getApplicationContext()," No Internet Connection" ,Toast.LENGTH_LONG).show();

        }
    }
}
