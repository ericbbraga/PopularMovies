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

    private ImageView mPosterImageView;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mSynopsisTextView;
    private TextView mRatingTextView;
    private View mButtonTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_info);

        Intent intent = getIntent();

        final Context context = this;
        final MovieInfo movieInfo = intent.getParcelableExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA);

        mPosterImageView = (ImageView) findViewById(R.id.iv_poster_detail);
        mTitleTextView = (TextView) findViewById(R.id.tv_info_detail);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date_detail);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis_detail);
        mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating_detail);

        mButtonTrailer = findViewById(R.id.btn_trailers);

        mButtonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, TrailerActivity.class);
                it.putExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA, movieInfo);
                startActivity(it);
            }
        });

        MovieImageLoader loader =
                new MovieImageLoader(context, MovieImageLoader.MovieImageQuality.BEST);

        loader.loadImage(movieInfo, mPosterImageView);

        mTitleTextView.setText(movieInfo.getTitle());
        mReleaseDateTextView.setText(movieInfo.getReleaseDate());
        mSynopsisTextView.setText(movieInfo.getPlotSynopsis());
        mRatingTextView.setText( String.format(Locale.ENGLISH, "%.1f/10", movieInfo.getRating()) );
    }



}
