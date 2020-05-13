package lukito.personal.testactivity.helper;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import lukito.personal.testactivity.MainActivity;
import lukito.personal.testactivity.model.MovieListModel;
import lukito.personal.testactivity.network.MovieListRequest;


public class SpinnerSortBy {

    public static void SpinnerSetup(final Activity activity){
        String[] arraySortBy = new String[]{
                "SortBy..","Popularity Descending",
                "Popularity Ascending",
                "Rating Descending",
                "Rating Ascending",
                "ReleaseDate Ascending",
                "ReleaseDate Descending",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item,arraySortBy);
        MainActivity.spSortBy.setAdapter(adapter);

    }
    public static void SpinnerTab(final Activity activity){
        String[] arrayChangeTab = new String[]{
                "Movies", "Tv Shows"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item,arrayChangeTab);
        MainActivity.spChangeTab.setAdapter(adapter);
    }


}
