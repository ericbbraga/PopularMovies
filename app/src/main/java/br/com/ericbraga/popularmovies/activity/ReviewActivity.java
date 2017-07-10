package br.com.ericbraga.popularmovies.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.domain.MovieInfoDomain;
import br.com.ericbraga.popularmovies.domain.MovieReview;
import br.com.ericbraga.popularmovies.view.ReviewsAdapter;

public class ReviewActivity extends Activity {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    private RecyclerView mReviewsRecyclerView;
    private View mErrorView;
    private View mProgressBar;
    private View mReviewEmpty;
    private MovieInfoDomain mMovieDomain;
    private MovieInfo mMovieInfo;
    private ReviewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_review);

        Intent intent = getIntent();
        Context context = this;
        mMovieDomain = new MovieInfoDomain(context);
        mMovieInfo = intent.getParcelableExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA);

        mReviewsRecyclerView = (RecyclerView) findViewById(R.id.reviews);
        mErrorView = findViewById(R.id.tv_error_message);
        mProgressBar = findViewById(R.id.pb_loading_indicator);
        mReviewEmpty = findViewById(R.id.tv_empty_message);

        TextView titleView = (TextView) findViewById(R.id.tv_info_detail);
        titleView.setText(getText(R.string.reviews));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        mReviewsRecyclerView.setLayoutManager(manager);
        mReviewsRecyclerView.setHasFixedSize(true);

        mAdapter = new ReviewsAdapter();
        mReviewsRecyclerView.setAdapter(mAdapter);

        new ReviewLoader().execute();

    }

    private class ReviewLoader extends AsyncTask<Void, Void, List<MovieReview>> {
        private boolean mRequestWorked;

        public ReviewLoader() {
            mRequestWorked = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideErrorMessage();
            hideEmptyMessage();
            showLoading();
        }

        @Override
        protected List<MovieReview> doInBackground(Void... voids) {
            List<MovieReview> reviews = new ArrayList<>();

            try {
                mRequestWorked = true;
                reviews = mMovieDomain.getReviewsFrom(mMovieInfo);

            } catch (Exception e) {
                mRequestWorked = false;
                Log.e(TAG, e.getMessage());
            }

            return reviews;
        }

        @Override
        protected void onPostExecute(List<MovieReview> reviews) {
            super.onPostExecute(reviews);

            if (mRequestWorked) {
                hideLoading();

                if (reviews.size() == 0) {
                    showEmptyMessage();
                } else {
                    setReviewView(reviews);
                }

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

    private void showEmptyMessage() {
        mReviewEmpty.setVisibility(View.VISIBLE);
    }

    private void hideEmptyMessage() {
        mReviewEmpty.setVisibility(View.INVISIBLE);
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void setReviewView(List<MovieReview> reviews) {
        mAdapter.setReviews(reviews);
    }

}
