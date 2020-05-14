package popularmovies.example.com.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import popularmovies.example.com.R;
import popularmovies.example.com.databinding.ReviewItemBinding;
import popularmovies.example.com.model.ReviewPOJO;
import popularmovies.example.com.utilities.AnimationUtil;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewPOJO> reviews;
    private ReviewItemBinding rBindingFlag;

    public ReviewAdapter(List<ReviewPOJO> reviews) {
        this.reviews = reviews;
        rBindingFlag=null;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewItemBinding reviewItemBinding = DataBindingUtil.inflate(inflater, R.layout.review_item,parent,false);
        return new ReviewAdapter.ViewHolder(reviewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews!=null?reviews.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        Boolean contentShown;
        ReviewItemBinding rBinding;
        ViewHolder(@NonNull ReviewItemBinding reviewItemBinding) {
            super(reviewItemBinding.getRoot());
            rBinding = reviewItemBinding;
            contentShown=false;
        }
        void bindData(ReviewPOJO reviewPOJO){
            rBinding.authorName.setText(reviewPOJO.getAuthor());
            rBinding.reviewContent.setText(reviewPOJO.getContent());
            rBinding.reviewToggleContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    float rotation = contentShown?180:0;
                    ViewCompat.animate(rBinding.toggleContent).rotation(rotation).start();
                    if (contentShown){
                        AnimationUtil.collapse(rBinding.reviewContent);
                        rBindingFlag=null;
                    }else {
                        AnimationUtil.expand(rBinding.reviewContent);
                        if (rBindingFlag!=null){
                            ViewCompat.animate(rBindingFlag.toggleContent).rotation(180).start();
                            AnimationUtil.collapse(rBindingFlag.reviewContent);
                        }
                        rBindingFlag=rBinding;
                    }
                    contentShown=!contentShown;
                }
            });
        }
    }
    public void updateList(List<ReviewPOJO> reviewPOJOS){
        this.reviews = reviewPOJOS;
        this.notifyDataSetChanged();
    }
}
