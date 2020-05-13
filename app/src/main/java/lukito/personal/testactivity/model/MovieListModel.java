package lukito.personal.testactivity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieListModel implements Parcelable {
    @SerializedName("popularity")
    private String popularity;

    @SerializedName("vote_count")
    private String votecoungt;

    @SerializedName("video")
    private String video;

    @SerializedName("poster_path")
    private String posterpath;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private String adult;

    @SerializedName("backdrop_path")
    private String backdroppath;

    @SerializedName("original_languange")
    private String originallanguange;

    @SerializedName("original_title")
    private String originaltitle;


    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private String voteaverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releasedate;

    public MovieListModel(){}

    public MovieListModel(String popularity, String votecoungt, String video, String posterpath, int id, String adult, String backdroppath, String originallanguange, String originaltitle, String title, String voteaverage, String overview, String releasedate) {
        this.popularity = popularity;
        this.votecoungt = votecoungt;
        this.video = video;
        this.posterpath = posterpath;
        this.id = id;
        this.adult = adult;
        this.backdroppath = backdroppath;
        this.originallanguange = originallanguange;
        this.originaltitle = originaltitle;
        this.title = title;
        this.voteaverage = voteaverage;
        this.overview = overview;
        this.releasedate = releasedate;
    }


    protected MovieListModel(Parcel in) {
        popularity = in.readString();
        votecoungt = in.readString();
        video = in.readString();
        posterpath = in.readString();
        id = in.readInt();
        adult = in.readString();
        backdroppath = in.readString();
        originallanguange = in.readString();
        originaltitle = in.readString();
        title = in.readString();
        voteaverage = in.readString();
        overview = in.readString();
        releasedate = in.readString();
    }

    public static final Creator<MovieListModel> CREATOR = new Creator<MovieListModel>() {
        @Override
        public MovieListModel createFromParcel(Parcel in) {
            return new MovieListModel(in);
        }

        @Override
        public MovieListModel[] newArray(int size) {
            return new MovieListModel[size];
        }
    };

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVotecoungt() {
        return votecoungt;
    }

    public void setVotecoungt(String votecoungt) {
        this.votecoungt = votecoungt;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }

    public String getOriginallanguange() {
        return originallanguange;
    }

    public void setOriginallanguange(String originallanguange) {
        this.originallanguange = originallanguange;
    }

    public String getOriginaltitle() {
        return originaltitle;
    }

    public void setOriginaltitle(String originaltitle) {
        this.originaltitle = originaltitle;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteaverage() {
        return voteaverage;
    }

    public void setVoteaverage(String voteaverage) {
        this.voteaverage = voteaverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(popularity);
        parcel.writeString(votecoungt);
        parcel.writeString(video);
        parcel.writeString(posterpath);
        parcel.writeInt(id);
        parcel.writeString(adult);
        parcel.writeString(backdroppath);
        parcel.writeString(originallanguange);
        parcel.writeString(originaltitle);
        parcel.writeString(title);
        parcel.writeString(voteaverage);
        parcel.writeString(overview);
        parcel.writeString(releasedate);
    }
}

