package br.com.ericbraga.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.domain.MovieInfoDomain;
import br.com.ericbraga.popularmovies.network.NetWorkConnectionException;
import br.com.ericbraga.popularmovies.parser.JSonMovieParserException;
import br.com.ericbraga.popularmovies.view.MovieAdapter;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickHandler{

    private static final int POPULAR = 1;
    private static final int TOP_RATED = 2;
    private static final int FAVORITE = 3;
    public static final String LATEST_OPTION_BUNDLE_PARAM = "LATEST_OPTION";

    private MovieInfoDomain mMovieDomain;

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;

    private MovieAdapter mAdapter;
    private int mLatestOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        MovieAdapter.MovieClickHandler handler = this;

        mMovieDomain = new MovieInfoDomain(context);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        final int totalColumns = getResources().getInteger(R.integer.main_grid_total_elements);

        RecyclerView.LayoutManager manager = new GridLayoutManager(context, totalColumns);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MovieAdapter(context);
        mAdapter.setMovieHandler(handler);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LATEST_OPTION_BUNDLE_PARAM, mLatestOption);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLatestOption = savedInstanceState.getInt(LATEST_OPTION_BUNDLE_PARAM);
    }

    private void showMovieInfoData() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideMovieInfoData() {
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    private void setErrorMessage(@StringRes int resourceMessage) {
        mErrorMessageTextView.setText(resourceMessage);
    }

    private void showProgressBar() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(MovieInfo movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(ExtraIntentParameters.MOVIE_PARCEABLE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_popular:
                loadPopularMovies();
                break;

            case R.id.menu_refresh:
                loadMovies();
                break;

            case R.id.menu_top_rated:
                loadTopRatedMovies();
                break;
            case R.id.menu_favorite:
                loadFavoriteMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMovies() {
        switch (mLatestOption) {
            case POPULAR:
            default:
                loadPopularMovies();
                break;
            case TOP_RATED:
                loadTopRatedMovies();
                break;
            case FAVORITE:
                loadFavoriteMovies();
                break;
        }
    }

    private void loadPopularMovies() {
        mLatestOption = POPULAR;
        setTitle(R.string.title_popular_movies);
        new PopularMoviesAsyncTask().execute();
    }

    private void loadTopRatedMovies() {
        mLatestOption = TOP_RATED;
        setTitle(R.string.title_top_rated_movies);
        new TopRatedAsyncTask().execute();
    }

    private void loadFavoriteMovies() {
        mLatestOption = FAVORITE;
        setTitle(R.string.favorite_movies);
        new FavoriteAsyncTask().execute();
    }

    private abstract class MainLoaderAsyncTask extends AsyncTask<Void, Void, List<MovieInfo>> {

        private Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
            hideMovieInfoData();
        }

        @Override
        protected List<MovieInfo> doInBackground(Void... params) {
            try {
                return getMovies();
            } catch (NetWorkConnectionException | JSonMovieParserException e) {
                mException = e;
            }

            return null;
        }

        abstract List<MovieInfo> getMovies()
                throws JSonMovieParserException, NetWorkConnectionException;

        void updateErrorMessage() {
            setErrorMessage(R.string.error_message);
        }

        @Override
        protected void onPostExecute(List<MovieInfo> movies) {
            super.onPostExecute(movies);
            hideProgressBar();

            if (movies == null || movies.isEmpty()) {
                updateErrorMessage();
                showErrorMessage();

            } else {
                hideErrorMessage();
                showMovieInfoData();
                mAdapter.setMovies(movies);
            }
        }
    }

    private class PopularMoviesAsyncTask extends MainLoaderAsyncTask {
        @Override
        List<MovieInfo> getMovies()
                throws JSonMovieParserException, NetWorkConnectionException {
            return mMovieDomain.getPopularMovies();
        }
    }

    private class TopRatedAsyncTask extends MainLoaderAsyncTask {
        @Override
        List<MovieInfo> getMovies()
                throws JSonMovieParserException, NetWorkConnectionException {
            return mMovieDomain.getTopRatedMovies();
        }
    }

    private class FavoriteAsyncTask extends MainLoaderAsyncTask {
        @Override
        List<MovieInfo> getMovies() throws JSonMovieParserException, NetWorkConnectionException {
            return mMovieDomain.getFavoriteMovies();
        }

        @Override
        void updateErrorMessage() {
            setErrorMessage(R.string.error_favorite_movies);
        }
    }

}
