package br.com.ericbraga.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.configuration.Configurations;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.view.MovieAdapter;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickHandler{

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageTextView;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        List<MovieInfo> movies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MovieInfo fake = new MovieInfo("fake movie #" + i, "2017-01-01", "/posterpath", "Synopsis fake", i);
            movies.add(fake);
        }

        MovieAdapter adapter = new MovieAdapter(movies);
        adapter.setMovieHandler(this);
        mRecyclerView.setAdapter(adapter);
    }

    public void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    public void hideErrorMessage() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(MovieInfo movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Configurations.MOVIE_PARCEABLE_EXTRA, movie);
        startActivity(intent);
    }
}
