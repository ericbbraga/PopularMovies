package br.com.ericbraga.popularmovies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieInfo;

/**
 * Created by ericbraga25.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<MovieInfo> mMovies;

    public MovieAdapter(List<MovieInfo> movies) {
        mMovies = new ArrayList<>();
        mMovies.addAll(movies);
    }

    public void updateMovies(List<MovieInfo> movies) {
        mMovies.clear();
        movies.addAll(movies);
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.poster_layout_item, parent, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        holder.mPosterImageView.setImageResource(android.R.drawable.star_on);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPosterImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.iv_poster_preview);
        }
    }
}
