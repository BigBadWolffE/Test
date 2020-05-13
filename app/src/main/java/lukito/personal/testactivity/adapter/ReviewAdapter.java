package lukito.personal.testactivity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lukito.personal.testactivity.R;
import lukito.personal.testactivity.model.ReviewMovies;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<ReviewMovies> reviewModels = new ArrayList<>();
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvCommentary;
        ImageView imgProductItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCommentary= itemView.findViewById(R.id.tvComment);
        }
    }
    public ReviewAdapter (Context context,
                             List<ReviewMovies> reviewModels,
                             Activity activity){
        this.context = context;
        this.reviewModels.clear();
        this.reviewModels.addAll(reviewModels);
        this.activity = activity;
    }

    public void replaceItemFiltered(List<ReviewMovies> reviewModels){
        this.reviewModels.clear();
        this.reviewModels.addAll(reviewModels);
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ReviewAdapter.ViewHolder){
            final ReviewMovies model = reviewModels.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;

            if(model != null){
                viewHolder.tvName.setText( model.getAuthor());
                viewHolder.tvCommentary.setText("Review: " +model.getContent());
            }
        }else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    private class LoadingViewHolder extends ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    @Override
    public int getItemViewType(int position) {
        return reviewModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return reviewModels != null ? reviewModels.size() : 0;
    }
}
