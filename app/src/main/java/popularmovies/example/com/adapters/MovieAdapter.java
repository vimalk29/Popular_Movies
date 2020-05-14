package popularmovies.example.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.List;

import popularmovies.example.com.R;
import popularmovies.example.com.actvities.DetailActivity;
import popularmovies.example.com.database.FavouriteDatabase;
import popularmovies.example.com.model.MoviePOJO;
import popularmovies.example.com.utilities.AppExecutors;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final List<MoviePOJO> moviePOJOS;
    private final Context context;

    public MovieAdapter(List<MoviePOJO> moviePOJOS, Context context) {
        this.moviePOJOS = moviePOJOS;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return moviePOJOS!=null? moviePOJOS.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView poster;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.posterImage);
            itemView.setOnClickListener(this);
            //favouriteIcon.setOnClickListener(this);
        }

        private void bindData(final int position){
            MoviePOJO moviePOJO = moviePOJOS.get(position);
            Picasso.get().load(moviePOJO.getPosterPath()).into(poster);
//            AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                @Override
//                public void run() {
//                    if (fDB.favouriteDAO().findMovie(moviePOJOS.get(position).getId()) != null){
//                        favouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite));
//                    }
//                }
//            });
        }
        final String TAG = "MovieAdapter";
        @Override
        public void onClick(final View view) {
            final int position = getAdapterPosition();
//            if(view.getId()== R.id.favourite_image){
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (fDB.favouriteDAO().findMovie(moviePOJOS.get(position).getId())!=null){
//                            //Log.d(TAG, "run: removing Fav");
//                            favouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_border));
//                            fDB.favouriteDAO().removeFromFavourite(moviePOJOS.get(position));
//                        }else {
//                            //Log.d(TAG, "run: adding Fav");
//                            favouriteIcon.setImageDrawable(context.getDrawable(R.drawable.ic_favorite));
//                            fDB.favouriteDAO().addToFavourite(moviePOJOS.get(position));
//                        }
//                    }
//                });
//            }else {
                Intent detailActivityIntent = new Intent(context, DetailActivity.class);
                detailActivityIntent.putExtra("movieData", moviePOJOS.get(position));
                context.startActivity(detailActivityIntent);
//            }
        }
    }
}
