package popularmovies.example.com.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import popularmovies.example.com.database.FavouriteDatabase;

public class MovieViewModel extends AndroidViewModel {
    private LiveData<List<MoviePOJO>> movies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        FavouriteDatabase database = FavouriteDatabase.getInstance(this.getApplication());
        movies = database.favouriteDAO().loadAllFavourites();
    }

    public LiveData<List<MoviePOJO>> getMovies() {
        return movies;
    }
}
