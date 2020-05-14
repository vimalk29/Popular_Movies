package popularmovies.example.com.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TheMovieDBNetwork {

    private final static String HTTPS = "https";
    private final static String TOP_RATED = "top_rated";
    //TODO ADD KEY HERE
    private final static String API_KEY = "YOUR_API_KEY_HERE";
    private final static String WEBURL= "api.themoviedb.org";
    private final static String TYPE = "movie";
    private final static String POPULAR = "popular";

    public static URL getMoviesUrl(boolean findByPopularity){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTPS)
                .authority(WEBURL)
                .appendPath("3")
                .appendPath(TYPE)
                .appendPath(findByPopularity? POPULAR: TOP_RATED)
                .appendQueryParameter("api_key", API_KEY);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static URL getMovieReviewUrl(String movieId){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTPS)
                .authority(WEBURL)
                .appendPath("3")
                .appendPath(TYPE)
                .appendPath(movieId)
                .appendPath("reviews")
                .appendQueryParameter("api_key",API_KEY);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static URL getMoviesTrailerUrl(String movieId){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(HTTPS)
                .authority(WEBURL)
                .appendPath("3")
                .appendPath(TYPE)
                .appendPath(movieId)
                .appendPath("videos")
                .appendQueryParameter("api_key",API_KEY);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
