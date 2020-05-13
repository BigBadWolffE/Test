package lukito.personal.testactivity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvListModel implements Parcelable {
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private String popularity;

    @SerializedName("vote_count")
    private String vote_count;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private String vote_average;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster_path;

    public TvListModel(){}


    protected TvListModel(Parcel in) {
        name = in.readString();
        popularity = in.readString();
        vote_count = in.readString();
        first_air_date = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
        id = in.readInt();
        vote_average = in.readString();
        overview = in.readString();
        poster_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(popularity);
        dest.writeString(vote_count);
        dest.writeString(first_air_date);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);
        dest.writeInt(id);
        dest.writeString(vote_average);
        dest.writeString(overview);
        dest.writeString(poster_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvListModel> CREATOR = new Creator<TvListModel>() {
        @Override
        public TvListModel createFromParcel(Parcel in) {
            return new TvListModel(in);
        }

        @Override
        public TvListModel[] newArray(int size) {
            return new TvListModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
