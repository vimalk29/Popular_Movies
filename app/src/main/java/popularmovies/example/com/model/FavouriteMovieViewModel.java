package popularmovies.example.com.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import popularmovies.example.com.database.FavouriteDatabase;

public class FavouriteMovieViewModel extends ViewModel {
    private LiveData<MoviePOJO> movie;

    public FavouriteMovieViewModel(FavouriteDatabase database, String movieId) {
        movie = database.favouriteDAO().findMovie(movieId);
    }

    public LiveData<MoviePOJO> getMovie() {
        return movie;
    }
}
