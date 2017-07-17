package br.com.ericbraga.popularmovies.network;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.ericbraga.popularmovies.domain.MovieInfo;

/**
 * Created by ericbraga25.
 */

public class MovieImageLoader {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String QUALITY_PARAM = "w185";

    private final Context mContext;

    public MovieImageLoader(Context context) {
        mContext = context;
    }

    public void loadImage(MovieInfo movieInfo, ImageView view) {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(QUALITY_PARAM)
                .appendEncodedPath(movieInfo.getPosterPath())
                .build();

        Glide.with(mContext).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
    }
}
