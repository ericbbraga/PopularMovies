package br.com.ericbraga.popularmovies.parser;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.ericbraga.popularmovies.domain.MovieInfo;
import br.com.ericbraga.popularmovies.domain.MovieType;

/**
 * Created by ericbraga25.
 */

public class JSonMovieParser extends JSonParser<MovieInfo> {

    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";

    private final int mType;

    public JSonMovieParser(@MovieType int type) {
        mType = type;
    }

    @Override
    public MovieInfo makeObject(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt(MOVIE_ID);
        String title = jsonObject.getString(MOVIE_TITLE);
        String releaseDate = jsonObject.getString(MOVIE_RELEASE_DATE);
        String posterPath = jsonObject.getString(MOVIE_POSTER_PATH);
        String synopsis = jsonObject.getString(MOVIE_OVERVIEW);
        double rating = jsonObject.getDouble(MOVIE_VOTE_AVERAGE);

        return new MovieInfo(id, title, releaseDate, posterPath, synopsis, rating, false, mType);
    }
}
