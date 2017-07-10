package br.com.ericbraga.popularmovies.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.domain.MovieInfoDomain;
import br.com.ericbraga.popularmovies.domain.MovieTrailer;
import br.com.ericbraga.popularmovies.view.TrailersAdapter;

public class TrailerActivity extends Activity implements TrailersAdapter.TrailerClickHandler {

    public static final String TAG = TrailerActivity.class.getSimpleName();

    private TextView mTitle;
    private RecyclerView mTrailersRecycleView;
    private TrailersAdapter mAdapter;
    private MovieInfoDomain mMovieDomain;
    private View mErrorView;
    private View mProgressBar;

    private static final String YOUTUBE_URL = "http://www.youtube.com";
    private static final String YOUTUBE_PATH_WATCH = "watch";
    private static final String YOUTUBE_QUERY_PARAM = "v";
    private MovieInfo mMovieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        mTitle = (TextView) findViewById(R.id.tv_info_detail);
        mTitle.setText(getText(R.string.trailers));
        mTrailersRecycleView = (RecyclerView) findViewById(R.id.trailers_items);
        mErrorView = findViewById(R.id.tv_error_message);
        mProgressBar = findViewById(R.id.pb_loading_indicator);

        Intent intent = getIntent();
        Context context = this;

        mMovieDomain = new MovieInfoDomain(context);
        mMovieInfo = intent.getParcelableExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA);

        final int totalColumns = getResources().getInteger(R.integer.main_grid_total_elements);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, totalColumns);
        mTrailersRecycleView.setLayoutManager(manager);
        mTrailersRecycleView.setHasFixedSize(true);

        TrailersAdapter.TrailerClickHandler handler = this;

        mAdapter = new TrailersAdapter(context);
        mAdapter.setClickHandler(handler);

        mTrailersRecycleView.setAdapter(mAdapter);
        mTrailersRecycleView.stopScroll();

        new TrailerLoader().execute();
    }

    private class TrailerLoader extends AsyncTask<Void, Void, List<MovieTrailer>> {
        private boolean mRequestWorked;

        public TrailerLoader() {
            mRequestWorked = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideErrorMessage();
            showLoading();
        }

        @Override
        protected List<MovieTrailer> doInBackground(Void... voids) {
            List<MovieTrailer> trailers = new ArrayList<>();

            try {
                mRequestWorked = true;
                trailers = mMovieDomain.getTrailersFrom(mMovieInfo);

            } catch (Exception e) {
                mRequestWorked = false;
                Log.e(TAG, e.getMessage());
            }

            return trailers;
        }

        @Override
        protected void onPostExecute(List<MovieTrailer> trailers) {
            super.onPostExecute(trailers);

            if (mRequestWorked) {
                hideLoading();
                setTrailerView(trailers);
            } else {
                showErrorMessage();
            }
        }
    }

    private void showErrorMessage() {
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        mErrorView.setVisibility(View.INVISIBLE);
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void setTrailerView(List<MovieTrailer> trailers) {
        mAdapter.setTrailers(trailers);
    }

    @Override
    public void onItemClick(MovieTrailer trailer) {

        Uri uri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendEncodedPath(YOUTUBE_PATH_WATCH)
                .appendQueryParameter(YOUTUBE_QUERY_PARAM,trailer.getYoutubeKey())
                .build();

        Intent intentYoutube = new Intent(Intent.ACTION_VIEW, uri);
        Intent intent = Intent.createChooser(intentYoutube, getString(R.string.open_with));
        startActivity(intent);
    }

}
