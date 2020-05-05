package popularmovies.example.com.actvities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import popularmovies.example.com.R;
import popularmovies.example.com.model.MoviePOJO;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Movie Detail");
        }

        Intent intent = getIntent();
        MoviePOJO moviePOJO = (MoviePOJO) intent.getSerializableExtra("movieData");
        if(moviePOJO == null){
            finish();
        }
        TextView movietitleTV = findViewById(R.id.movie_title);
        TextView releaseDateTV = findViewById(R.id.release_date_tv);
        TextView overviewTV = findViewById(R.id.overview_tv);
        ImageView posterIV = findViewById(R.id.posterImage);
        TextView popularityTV = findViewById(R.id.popularity_tv);
        TextView voteAvgTV = findViewById(R.id.average_vote);
        TextView languageTV = findViewById(R.id.language);

        Picasso.get().load(moviePOJO.getPosterPath()).into(posterIV);
        movietitleTV.setText(moviePOJO.getTitle());
        releaseDateTV.setText(moviePOJO.getReleaseDate());
        overviewTV.setText(moviePOJO.getOverview());
        popularityTV.setText(String.valueOf(moviePOJO.getPopularity()));
        voteAvgTV.setText(String.valueOf(moviePOJO.getVoteAvg()));
        languageTV.setText(moviePOJO.getLanguage());
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
