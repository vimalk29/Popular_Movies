package popularmovies.example.com.model;

import java.io.Serializable;

/*
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
*/
public class MoviePOJO implements Serializable /*implements Parcelable*/ {
    private String id;
    private String title;
    private String releaseDate;
    private String overview;
    private Double voteAvg;
    private String posterPath;
    private String language;
    private Double popularity;

    public MoviePOJO(String id, String title, String releaseDate, String overview, Double voteAvg, String posterPath, String language, Double popularity) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAvg = voteAvg;
        this.posterPath = posterPath;
        this.language = language;
        this.popularity = popularity;
    }

    public MoviePOJO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        SparseArray<Object> spa = new SparseArray<>();
//        spa.put(1,this.title);
//        spa.put(2,this.releaseDate);
//        spa.put(3,this.overview);
//        spa.put(4,this.voteAvg);
//        spa.put(5,this.posterPath);
//        spa.put(6,this.language);
//        spa.put(7,this.popularity);
//        parcel.writeSparseArray(spa);
//    }
//    public static final Parcelable.Creator<MoviePOJO> CREATOR
//            = new Parcelable.Creator<MoviePOJO>() {
//        public MoviePOJO createFromParcel(Parcel in) {
//            return new MoviePOJO(in);
//        }
//
//        public MoviePOJO[] newArray(int size) {
//            return new MoviePOJO[size];
//        }
//    };
//
//    public MoviePOJO(Parcel in) {
//        SparseArray spa = new SparseArray<>();
//        in.readSparseArray();
//
//
//    }
}
