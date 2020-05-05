package popularmovies.example.com.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import popularmovies.example.com.model.MoviePOJO;

public class MovieDBJsonUtils {

    final static String RESULTS = "results";
    final static String ID = "id";
    final static String TITLE = "title";
    final static String RELEASE_DATE = "release_date";
    final static String OVERVIEW = "overview";
    final static String VOTE_AVG = "vote_average";
    final static String POSTER_PATH = "poster_path";
    final static String LANGUAGE = "original_language";
    final static String POPULARITY = "popularity";
    final static String POSTER_URL = "https://image.tmdb.org/t/p/w500";

    public static MoviePOJO[] getMovieDataFromJson(String json) throws JSONException {
        JSONArray resultArray = new JSONObject(json).getJSONArray(RESULTS);
        MoviePOJO[] parsedMovieData = new MoviePOJO[resultArray.length()];
        for (int i=0;i<resultArray.length();i++){
            JSONObject movie = resultArray.getJSONObject(i);

            String id = movie.getString(ID);;
            String title = movie.getString(TITLE);
            String releaseDate= movie.getString(RELEASE_DATE);
            String overview=movie.getString(OVERVIEW);
            Double voteAvg=movie.getDouble(VOTE_AVG);
            String posterPath= POSTER_URL + movie.getString(POSTER_PATH);
            String language=movie.getString(LANGUAGE);
            Double popularity = movie.getDouble(POPULARITY);
            parsedMovieData[i] = new MoviePOJO(id,title,releaseDate,overview,voteAvg,posterPath,language,popularity);
        }
        return parsedMovieData;
    }
}
