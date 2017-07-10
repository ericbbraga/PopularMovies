package br.com.ericbraga.popularmovies.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ericbraga.popularmovies.R;
import br.com.ericbraga.popularmovies.domain.MovieReview;

/**
 * Created by ericbraga on 10/07/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<MovieReview> mReviews;

    public ReviewsAdapter() {
        mReviews = new ArrayList<>();
    }

    public void setReviews(List<MovieReview> reviews) {
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reviews, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        MovieReview review = mReviews.get(position);
        holder.mAuthor.setText(review.getAuthor());
        holder.mReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        public TextView mAuthor;
        public TextView mReview;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.author_review);
            mReview = (TextView) itemView.findViewById(R.id.review);
        }
    }
}
