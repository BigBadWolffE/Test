package lukito.personal.testactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lukito.personal.testactivity.adapter.DashboardAdapter;
import lukito.personal.testactivity.helper.CloseSoftKeyboard;
import lukito.personal.testactivity.helper.SpinnerSortBy;
import lukito.personal.testactivity.model.GenreModel;
import lukito.personal.testactivity.model.MovieListModel;
import lukito.personal.testactivity.network.MovieListRequest;

public class MainActivity extends AppCompatActivity {
    private static RecyclerView rcMovies,rcAnimated;
    public static DashboardAdapter dashboardAdapter;
    public static EditText etSearch;
    public static List<MovieListModel> movieListModels;
    public static Spinner spSortBy,spChangeTab;

    static DividerItemDecoration itemDecorator;
    public static ProgressDialog progressDialog;

    // Genre
    public static String idGenre, paramsGenre;
    public static List<GenreModel> genreModelList;
    public static AutoCompleteTextView actGenre;
    public static ConstraintLayout clGenre;
    public static TextView tvLabelGenre;
    public static ImageView imgGenreRemove;


    static Drawable close;
    static Drawable search;


    public MainActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        rcMovies = findViewById(R.id.rcMovie);
        rcAnimated = findViewById(R.id.rcAnimated);
        etSearch = findViewById(R.id.et_search);
        spSortBy = findViewById(R.id.spinner_sortBy);
        spChangeTab =findViewById(R.id.spinnerType);

        itemDecorator = new DividerItemDecoration(rcMovies.getContext(),
                DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(rcMovies.getContext(),
                R.drawable.divider_line_item));
        SpinnerSortBy.SpinnerSetup(this);
        SpinnerSortBy.SpinnerTab(this);

        spChangeTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spChangeTab.getSelectedItem().toString().equals("Tv Shows")){
                    Intent tvShows = new Intent(MainActivity.this, TvMainActivity.class);
                    ((Activity)MainActivity.this).startActivityForResult(tvShows,4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spSortBy.getSelectedItem().toString().equals("Popularity Descending")){
                    MovieListRequest.sortByString = "popularity.desc";
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);
                }else if(spSortBy.getSelectedItem().toString().equals("Popularity Ascending")){
                    MovieListRequest.sortByString = "popularity.asc";
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);
                }else if(spSortBy.getSelectedItem().toString().equals("Rating Descending")){
                    MovieListRequest.sortByString = "vote_average.desc";
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);
                }else if(spSortBy.getSelectedItem().toString().equals("Rating Ascending")){
                    MovieListRequest.sortByString = "vote_average.asc";
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);
                }else if(spSortBy.getSelectedItem().toString().equals("ReleaseDate Ascending")){
                    MovieListRequest.sortByString = "release_date.asc";
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);
                }else if(spSortBy.getSelectedItem().toString().equals("ReleaseDate Descending")){
                    MovieListRequest.sortByString = "release_date.desc";
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);
                }
                else{
                    initAdapter(MainActivity.this);
                    requestFilter(MainActivity.this);
                    initScrollListener(MainActivity.this);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        movieListModels = new ArrayList<>();
        dashboardAdapter = new DashboardAdapter(this, movieListModels, this);


        // Province
        actGenre = findViewById(R.id.act_sort);
        clGenre = findViewById(R.id.cl_sort);
        tvLabelGenre = findViewById(R.id.label_selected_sort);
        imgGenreRemove = findViewById(R.id.img_label_sort_remove);
        MovieListRequest.getGenreList(MainActivity.this);

        searchMulti(this,this);

        initAdapter(this);
        requestFilter(this);
        initScrollListener(this);

//        initAdapterAnimation(this);
//        requestFilterAnimation(this);
//        initScrollListenerAnimation(this);


    }

    public static void setGenreAdapter(Activity activity, List<GenreModel> genreList) {
        idGenre = "";
        paramsGenre = "";
        genreModelList= new ArrayList<>();
        genreModelList.addAll(genreList);
        ArrayAdapter<GenreModel> adapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_dropdown_item_1line, genreModelList);
        actGenre.setAdapter(adapter);

        actGenre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    actGenre.showDropDown();
            }
        });

        actGenre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actGenre.showDropDown();
                return false;
            }
        });


        actGenre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject selectedGenre = new JSONObject(new Gson().toJson(actGenre.getAdapter().getItem(position)));
                    idGenre = selectedGenre.getString("id");
                    paramsGenre = selectedGenre.getString("id") + " - " + selectedGenre.getString("name");
                    System.out.println("Selected Province: " + idGenre);
                    System.out.println("Selected Province: " + paramsGenre);
                    actGenre.setVisibility(View.GONE);
                    tvLabelGenre.setText(selectedGenre.getString("name"));
                    clGenre.setVisibility(View.VISIBLE);

//                    LocationRequest.getCitiesList(activity, idProvince);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        imgGenreRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idGenre = "";
                paramsGenre = "";
                actGenre.setText("");
                actGenre.setVisibility(View.VISIBLE);
                clGenre.setVisibility(View.GONE);
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    public void searchMulti(final Activity activity, final Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            close = context.getDrawable(R.drawable.ic_close_black_24dp);
            search = context.getDrawable(R.drawable.ic_search_black_24dp);
        } else {
            close = activity.getResources().getDrawable(R.drawable.ic_close_black_24dp);
            search = activity.getResources().getDrawable(R.drawable.ic_search_black_24dp);
        }
        etSearch.setCompoundDrawablesWithIntrinsicBounds(null,null,search,null);
        etSearch.addTextChangedListener(searchWatcher);
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Harap tunggu...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    MovieListRequest.searchString= etSearch.getText().toString();
                    MovieListRequest.page = 1;
                    movieListModels.clear();
                    MovieListRequest.getMovieListSearch(activity);
                }

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Harap tunggu...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                MovieListRequest.searchString= etSearch.getText().toString();
                MovieListRequest.page = 1;
                movieListModels.clear();
                MovieListRequest.getMovieListSearch(activity);
                return false;
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etSearch.getRight() -
                            etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(etSearch.length()>0){
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Harap tunggu...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            etSearch.setText("");
                            event.setAction(MotionEvent.ACTION_CANCEL);
                            MovieListRequest.searchString="";
                            MovieListRequest.page = 1;
                            MovieListRequest.getMovieList(activity);
                        }

                        return false;
                    }
                }
                return false;
            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                CloseSoftKeyboard.hideSoftKeyboard(v, activity);
            }
        });
    }

    public TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            customerAdapter.getFilter().filter(s);

            if (etSearch.getText().length() > 0) {
                etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, close, null);

            } else {
                etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        null,null,search,null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void initAdapter(Activity activity) {

        movieListModels = new ArrayList<>();
        dashboardAdapter = new DashboardAdapter(this, movieListModels, this);

        rcMovies.setLayoutManager(new GridLayoutManager(activity,2));
        rcMovies.addItemDecoration(itemDecorator);
        rcMovies.setAdapter(dashboardAdapter);
    }
    public static void updateDataMovies(List<MovieListModel>movieListModelsResponse, int page){
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
                rcMovies.scrollToPosition(0);
    }
    static boolean isLoading = false;

    public static void initScrollListener(final Activity activity) {
        rcMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        MovieListRequest.getMovieList(activity);
    }

    private void requestFilter(Activity activity){

        MovieListRequest.page = 1;
        movieListModels.clear();
        MovieListRequest.getMovieList(activity);



    }


}
