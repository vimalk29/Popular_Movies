package popularmovies.example.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class MoviePOJO implements Parcelable {
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

    protected MoviePOJO(Parcel in) {
        id = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        voteAvg = in.readByte() == 0x00 ? null : in.readDouble();
        posterPath = in.readString();
        language = in.readString();
        popularity = in.readByte() == 0x00 ? null : in.readDouble();
        byte isFavouriteVal = in.readByte();
        isFavourite = isFavouriteVal == 0x02 ? null : isFavouriteVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        if (voteAvg == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(voteAvg);
        }
        dest.writeString(posterPath);
        dest.writeString(language);
        if (popularity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(popularity);
        }
        if (isFavourite == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isFavourite ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MoviePOJO> CREATOR = new Parcelable.Creator<MoviePOJO>() {
        @Override
        public MoviePOJO createFromParcel(Parcel in) {
            return new MoviePOJO(in);
        }

        @Override
        public MoviePOJO[] newArray(int size) {
            return new MoviePOJO[size];
        }
    };
}
