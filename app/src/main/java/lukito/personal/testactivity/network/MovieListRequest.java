package lukito.personal.testactivity.network;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import lukito.personal.testactivity.DetailMoviesActivity;
import lukito.personal.testactivity.MainActivity;
import lukito.personal.testactivity.TvMainActivity;
import lukito.personal.testactivity.model.GenreModel;
import lukito.personal.testactivity.model.MovieListModel;
import lukito.personal.testactivity.model.ReviewMovies;
import lukito.personal.testactivity.model.TvListModel;

public class MovieListRequest {
    public static String message;
    public static int page;
    public static String title,overview,popularity,searchString;
    public static String sortByString = "popularity.desc";
    public static String responseDate;

    public static void getMovieList(final Activity activity){
        OkHttpClient client = new OkHttpClient();

        Request requestHttp = new Request.Builder()
                .url(API.Host+API.MoviesList+"?api_key="+API.APIKey+"&" +
                        "language=en-US&sort_by="+sortByString+"&include_adult=true" +
                        "&include_video=false&page="+page).build();

        Log.e("URL",API.Host+API.MoviesList+"?api_key="+API.APIKey+"&" +
                "language=en-US&sort_by="+sortByString+"&include_adult=true"+
                "&include_video=false&page="+page);

        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Error",e.toString());

                    }
                });

            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String responseData = response.body().string();
                Log.e("RESPONSE BODY", responseData);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            String pageResponse = json.getString("page");
                            String totalResults = json.getString("total_results");
                            String totalPages = json.getString("total_pages");
                            String results = json.getString("results");

                            if(response.code()==200){

                                List<MovieListModel> movieListModels= new Gson()
                                        .fromJson(results,
                                                new TypeToken<List<MovieListModel>>() {
                                                }.getType());

                                MainActivity.updateDataMovies(movieListModels, page);
                                MainActivity.progressDialog.dismiss();
//                                MainActivity.updateDataMoviesAnimation(movieListModels,page);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }
    public static void getTvList(final Activity activity){
        OkHttpClient client = new OkHttpClient();

        Request requestHttp = new Request.Builder()
                .url(API.Host+API.TvList+"?api_key="+API.APIKey+"&" +
                        "language=en-US&sort_by="+sortByString+"&timezone=America%2FNew_York&" +
                        "page="+page).build();

        Log.e("URL",API.Host+API.TvList+"?api_key="+API.APIKey+"&" +
                "language=en-US&sort_by="+sortByString+"&timezone=America%2FNew_York&" +
                "page="+page);

        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Error",e.toString());

                    }
                });

            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String responseData = response.body().string();
                Log.e("RESPONSE BODY", responseData);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            String pageResponse = json.getString("page");
                            String totalResults = json.getString("total_results");
                            String totalPages = json.getString("total_pages");
                            String results = json.getString("results");

                            if(response.code()==200){

                                List<TvListModel> tvListModels= new Gson()
                                        .fromJson(results,
                                                new TypeToken<List<TvListModel>>() {
                                                }.getType());

                                TvMainActivity.updateDataMovies(tvListModels, page);
                                TvMainActivity.progressDialog.dismiss();
//                                MainActivity.updateDataMoviesAnimation(movieListModels,page);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }



    public static void getMovieListSearch(final Activity activity){
        OkHttpClient client = new OkHttpClient();

        Request requestHttp = new Request.Builder()
                .url(API.Host+API.SearcMulti+"?api_key="+API.APIKey+"&" +
                        "language=en-US&"+"query="+searchString+
                        "&include_adult=true"+
                        "&include_video=false&page="+page).build();

        Log.e("URL Search",API.Host+API.SearcMulti+"?api_key="+API.APIKey+"&" +
                "language=en-US&"+"query="+searchString+
                "&include_adult=true"+
                "&include_video=false&page="+page);

        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Error",e.toString());

                    }
                });

            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String responseData = response.body().string();
                Log.e("RESPONSE BODY SEARCH", responseData);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            String pageResponse = json.getString("page");
                            String totalResults = json.getString("total_results");
                            String totalPages = json.getString("total_pages");
                            String results = json.getString("results");

                            if(response.code()==200){

                                List<MovieListModel> movieListModels= new Gson()
                                        .fromJson(results,
                                                new TypeToken<List<MovieListModel>>() {
                                                }.getType());

                                MainActivity.updateDataMovies(movieListModels, page);
//                                MainActivity.updateDataMoviesAnimation(movieListModels,page);
                                MainActivity.progressDialog.dismiss();

                            }else if(response.code()!=200){
                                if (MainActivity.movieListModels.size() != 0){
                                    MainActivity.movieListModels.remove(MainActivity.movieListModels.size() - 1);
                                    int scrollPosition = MainActivity.movieListModels.size();
                                    MainActivity.dashboardAdapter.notifyItemRemoved(scrollPosition);
                                    MainActivity.progressDialog.dismiss();
                                } else {
                                    MainActivity.movieListModels.clear();
                                    MainActivity.dashboardAdapter.replaceItemFiltered(MainActivity.movieListModels);
                                    MainActivity.dashboardAdapter.notifyDataSetChanged();
                                    MainActivity.progressDialog.dismiss();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }



    public static void getMovieReview (final Activity activity){

        OkHttpClient client = new OkHttpClient();

        Request requestHttp = new Request.Builder()
                .url(API.Host+API.Review+"/"+ DetailMoviesActivity.idMovies+"/reviews"+"?api_key="+API.APIKey+"&" +
                        "language=en-US&page="+page+"&append_to_response=videos,images").build();

        Log.e("URL TEST",API.Host+API.Review+"/"+ DetailMoviesActivity.idMovies+"/reviews"+"?api_key="+API.APIKey+"&" +
                "language=en-US&page="+page+"&append_to_response=videos,images");

        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Error",e.toString());

                    }
                });

            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String responseData = response.body().string();
                Log.e("RESPONSE BODY Review", responseData);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            String id = json.getString("id");
                            String pages = json.getString("page");
                            String results = json.getString("results");

                            if(response.code()==200){

                                List<ReviewMovies> reviewMoviesList= new Gson()
                                        .fromJson(results,
                                                new TypeToken<List<ReviewMovies>>() {
                                                }.getType());

                                DetailMoviesActivity.updateDataMovies(reviewMoviesList, page);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    public static void getGenreList(final Activity activity){
        OkHttpClient client =  new OkHttpClient();
        Request requestHttp = new Request.Builder()
                .get()
                .url(API.GetGenre)
                .build();
        client.newCall(requestHttp).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Error",e.toString());

                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final  String responseData =response.body().string();
                Log.e("Tag Genre",responseData);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(responseData);
                            if(json.getString("genres")!=null){
                                String arrayGenres = json.getString("genres");
                                List<GenreModel> genreModels = new Gson()
                                        .fromJson(arrayGenres, new TypeToken<List<GenreModel>>(){}.getType());
                                MainActivity.setGenreAdapter(activity,genreModels);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });



    }


}
