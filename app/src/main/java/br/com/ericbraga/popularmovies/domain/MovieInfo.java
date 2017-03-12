package br.com.ericbraga.popularmovies.domain;

/**
 * Created by ericbraga25.
 */

public class MovieInfo {

    private String mTitle;
    private String mReleaseDate;
    private String mPosterPath;
    private String mPlotSynopsis;
    private double mRating;

    public MovieInfo(String title, String releaseDate, String posterPath, String synopsis, double rating) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
        mPlotSynopsis = synopsis;
        mRating = rating;
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
}
