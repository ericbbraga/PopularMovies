package br.com.ericbraga.popularmovies.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.ericbraga.popularmovies.domain.MovieInfo;

/**
 * Created by ericbraga25.
 */

public class JSonMovieParser {

    private static final String RESULTS = "results";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";

    private String mJson;

    public JSonMovieParser(String json) {
        mJson = json;
    }

    public List<MovieInfo> extractMovies() throws JSonMovieParserException {

        List<MovieInfo> movies = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(mJson);
            JSONArray jsonResults = jsonRootObject.getJSONArray(RESULTS);

            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject movieObject = jsonResults.getJSONObject(i);

                String title = movieObject.getString(MOVIE_TITLE);
                String releaseDate = movieObject.getString(MOVIE_RELEASE_DATE);
                String posterPath = movieObject.getString(MOVIE_POSTER_PATH);
                String synopsis = movieObject.getString(MOVIE_OVERVIEW);
                double rating = movieObject.getDouble(MOVIE_VOTE_AVERAGE);

                MovieInfo movieInfo = new MovieInfo(title, releaseDate, posterPath, synopsis, rating);
                movies.add(movieInfo);
            }

        } catch (JSONException e) {
            throw new JSonMovieParserException(e);
        }

        return movies;

    }
}
