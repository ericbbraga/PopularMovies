package br.com.ericbraga.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericbraga on 10/07/17.
 */

public class MovieReview implements Parcelable {
    private String mAuthor;
    private String mContent;
    private String mUrl;

    public MovieReview(String author, String content, String url) {
        mAuthor = author;
        mContent = content;
    }

    protected MovieReview(Parcel in) {
        mAuthor = in.readString();
        mContent = in.readString();
        mUrl = in.readString();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mContent);
        dest.writeString(mUrl);
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
}
