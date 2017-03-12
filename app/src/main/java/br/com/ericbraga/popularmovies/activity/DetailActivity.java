package br.com.ericbraga.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.configuration.Configurations;
import br.com.ericbraga.popularmovies.domain.MovieInfo;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPosterImageView;

    private TextView mTitleTextView;

    private TextView mReleaseDateTextView;

    private TextView mSynopsisTextView;

    private TextView mRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_info);

        Intent intent = getIntent();
        MovieInfo movie = intent.getParcelableExtra(Configurations.MOVIE_PARCEABLE_EXTRA);

        mPosterImageView = (ImageView) findViewById(R.id.iv_poster_detail);
        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title_detail);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date_detail);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis_detail);
        mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating_detail);

        // TODO: load the image from moviedb

        mPosterImageView.setImageResource(android.R.drawable.star_on);

        mTitleTextView.setText(movie.getTitle());
        mReleaseDateTextView.setText(movie.getReleaseDate());
        mSynopsisTextView.setText(movie.getPlotSynopsis());
        mRatingTextView.setText( Double.toString(movie.getRating()) );

    }
}
