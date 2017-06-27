package com.larry.osakwe.book_listing;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private TextView mEmptyStateTextView;
    private BookAdapter mAdapter;
    private static final int BOOK_LOADER_ID = 1;

    private static String url = "https://www.googleapis.com/books/v1/volumes?q=" + getIntent().getExtras().getString("searchEntry");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //String searchTerm = getIntent().getExtras().getString("searchEntry");
        //String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm;

        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setEmptyView(mEmptyStateTextView);
        bookListView.setAdapter(mAdapter);

        if (isNetworkOnline()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            mEmptyStateTextView.setText("No internet connection.");
            View loadSpinner = findViewById(R.id.loading_spinner);
            loadSpinner.setVisibility(View.GONE);
        }


    }

    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getActiveNetworkInfo();
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        //String searchTerm = getIntent().getExtras().getString("searchEntry");
        //String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm;
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText("No books with that name");
        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }

        ProgressBar loadSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loadSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }


    public static class BookLoader extends AsyncTaskLoader<List<Book>> {

        private String mUrl;

        public BookLoader(Context context, String url) {
            super(context);
            this.mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }


        @Override
        public List<Book> loadInBackground() {
            if (mUrl == null) {
                return null;
            }




            List<Book> books = QueryUtils.extractBooks(url);
            return books;
        }
    }


}
