package popularmovies.example.com.model;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import popularmovies.example.com.database.FavouriteDatabase;

public class FavouriteViewModelFactory  extends ViewModelProvider.NewInstanceFactory {
    private final FavouriteDatabase mDb;
    private final String mMovieId;

    public FavouriteViewModelFactory(FavouriteDatabase database, String movieId) {
        mDb = database;
        mMovieId = movieId;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FavouriteMovieViewModel(mDb, mMovieId);
    }
}