package br.com.ericbraga.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericbraga25.
 */

public class MovieInfo implements Parcelable {

    private int mId;
    private String mTitle;
    private String mReleaseDate;
    private String mPosterPath;
    private String mPlotSynopsis;
    private double mRating;

    private List<MovieTrailer> mTrailers;

    public MovieInfo(int id, String title, String releaseDate, String posterPath, String synopsis, double rating) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
        mPlotSynopsis = synopsis;
        mRating = rating;
        mTrailers = new ArrayList<>();
    }

    private MovieInfo(Parcel source) {
        mId = source.readInt();
        mTitle = source.readString();
        mReleaseDate = source.readString();
        mPosterPath = source.readString();
        mPlotSynopsis = source.readString();
        mRating = source.readDouble();
        mTrailers = new ArrayList<>();
        source.readTypedList(mTrailers, MovieTrailer.CREATOR);
    }

    public void addTrailer(MovieTrailer trailer) {
        mTrailers.add(trailer);
    }

    public void addTrailer(List<MovieTrailer> trailers) {
        mTrailers.addAll(trailers);
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public double getRating() {
        return mRating;
    }

    public List<MovieTrailer> getTrailers() {
        return mTrailers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mPosterPath);
        dest.writeString(mPlotSynopsis);
        dest.writeDouble(mRating);
        dest.writeTypedList(mTrailers);
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel parcel) {
            return new MovieInfo(parcel);
        }

        @Override
        public MovieInfo[] newArray(int i) {
            return new MovieInfo[i];
        }
    };
}
