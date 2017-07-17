package br.com.ericbraga.popularmovies.domain;

/**
 * Created by ericbraga on 21/07/17.
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({MovieType.TOP_MOVIE, MovieType.POPULAR_MOVIE})
public @interface MovieType {

    public static final int TOP_MOVIE = 0;
    public static final int POPULAR_MOVIE = 1;

}
