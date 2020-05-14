package popularmovies.example.com.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import popularmovies.example.com.model.MoviePOJO;


@Database(entities = {MoviePOJO.class}, version = 1, exportSchema = false)
public abstract class FavouriteDatabase extends RoomDatabase {
    private static final String LOG_TAG = FavouriteDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movielist";
    private static FavouriteDatabase sInstance;

    public static FavouriteDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavouriteDatabase.class, FavouriteDatabase.DATABASE_NAME)
                        // Queries should be done in a separate thread to avoid locking the UI
                        // We will allow this ONLY TEMPORALLY to see that our DB is working
//                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavouriteDAO favouriteDAO();
}
