package lukito.personal.testactivity.adapter;

//import androidx.appcompat.app.AlertController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lukito.personal.testactivity.DetailMoviesActivity;
import lukito.personal.testactivity.R;
import lukito.personal.testactivity.model.MovieListModel;
import lukito.personal.testactivity.network.API;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MovieListModel> movieListModels = new ArrayList<>();
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMovieName, tvPopularity, tvOverview, tvReleaseDate;
        ImageView imgProductItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvPopularity = itemView.findViewById(R.id.tvPopularityCount);
            tvOverview= itemView.findViewById(R.id.tvOverview);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            imgProductItem = itemView.findViewById(R.id.imgProductItem);
        }
    }

    public DashboardAdapter (Context context,
                             List<MovieListModel> movieListModels,
                             Activity activity){
        this.context = context;
        this.movieListModels.clear();
        this.movieListModels.addAll(movieListModels);
        this.activity = activity;
    }

    public void replaceItemFiltered(List<MovieListModel> movieListModels){
        this.movieListModels.clear();
        this.movieListModels.addAll(movieListModels);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movies_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false);
            return new LoadingViewHolder(view);
        }    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DashboardAdapter.ViewHolder){
            final MovieListModel model = movieListModels.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;

            if(model != null){
                viewHolder.tvMovieName.setText("Title: " + model.getTitle());
                viewHolder.tvOverview.setText("Overview: " +model.getOverview());
                viewHolder.tvPopularity.setText("Popularity: "+model.getPopularity());
                viewHolder.tvReleaseDate.setText("Release Date: "+model.getReleasedate());

                String link = model.getPosterpath();

                if(model.getPosterpath()!= null){
                    String removeLink = model.getPosterpath().replaceAll("\\/", "" );

                    Picasso.with(activity).load(API.HostImage+"t/p/w200/"+removeLink).
                            into(viewHolder.imgProductItem);
                }else{
                    viewHolder.imgProductItem.setVisibility(View.INVISIBLE);
                }



                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent detailMovies = new Intent(activity, DetailMoviesActivity.class);
                        detailMovies.putExtra(MovieListModel.class.getSimpleName(),model);
                        ((Activity)context).startActivityForResult(detailMovies,5);

                    }
                });


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
        return movieListModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return movieListModels != null ? movieListModels.size() : 0;
    }
}
