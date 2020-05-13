package lukito.personal.testactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lukito.personal.testactivity.adapter.ReviewAdapter;
import lukito.personal.testactivity.model.MovieListModel;
import lukito.personal.testactivity.model.ReviewMovies;
import lukito.personal.testactivity.network.API;
import lukito.personal.testactivity.network.MovieListRequest;

public class DetailMoviesActivity extends AppCompatActivity {
    public static ImageView imgPoster;
    public static TextView tvTitle,tvPopularity,tvOverView,tvReleaseDate,tvVoteAverage,tvVoteCount;
    public static RecyclerView rcComment;
    public static CardView commentViewsBtn;

    public static MovieListModel movieListModel;
    public static List<ReviewMovies> movieListModels;
    public static ReviewAdapter dashboardAdapter;
    public static int idMovies;
    static DividerItemDecoration itemDecorator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        imgPoster = findViewById(R.id.imgProductItemDetail);
        tvTitle = findViewById(R.id.tvMovieNameDetail);
        tvPopularity = findViewById(R.id.tvPopularityCountDetail);
        tvOverView = findViewById(R.id.tvOverviewDetail);
        tvReleaseDate = findViewById(R.id.tvReleaseDateDetail);
        tvVoteAverage = findViewById(R.id.tvVoteAverageDetail);
        tvVoteCount = findViewById(R.id.tvVoteCountDetail);
        rcComment= findViewById(R.id.recyclerView3);
        commentViewsBtn = findViewById(R.id.buttonViewComments);


        itemDecorator = new DividerItemDecoration(rcComment.getContext(),
                DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(rcComment.getContext(),
                R.drawable.divider_line_item));
        commentViewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcComment.setVisibility(View.VISIBLE);
            }
        });



        movieListModel = getIntent().getParcelableExtra(MovieListModel.class.getSimpleName());
        idMovies = movieListModel.getId();

        initAdapter(this);
        requestFilter(this);
        initScrollListener(this);

        OkHttpClient client = new OkHttpClient();
        Request requestHttp = new Request.Builder().
                url(API.Host+"3/movie/"+movieListModel.getId()+"?api_key="+API.APIKey+"&language=en-US"+"&append_to_response=videos,images").
                build();

        Log.e("URL Detail",API.Host+"3/movie/"+movieListModel.getId()+"?api_key="+API.APIKey+"&language=en-US"+"&append_to_response=videos,images");

        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                DetailMoviesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Error",e.toString());

                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                final String responseData = response.body().string();
                Log.e("RESPONSE BODY", responseData);
                DetailMoviesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            tvTitle.setText("Title: "+json.getString("title"));
                            tvOverView.setText("Overview: "+json.getString("overview"));
                            tvPopularity.setText("Popularity: "+json.getString("popularity"));
                            tvReleaseDate.setText("Release Date: "+json.getString("release_date"));
                            tvVoteAverage.setText("Average Rating: "+json.getString("vote_average"));
                            tvVoteCount.setText("Votes ++: "+json.getString("vote_count"));

                            String removeLink = json.getString("poster_path").replaceAll("\\/", "" );
                            Picasso.with(DetailMoviesActivity.this).load(API.HostImage+"t/p/w200/"+removeLink).into(imgPoster);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }
    private void initAdapter(Activity activity) {

        movieListModels = new ArrayList<>();
        dashboardAdapter = new ReviewAdapter(this, movieListModels, this);

        rcComment.setLayoutManager(new LinearLayoutManager(activity));
        rcComment.addItemDecoration(itemDecorator);
        rcComment.setAdapter(dashboardAdapter);
    }
    public static void updateDataMovies(List<ReviewMovies>movieListModelsResponse, int page){
        if(movieListModels.size() != 0){
            movieListModels.remove(movieListModels.size() - 1);
            int scrollPosition = movieListModels.size();
            dashboardAdapter.notifyItemRemoved(scrollPosition);
        }

        if (page == 1)
            movieListModels.clear();
        movieListModels.addAll(movieListModelsResponse);
        dashboardAdapter.replaceItemFiltered(movieListModels);
//        inventoryAdapter.notifyDataSetChanged();
        isLoading = false;

        if (page == 1)
            if (movieListModels.size() > 0)
                rcComment.scrollToPosition(0);
    }
    static boolean isLoading = false;

    public static void initScrollListener(final Activity activity) {
        rcComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == movieListModels.size() - 1) {
                        //bottom of list!
                        loadMoreProduct(activity);
                        isLoading = true;
                    }
                }
            }
        });
    }

    public static void loadMoreProduct(Activity activity) {
        movieListModels.add(null);
        dashboardAdapter.notifyItemInserted(movieListModels.size() - 1);

        MovieListRequest.page = MovieListRequest.page + 1;
        MovieListRequest.getMovieReview(activity);
    }

    private void requestFilter(Activity activity){

        MovieListRequest.page = 1;
        movieListModels.clear();
        MovieListRequest.getMovieReview(activity);



    }

}
