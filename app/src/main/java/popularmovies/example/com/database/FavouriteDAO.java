package popularmovies.example.com.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import popularmovies.example.com.model.MoviePOJO;

@Dao
public interface FavouriteDAO {
    @Query("SELECT * FROM favourites ORDER BY popularity")
    LiveData<List<MoviePOJO>> loadAllFavourites();

    @Insert
    void addToFavourite(MoviePOJO moviePOJO);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavourite(MoviePOJO moviePOJO);

    @Delete
    void removeFromFavourite(MoviePOJO moviePOJO);

    @Query("SELECT * FROM favourites WHERE movieId = :id")
    LiveData<MoviePOJO> findMovie(String id);
}
