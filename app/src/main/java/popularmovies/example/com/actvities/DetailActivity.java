package popularmovies.example.com.actvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import popularmovies.example.com.R;
import popularmovies.example.com.adapters.ReviewAdapter;
import popularmovies.example.com.adapters.VideosAdapter;
import popularmovies.example.com.database.FavouriteDatabase;
import popularmovies.example.com.databinding.ActivityDetailBinding;
import popularmovies.example.com.model.FavouriteMovieViewModel;
import popularmovies.example.com.model.FavouriteViewModelFactory;
import popularmovies.example.com.model.MoviePOJO;
import popularmovies.example.com.model.ReviewPOJO;
import popularmovies.example.com.model.VideosPOJO;
import popularmovies.example.com.utilities.AnimationUtil;
import popularmovies.example.com.utilities.AppExecutors;
import popularmovies.example.com.utilities.MovieDBJsonUtils;
import popularmovies.example.com.utilities.TheMovieDBNetwork;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private FavouriteDatabase database;
    private MoviePOJO moviePOJO;
    private boolean isFav=false;
    private Boolean reviewsShown=false,trailerShown=false;
    private ActivityDetailBinding dBinding;
    private VideosAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;
    private String TAG = "DetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Intent intent = getIntent();
        moviePOJO = intent.getParcelableExtra("movieData");
        if(moviePOJO == null){
            Toast.makeText(this,"Oops! Movie Data not found!",Toast.LENGTH_LONG).show();
            finish();
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        AndroidNetworking.initialize(getApplicationContext());
        Picasso.get().load(moviePOJO.getPosterPath()).into(dBinding.posterImage);
        dBinding.movieTitle.setText(moviePOJO.getTitle());
        dBinding.releaseDateTv.setText(moviePOJO.getReleaseDate());
        dBinding.overviewTv.setText(moviePOJO.getOverview());
        dBinding.popularityTv.setText(String.valueOf(moviePOJO.getPopularity()));
        dBinding.averageVote.setText(String.valueOf(moviePOJO.getVoteAvg()));
        dBinding.averageVote.setText(moviePOJO.getLanguage());

        database = FavouriteDatabase.getInstance(DetailActivity.this);
        setupFavouriteButton();
        dBinding.trailerRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        dBinding.trailerRecyclerView.setHasFixedSize(true);
        videoAdapter = new VideosAdapter(DetailActivity.this, null);
        dBinding.trailerRecyclerView.setAdapter(videoAdapter);

        dBinding.reviewRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        dBinding.reviewRecyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter( null);
        dBinding.reviewRecyclerView.setAdapter(reviewAdapter);

        reviewsShown = toggleViews(dBinding.reviewRecyclerView, dBinding.reviewToggleImage, reviewsShown);
        trailerShown = toggleViews(dBinding.trailerRecyclerView, dBinding.trailerToggleImage, trailerShown);
        dBinding.favouriteImage.setOnClickListener(this);
        dBinding.reviewLabelContainer.setOnClickListener(this);
        dBinding.trailerLabelContainer.setOnClickListener(this);
        getVideosAndReviews();
    }

    private void setupFavouriteButton() {
        FavouriteViewModelFactory factory = new FavouriteViewModelFactory(database, moviePOJO.getId());
        final FavouriteMovieViewModel viewModel = new ViewModelProvider(DetailActivity.this,factory).get(FavouriteMovieViewModel.class);
        viewModel.getMovie().observe(DetailActivity.this, new Observer<MoviePOJO>() {
            @Override
            public void onChanged(MoviePOJO moviePOJO) {
                if(moviePOJO!=null){
                    dBinding.favouriteImage.setImageDrawable(getDrawable(R.drawable.ic_favorite));
                    isFav=true;
                }else{
                    dBinding.favouriteImage.setImageDrawable(getDrawable(R.drawable.ic_favorite_border));
                    isFav=false;
                }
            }
        });
    }

    private void getVideosAndReviews(){
        AndroidNetworking.get(TheMovieDBNetwork.getMoviesTrailerUrl(moviePOJO.getId()).toString())
                .setTag("trailer")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<VideosPOJO> videosList = MovieDBJsonUtils.getVideosFromJson(response);
                            if (videosList!=null){
                                videoAdapter.updateList(videosList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: Couldn't parse Response");
                        }
                        AndroidNetworking.cancel("trailer");
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });

        AndroidNetworking.get(TheMovieDBNetwork.getMovieReviewUrl(moviePOJO.getId()).toString())
                .setTag("review")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<ReviewPOJO> reviewPOJOS = MovieDBJsonUtils.getReviewsFromJson(response);
                            if (reviewPOJOS!=null){
                                reviewAdapter.updateList(reviewPOJOS);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: Couldn't parse Response");
                        }
                        AndroidNetworking.cancel("review");
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.favourite_image:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (isFav){
                            database.favouriteDAO().removeFromFavourite(moviePOJO);
                        }else{
                            database.favouriteDAO().addToFavourite(moviePOJO);
                        }
                        isFav=!isFav;
                    }
                });
                break;
            case R.id.review_label_container:
                reviewsShown = toggleViews(dBinding.reviewRecyclerView, dBinding.reviewToggleImage, reviewsShown);
                break;
            case R.id.trailer_label_container:
                trailerShown = toggleViews(dBinding.trailerRecyclerView, dBinding.trailerToggleImage, trailerShown);
                break;
        }
    }

    private Boolean toggleViews(View recycler, View toggleImage, Boolean isShown){
        float rotation = isShown?0:180;
        ViewCompat.animate(toggleImage).rotation(rotation).start();
        if (isShown)
            AnimationUtil.collapse(recycler);
        else
            AnimationUtil.expand(recycler);
        return !isShown;
    }
}
