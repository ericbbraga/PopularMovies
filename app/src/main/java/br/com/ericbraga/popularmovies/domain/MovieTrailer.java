package br.com.ericbraga.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericbraga on 04/07/17.
 */

public class MovieTrailer implements Parcelable {
    private String mId;
    private String mYoutubeKey;
    private String mName;

    public MovieTrailer(String mId, String mYoutubeKey, String mName) {
        this.mId = mId;
        this.mYoutubeKey = mYoutubeKey;
        this.mName = mName;
    }

    protected MovieTrailer(Parcel in) {
        mId = in.readString();
        mYoutubeKey = in.readString();
        mName = in.readString();
    }

    public String getId() {
        return mId;
    }

    public String getYoutubeKey() {
        return mYoutubeKey;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mYoutubeKey);
        dest.writeString(mName);
    }

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
}
