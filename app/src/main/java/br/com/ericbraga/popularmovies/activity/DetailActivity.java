package br.com.ericbraga.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.network.MovieImageLoader;

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

        Context context = this;
        Intent intent = getIntent();
        MovieInfo movieInfo = intent.getParcelableExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA);

        mPosterImageView = (ImageView) findViewById(R.id.iv_poster_detail);
        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title_detail);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date_detail);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis_detail);
        mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating_detail);

        MovieImageLoader loader =
                new MovieImageLoader(context, MovieImageLoader.MovieImageQuality.BEST);

        loader.loadImage(movieInfo, mPosterImageView);

        mTitleTextView.setText(movieInfo.getTitle());
        mReleaseDateTextView.setText(movieInfo.getReleaseDate());
        mSynopsisTextView.setText(movieInfo.getPlotSynopsis());
        mRatingTextView.setText( Double.toString(movieInfo.getRating()) );

    }
}
