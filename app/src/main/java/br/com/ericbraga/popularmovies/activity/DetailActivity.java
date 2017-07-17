package br.com.ericbraga.popularmovies.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import br.com.ericbraga.popularmovies.R;
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

        View buttonTrailer = findViewById(R.id.btn_trailers);
        View buttonReview = findViewById(R.id.btn_reviews);

        buttonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, TrailerActivity.class);
                it.putExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA, mMovieInfo);
                startActivity(it);
            }
        });

        buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, ReviewActivity.class);
                it.putExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA, mMovieInfo);
                startActivity(it);
            }
        });

        MovieImageLoader loader = new MovieImageLoader(context);
        loader.loadImage(mMovieInfo, posterImageView);

        titleTextView.setText(mMovieInfo.getTitle());
        releaseDateTextView.setText(mMovieInfo.getReleaseDate());
        synopsisTextView.setText(mMovieInfo.getPlotSynopsis());
        ratingTextView.setText( String.format(Locale.ENGLISH, "%.1f/10", mMovieInfo.getRating()) );

    }
}
