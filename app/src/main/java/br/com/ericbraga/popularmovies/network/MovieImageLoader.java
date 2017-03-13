package br.com.ericbraga.popularmovies.network;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.ericbraga.popularmovies.domain.MovieInfo;

/**
 * Created by ericbraga25.
 */

public class MovieImageLoader {

    private final String BASE_URL = "http://image.tmdb.org/t/p/";

    public enum MovieImageQuality {
        BASIC("w92"),
        NORMAL("w185"),
        BEST("w780");

        private final String mQuality;

        MovieImageQuality(String quality) {
            mQuality = quality;
        }

        public String getQuality() {
            return mQuality;
        }
    }

    private final Context mContext;
    private final MovieImageQuality mQualityImageLoader;

    public MovieImageLoader(Context context, MovieImageQuality quality) {
        mContext = context;
        mQualityImageLoader = quality;
    }

    public void loadImage(MovieInfo movieInfo, ImageView view) {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(mQualityImageLoader.getQuality())
                .appendEncodedPath(movieInfo.getPosterPath())
                .build();

        Glide.with(mContext).load(uri).into(view);
    }
}
