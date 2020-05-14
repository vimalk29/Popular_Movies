package popularmovies.example.com.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import popularmovies.example.com.model.MoviePOJO;
import popularmovies.example.com.model.ReviewPOJO;
import popularmovies.example.com.model.VideosPOJO;

public class MovieDBJsonUtils {

    private final static String RESULTS = "results";
    private final static String ID = "id";
    private final static String TITLE = "title";
    private final static String RELEASE_DATE = "release_date";
    private final static String OVERVIEW = "overview";
    private final static String VOTE_AVG = "vote_average";
    private final static String POSTER_PATH = "poster_path";
    private final static String LANGUAGE = "original_language";
    private final static String POPULARITY = "popularity";
    private final static String POSTER_URL = "https://image.tmdb.org/t/p/w500";

    public static List<MoviePOJO> getMovieDataFromJson(String json) throws JSONException {
        JSONArray resultArray = new JSONObject(json).getJSONArray(RESULTS);
        List<MoviePOJO> parsedMovieData = new ArrayList<>();
        for (int i=0;i<resultArray.length();i++){
            JSONObject movie = resultArray.getJSONObject(i);
            String id = movie.getString(ID);
            String title = movie.getString(TITLE);
            String releaseDate= movie.getString(RELEASE_DATE);
            String overview=movie.getString(OVERVIEW);
            Double voteAvg=movie.getDouble(VOTE_AVG);
            String posterPath= POSTER_URL + movie.getString(POSTER_PATH);
            String language=movie.getString(LANGUAGE);
            Double popularity = movie.getDouble(POPULARITY);
            parsedMovieData.add(new MoviePOJO(id,title,releaseDate,overview,voteAvg,posterPath,language,popularity));
        }
        
        return parsedMovieData;
    }
    public static List<VideosPOJO> getVideosFromJson(JSONObject json) throws JSONException {
        JSONArray resultArray = json.getJSONArray(RESULTS);
        List<VideosPOJO> parsedVideos = new ArrayList<>();

        for (int i =0; i<resultArray.length();i++){
            JSONObject videos = resultArray.getJSONObject(i);
            String id = videos.getString("id");
            String key = videos.getString("key");
            String name = videos.getString("name");
            String type = videos.getString("type");
            String size = videos.getString("size");
            String site = videos.getString("site");
            parsedVideos.add(new VideosPOJO(id,key,name,type,size,site));
        }
        return parsedVideos;
    }
    public static List<ReviewPOJO> getReviewsFromJson(JSONObject json) throws JSONException {
        JSONArray resultArray = json.getJSONArray(RESULTS);
        List<ReviewPOJO> parsedVideos = new ArrayList<>();

        for (int i =0; i<resultArray.length();i++){
            JSONObject videos = resultArray.getJSONObject(i);
            String id = videos.getString("id");
            String author = videos.getString("author");
            String content = videos.getString("content");
            String url = videos.getString("url");
            parsedVideos.add(new ReviewPOJO(id,author,content,url));
        }
        return parsedVideos;
    }
}
