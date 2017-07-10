package br.com.ericbraga.popularmovies.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.domain.MovieTrailer;
import br.com.ericbraga.popularmovies.view.TrailersAdapter;

public class TrailerActivity extends Activity implements TrailersAdapter.TrailerClickHandler {

    private TextView mTitle;
    private RecyclerView mTrailersRecycleView;
    private TrailersAdapter mAdapter;

    private static String YOUTUBE_URL = "http://www.youtube.com";
    private static String YOUTUBE_PATH_WATCH = "watch";
    private static String YOUTUBE_QUERY_PARAM = "v";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        Intent intent = getIntent();

        Context context = this;
        final MovieInfo movieInfo = intent.getParcelableExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA);

        mTitle = (TextView) findViewById(R.id.tv_info_detail);
        mTitle.setText(getText(R.string.trailers));
        mTrailersRecycleView = (RecyclerView) findViewById(R.id.trailers_items);

        List<MovieTrailer> trailers = movieInfo.getTrailers();
        changeTrailerViewVisibility(context, trailers);
    }

    private void changeTrailerViewVisibility(Context context, List<MovieTrailer> trailers) {
        final int totalColumns = getResources().getInteger(R.integer.main_grid_total_elements);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, totalColumns);
        mTrailersRecycleView.setLayoutManager(manager);
        mTrailersRecycleView.setHasFixedSize(true);

        TrailersAdapter.TrailerClickHandler handler = this;

        mAdapter = new TrailersAdapter(context);
        mAdapter.setTrailers(trailers);
        mAdapter.setClickHandler(handler);

        mTrailersRecycleView.setAdapter(mAdapter);
        mTrailersRecycleView.stopScroll();
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
