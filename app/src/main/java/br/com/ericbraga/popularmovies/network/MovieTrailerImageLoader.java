package br.com.ericbraga.popularmovies.network;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.ericbraga.popularmovies.domain.MovieTrailer;

/**
 * Created by ericbraga25.
 */

public class MovieTrailerImageLoader {
    private static final String YOUTUBE_KEY = "{key}";

    private static final String BASE_URL = "http://img.youtube.com/vi/" + YOUTUBE_KEY + "/0.jpg";

    private final Context mContext;

    public MovieTrailerImageLoader(Context context) {
        mContext = context;
    }

    public void loadImage(MovieTrailer movieTrailer, ImageView view) {
        String imageURL = BASE_URL.replace(YOUTUBE_KEY, movieTrailer.getYoutubeKey());
        Uri uri = Uri.parse(imageURL).buildUpon().build();
        Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
    }
}
