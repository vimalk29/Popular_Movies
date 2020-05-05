package popularmovies.example.com.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TheMovieDB {

    public static Boolean findByPopularity = true;

    final static String TOP_RATED = "top_rated";
    final static String API_KEY = "YOUR_API_KEY_HERE";
    final static String WEBURL= "api.themoviedb.org";
    final static String TYPE = "movie";
    final static String POPULAR = "popular";

    public static URL getPopularMoviesUrl(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
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
