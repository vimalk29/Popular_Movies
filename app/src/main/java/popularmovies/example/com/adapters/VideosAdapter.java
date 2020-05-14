package popularmovies.example.com.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import popularmovies.example.com.R;
import popularmovies.example.com.databinding.VideoItemBinding;
import popularmovies.example.com.model.VideosPOJO;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private List<VideosPOJO> videoList;
    private Context context;

    public VideosAdapter( Context context, List<VideosPOJO> videoList) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VideoItemBinding videoItemBinding = DataBindingUtil.inflate(inflater, R.layout.video_item, parent, false);
        return new VideosAdapter.ViewHolder(videoItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList!=null? videoList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        VideoItemBinding videoItemBinding;
        ViewHolder(@NonNull VideoItemBinding itemView) {
            super(itemView.getRoot());
            videoItemBinding = itemView;
        }
        void bindData(final VideosPOJO videosPOJO){
            videoItemBinding.movieName.setText(videosPOJO.getName());
            String size = videosPOJO.getSize().concat("p");
            videoItemBinding.movieSize.setText(size);
            videoItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Opening Youtube",Toast.LENGTH_SHORT).show();
                    String id = videosPOJO.getKey();

                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + id));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        Log.d("VideoAdapter", "onClick: "+ex.getMessage());
                        context.startActivity(webIntent);
                    }
                }
            });
        }
    }
    public void updateList(List<VideosPOJO> videosPOJOList){
        this.videoList = videosPOJOList;
        this.notifyDataSetChanged();
    }
}
