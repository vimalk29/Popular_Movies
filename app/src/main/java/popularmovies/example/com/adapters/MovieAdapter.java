package popularmovies.example.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import popularmovies.example.com.R;
import popularmovies.example.com.actvities.DetailActivity;
import popularmovies.example.com.model.MoviePOJO;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    final MoviePOJO[] moviePOJOS;
    final Context context;

    public MovieAdapter(MoviePOJO[] moviePOJOS, Context context) {
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
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return moviePOJOS!=null? moviePOJOS.length:0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView poster;
        //TextView title;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.posterImage);
            //title = itemView.findViewById(R.id.movie_title);
            itemView.setOnClickListener(this);
        }

        private void bindData(){
            MoviePOJO moviePOJO = moviePOJOS[getAdapterPosition()];
            //title.setText(moviePOJO.getTitle());
            Picasso.get().load(moviePOJO.getPosterPath()).into(poster);
        }

        @Override
        public void onClick(View view) {
            Intent detailActivityIntent = new Intent(context, DetailActivity.class);
            detailActivityIntent.putExtra("movieData", moviePOJOS[getAdapterPosition()]);
            context.startActivity(detailActivityIntent);
        }
    }
}
