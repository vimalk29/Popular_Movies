package popularmovies.example.com.actvities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.List;

import popularmovies.example.com.R;
import popularmovies.example.com.adapters.MovieAdapter;
import popularmovies.example.com.database.FavouriteDatabase;
import popularmovies.example.com.model.MoviePOJO;
import popularmovies.example.com.utilities.MovieDBJsonUtils;
import popularmovies.example.com.utilities.TheMovieDBNetwork;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView movieRecyclerView;
    private TextView loadingIndicatorTV;
    private ActionBar actionBar;
    private FetchPopularMovies fetchPopularMovies;
    private FavouriteDatabase fDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();

        fDB = FavouriteDatabase.getInstance(getApplicationContext());
        movieRecyclerView = findViewById(R.id.movie_recycler);
        loadingIndicatorTV = findViewById(R.id.loading_text);

        movieRecyclerView.setVisibility(View.GONE);
        loadingIndicatorTV.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(this);
        loadMoviesFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    final String TAG = "MainActivity";
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_sort_key))){
            loadMoviesFromPreferences(sharedPreferences);
        }
    }

    private void loadMoviesFromPreferences(SharedPreferences sharedPreferences) {
        fetchPopularMovies = new FetchPopularMovies();
        String type = sharedPreferences.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_popularity));
        if(type.equals(getString(R.string.pref_sort_popularity))){
            Log.d(TAG, "loadMoviesFromPreferences: POPULARITY "+type);
            actionBar.setTitle("Popular Movies");

            fetchPopularMovies.execute(true);
        }else if (type.equals(getString(R.string.pref_sort_toprated))){
            Log.d(TAG, "loadMoviesFromPreferences: TOP_RATED "+type);
            actionBar.setTitle("Top Rated Movies");
            fetchPopularMovies.execute(false);
        }else{
            Log.d(TAG, "loadMoviesFromPreferences: FAVOURITE "+type);
            actionBar.setTitle("Favourite Movies");
            LiveData<List<MoviePOJO>> movies = fDB.favouriteDAO().loadAllFavourites();
            movies.observe(MainActivity.this, new Observer<List<MoviePOJO>>() {
                @Override
                public void onChanged(List<MoviePOJO> moviePOJOS) {
                    if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_popularity)).equals(getString(R.string.pref_sort_favourite))) {
                        movieRecyclerView.setVisibility(View.VISIBLE);
                        loadingIndicatorTV.setVisibility(View.GONE);
                        populateUI(moviePOJOS);
                    }
                }
            });
        }
    }

    void populateUI(List<MoviePOJO> moviePOJOS){
        loadingIndicatorTV.setVisibility(View.GONE);
        movieRecyclerView.setVisibility(View.VISIBLE);
        MovieAdapter movieAdapter = new MovieAdapter(moviePOJOS, MainActivity.this);
        movieRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns());
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(movieAdapter);
    }

    public class FetchPopularMovies extends AsyncTask<Boolean, Void, List<MoviePOJO>> {

        @Override
        protected List<MoviePOJO> doInBackground(Boolean... findPopular) {
            if (findPopular[0]==null)
                return null;
            URL popularMovierequestUrl = TheMovieDBNetwork.getMoviesUrl(findPopular[0]);
            if (isOnline()) {
                try {
                    String jsonMovieResponse = TheMovieDBNetwork
                            .getResponseFromHttpUrl(popularMovierequestUrl);
                    Log.d("MainActivity", "doInBackground: "+ jsonMovieResponse);
                    return MovieDBJsonUtils
                            .getMovieDataFromJson(jsonMovieResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<MoviePOJO> moviePOJOS) {
            super.onPostExecute(moviePOJOS);
            if(moviePOJOS==null || moviePOJOS.size()==0){
                loadingIndicatorTV.setText(getString(R.string.error_loading_text));
            }else {
                populateUI(moviePOJOS);
            }
        }
        boolean isOnline() {
            try {
                int timeoutMs = 1500;
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                sock.connect(sockaddr, timeoutMs);
                sock.close();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.settings){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
