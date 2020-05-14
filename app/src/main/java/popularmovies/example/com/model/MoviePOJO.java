package popularmovies.example.com.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
*/
@Entity(tableName = "favourites")
public class MoviePOJO implements Serializable /*implements Parcelable*/ {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    private String id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "avgVote")
    private Double voteAvg;
    @ColumnInfo(name = "posterPath")
    private String posterPath;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "popularity")
    private Double popularity;

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    private Boolean isFavourite;

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
    @Ignore
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

}
