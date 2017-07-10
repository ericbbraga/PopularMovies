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
import br.com.ericbraga.popularmovies.domain.MovieTrailer;
import br.com.ericbraga.popularmovies.network.MovieTrailerImageLoader;

/**
 * Created by ericbraga25.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {
    private List<MovieTrailer> mTrailers;

    private MovieTrailerImageLoader mLoader;

    private TrailerClickHandler mClickHandler;

    public TrailersAdapter(Context context) {
        mTrailers = new ArrayList<>();
        mLoader = new MovieTrailerImageLoader(context);
    }

    public interface TrailerClickHandler {
        void onItemClick(MovieTrailer trailer);
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailers, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        MovieTrailer trailer = mTrailers.get(position);
        mLoader.loadImage(trailer, holder.mPreview);
    }

    public void setTrailers(List<MovieTrailer> trailers) {
        mTrailers.clear();
        mTrailers.addAll(trailers);
        notifyDataSetChanged();
    }

    public void setClickHandler(TrailerClickHandler handler) {
        mClickHandler = handler;
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mPreview;
        public ImageView mPlayIcon;

        public TrailersViewHolder(View itemView) {
            super(itemView);
            mPreview = (ImageView) itemView.findViewById(R.id.iv_trailer_preview);
            mPlayIcon = (ImageView) itemView.findViewById(R.id.iv_trailer_icon);

            mPreview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickHandler != null) {
                int position = getAdapterPosition();
                MovieTrailer trailer = mTrailers.get(position);
                mClickHandler.onItemClick(trailer);
            }
        }
    }

}
