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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lukito.personal.testactivity.R;
import lukito.personal.testactivity.model.MovieListModel;
import lukito.personal.testactivity.model.TvListModel;
import lukito.personal.testactivity.network.API;

public class TvDashBoardAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<TvListModel> tvListModels = new ArrayList<>();
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMovieName, tvPopularity, tvOverview, tvReleaseDate;
        ImageView imgProductItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvTelevisionName);
            tvPopularity = itemView.findViewById(R.id.tvPopularityTelevisionCount);
            tvOverview= itemView.findViewById(R.id.tvOverviewTelevision);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDateTelevision);
            imgProductItem = itemView.findViewById(R.id.imgProductItem);
        }

    }
    public TvDashBoardAdapter (Context context, List<TvListModel> tvListModels,
                               Activity activity){
        this.context = context;
        this.tvListModels.clear();
        this.tvListModels.addAll(tvListModels);
        this.activity = activity;
    }
    public void replaceItemFiltered(List<TvListModel> tvListModels){
        this.tvListModels.clear();
        this.tvListModels.addAll(tvListModels);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tv_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false);
            return new LoadingViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof TvDashBoardAdapter.ViewHolder){
            final TvListModel model = tvListModels.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;

            if(model != null){
                viewHolder.tvMovieName.setText("Title: " + model.getName());
                viewHolder.tvOverview.setText("Overview: " +model.getOverview());
                viewHolder.tvPopularity.setText("Popularity: "+model.getPopularity());
                viewHolder.tvReleaseDate.setText("Release Date: "+model.getFirst_air_date());

                String link = model.getPoster_path();

                if(model.getPoster_path()!= null){
                    String removeLink = model.getPoster_path().replaceAll("\\/", "" );

                    Picasso.with(activity).load(API.HostImage+"t/p/w200/"+removeLink).
                            into(viewHolder.imgProductItem);
                }else{
                    viewHolder.imgProductItem.setVisibility(View.INVISIBLE);
                }



//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent detailMovies = new Intent(activity, DetailMoviesActivity.class);
//                        detailMovies.putExtra(MovieListModel.class.getSimpleName(),model);
//                        ((Activity)context).startActivityForResult(detailMovies,5);
//
//                    }
//                });


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
        return tvListModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return tvListModels != null ? tvListModels.size() : 0;
    }
}
