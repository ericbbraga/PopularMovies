package br.com.ericbraga.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by ericbraga25.
 */

public class MovieInfo implements Parcelable, Comparable<MovieInfo> {
    private long mId;
    private String mTitle;
    private String mReleaseDate;
    private String mPosterPath;
    private String mPlotSynopsis;
    private double mRating;
    private boolean mFavorite;
    private int mMovieType;

    public MovieInfo(long id, String title, String releaseDate, String posterPath, String synopsis,
                     double rating, boolean favorite, @MovieType int movieType) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
        mPlotSynopsis = synopsis;
        mRating = rating;
        mFavorite = favorite;
        mMovieType = movieType;
    }

    private MovieInfo(Parcel source) {
        mId = source.readLong();
        mTitle = source.readString();
        mReleaseDate = source.readString();
        mPosterPath = source.readString();
        mPlotSynopsis = source.readString();
        mRating = source.readDouble();
        mFavorite = source.readInt() == 1;
    }

    public long getId() {
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

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.mFavorite = favorite;
    }

    public int getType() {
        return mMovieType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mPosterPath);
        dest.writeString(mPlotSynopsis);
        dest.writeDouble(mRating);
        dest.writeInt(mFavorite ? 1 : 0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieInfo movieInfo = (MovieInfo) o;

        return mId == movieInfo.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    @Override
    public int compareTo(@NonNull MovieInfo o) {
        return (int) (getId() - o.getId());
    }
}
