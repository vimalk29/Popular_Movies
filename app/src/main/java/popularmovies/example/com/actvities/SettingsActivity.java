package popularmovies.example.com.actvities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import popularmovies.example.com.R;
import popularmovies.example.com.utilities.TheMovieDB;

public class SettingsActivity extends AppCompatActivity {
    private RadioButton sortByPopularityRB;
    private RadioButton sortByRatingRB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sortByPopularityRB = findViewById(R.id.radio_btn_popularity);
        sortByRatingRB = findViewById(R.id.radio_btn_top_rated);
        if(TheMovieDB.findByPopularity) {
            sortByPopularityRB.setChecked(true);
            sortByRatingRB.setChecked(false);
        }else{
            sortByPopularityRB.setChecked(false);
            sortByRatingRB.setChecked(true);
        }
    }
    public void sortBy(View view){
        switch (view.getId()){
            case R.id.radio_btn_popularity:
                if(!TheMovieDB.findByPopularity) {
                    TheMovieDB.findByPopularity = true;
                    sortByPopularityRB.setChecked(true);
                    sortByRatingRB.setChecked(false);
                }
                break;
            case R.id.radio_btn_top_rated:
                if (TheMovieDB.findByPopularity) {
                    TheMovieDB.findByPopularity = false;
                    sortByPopularityRB.setChecked(false);
                    sortByRatingRB.setChecked(true);
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
