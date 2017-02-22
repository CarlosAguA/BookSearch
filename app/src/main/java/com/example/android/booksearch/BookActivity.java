package com.example.android.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;


public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /** URL to query the Google Books for books information */
    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=subject+";

    private static final int BOOK_LOADER_ID = 1 ;

    ImageButton searchButton ;
    EditText inputSearch ;
    TextView emptyState ;
    BookAdapter mAdapter ; //Global mAdapter that modifies on each bookListUpdating
    String query ;
    LoaderManager loaderManager ;
    Bundle bundle ;
    View progressBar;

    public static final String LOG_TAG = BookActivity.class.getName();

    /* Constant with the query that indicates maxResults*/
    public static final String RESULTS = "&maxResults=15" ;

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {

        progressBar.setVisibility(View.GONE);

        emptyState.setVisibility(View.VISIBLE);
        // Set empty state text to display "No books found."
        emptyState.setText(R.string.noBooks);

        Log.i(LOG_TAG, "TEST  : OnLoadFinished()");
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST  : OnCreateLoader ");
        // Create a new loader for the given URL
        return new BookLoader(this, query);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        Log.i(LOG_TAG, "TEST  : OnLoaderReset()");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Log.i(LOG_TAG, "TEST  : Method OnCreate()");

        searchButton =(ImageButton) findViewById(R.id.searchButton);
        inputSearch = (EditText) findViewById(R.id.inputSearch) ;

        emptyState = (TextView) findViewById(R.id.info_tv);
        emptyState.setText(R.string.instructions);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        // Find a reference to the {@link ListView} in the layout
        ListView  bookListView = (ListView) findViewById(R.id.list) ;
        bookListView.setEmptyView(emptyState);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        loaderManager = getLoaderManager();

        if(loaderManager.getLoader(BOOK_LOADER_ID) != null ){
            loaderManager.initLoader(BOOK_LOADER_ID, bundle, BookActivity.this);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emptyState.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                query = BOOKS_REQUEST_URL + formatSearch( inputSearch.getText().toString() )  ;
                Log.i(LOG_TAG, "TEST:" + query) ;

                // Get details on the currently active default data network
                NetworkInfo networkInfo =  checkInternetConenction();
                if (networkInfo != null && networkInfo.isConnected()) {

                   bundle = new Bundle() ;
                   bundle.putString("BOOK_API", query );

                   if(loaderManager.getLoader(BOOK_LOADER_ID) != null ){

                    loaderManager.restartLoader(BOOK_LOADER_ID, bundle ,BookActivity.this);
                    }

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOK_LOADER_ID, bundle, BookActivity.this);
                    Log.i(LOG_TAG, "TEST  : initloader() ");

                }else{
                   progressBar.setVisibility(View.GONE);
                    emptyState.setVisibility(View.VISIBLE);
                    // Update empty state with no connection error message
                    emptyState.setText(R.string.connection);
                }

            }
        });
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
    private NetworkInfo checkInternetConenction() {

        ConnectivityManager cm =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {

            Toast.makeText(getApplicationContext()," No Internet Connection" ,Toast.LENGTH_LONG).show();

        }
        return info ;
    }
}
