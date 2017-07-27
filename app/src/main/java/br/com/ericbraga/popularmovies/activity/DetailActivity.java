package br.com.ericbraga.popularmovies.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.data.MovieContract;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.network.MovieImageLoader;

public class DetailActivity extends Activity {

    private MovieInfo mMovieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_info);

        Intent intent = getIntent();

        final Context context = this;
        mMovieInfo = intent.getParcelableExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA);

        configureLayoutElements(context);
    }

    private void configureLayoutElements(final Context context) {
        ImageView posterImageView = (ImageView) findViewById(R.id.iv_poster_detail);
        TextView titleTextView = (TextView) findViewById(R.id.tv_info_detail);
        TextView releaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date_detail);
        TextView synopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis_detail);
        TextView ratingTextView = (TextView) findViewById(R.id.tv_movie_rating_detail);
        final FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.btn_favorite);

        View buttonTrailer = findViewById(R.id.btn_trailers);
        View buttonReview = findViewById(R.id.btn_reviews);

        buttonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrailerActivity(context);
            }
        });

        buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReviewActivity(context);
            }
        });

        favoriteButton.setImageResource(mMovieInfo.isFavorite() ? R.drawable.ic_star_fill :
                R.drawable.ic_star);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favoriteValue = !mMovieInfo.isFavorite();
                int favorite = favoriteValue ? 1 : 0;
                if (updateMovie(context, favorite) > 0) {
                    mMovieInfo.setFavorite(favoriteValue);
                }

                favoriteButton.setImageResource(mMovieInfo.isFavorite() ? R.drawable.ic_star_fill :
                        R.drawable.ic_star);

                int messageRes = favoriteValue ?
                        R.string.change_favorite_status_movie : R.string.change_unfavorite_status_movie;

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                        messageRes, Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        });


        MovieImageLoader loader = new MovieImageLoader(context);
        loader.loadImage(mMovieInfo, posterImageView);

        titleTextView.setText(mMovieInfo.getTitle());
        releaseDateTextView.setText(mMovieInfo.getReleaseDate());
        synopsisTextView.setText(mMovieInfo.getPlotSynopsis());
        ratingTextView.setText( String.format(Locale.ENGLISH, "%.1f/10", mMovieInfo.getRating()) );

    }

    private void startTrailerActivity(Context context) {
        Intent it = new Intent(context, TrailerActivity.class);
        it.putExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA, mMovieInfo);
        startActivity(it);
    }

    private void startReviewActivity(Context context) {
        Intent it = new Intent(context, ReviewActivity.class);
        it.putExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA, mMovieInfo);
        startActivity(it);
    }

    private int updateMovie(Context context, int favorite) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.FAVORITE_COLUMN, favorite);

        Uri contentUri = MovieContract.MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(Long.toString(mMovieInfo.getId()))
                .build();

        return resolver.update(contentUri, values, null, null);
    }
}
