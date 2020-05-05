package popularmovies.example.com.actvities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import popularmovies.example.com.R;
import popularmovies.example.com.adapters.MovieAdapter;
import popularmovies.example.com.model.MoviePOJO;
import popularmovies.example.com.utilities.MovieDBJsonUtils;
import popularmovies.example.com.utilities.TheMovieDB;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieRecyclerView;
    private TextView loadingIndicatorTV;
    private ActionBar actionBar;
    private boolean lastSort=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        movieRecyclerView = findViewById(R.id.movie_recycler);
        loadingIndicatorTV = findViewById(R.id.loading_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lastSort!=TheMovieDB.findByPopularity) {
            lastSort=TheMovieDB.findByPopularity;
            actionBar.setTitle(lastSort?"Popular Movies": "Top Rated Movies");
            movieRecyclerView.setVisibility(View.GONE);
            loadingIndicatorTV.setVisibility(View.VISIBLE);
            FetchPopularMovies fetchPopularMovies = new FetchPopularMovies();
            fetchPopularMovies.execute();
        }
    }

    public class FetchPopularMovies extends AsyncTask<Void, Void, MoviePOJO[]> {

        @Override
        protected MoviePOJO[] doInBackground(Void... voids) {
            URL popularMovierequestUrl = TheMovieDB.getPopularMoviesUrl();
            if (isOnline()) {
                try {
                    String jsonMovieResponse = TheMovieDB
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
        protected void onPostExecute(MoviePOJO[] moviePOJOS) {
            super.onPostExecute(moviePOJOS);
            if(moviePOJOS==null || moviePOJOS.length==0){
                loadingIndicatorTV.setText(getString(R.string.error_loading_text));
            }else {
                loadingIndicatorTV.setVisibility(View.GONE);
                movieRecyclerView.setVisibility(View.VISIBLE);
                MovieAdapter movieAdapter = new MovieAdapter(moviePOJOS, MainActivity.this);

                movieRecyclerView.setHasFixedSize(true);
                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                movieRecyclerView.setLayoutManager(layoutManager);
                movieRecyclerView.setAdapter(movieAdapter);
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
}
