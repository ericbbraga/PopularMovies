package br.com.ericbraga.popularmovies.parser;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.ericbraga.popularmovies.domain.MovieTrailer;

/**
 * Created by ericbraga on 04/07/17.
 */

public class JsonMovieTrailerParser extends JSonParser<MovieTrailer> {
    private static final String TRAILER_ID = "id";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_YOUTUBE_KEY = "key";

    public JsonMovieTrailerParser(String json) {
        super(json);
    }

    @Override
    public MovieTrailer makeObject(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.getString(TRAILER_ID);
        String key = jsonObject.getString(TRAILER_YOUTUBE_KEY);
        String name = jsonObject.getString(TRAILER_NAME);

        return new MovieTrailer(id, key, name);
    }
}
