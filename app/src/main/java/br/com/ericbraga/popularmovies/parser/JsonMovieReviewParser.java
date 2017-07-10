package br.com.ericbraga.popularmovies.parser;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.ericbraga.popularmovies.domain.MovieReview;

/**
 * Created by ericbraga on 10/07/17.
 */

public class JsonMovieReviewParser extends JSonParser<MovieReview> {
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_URL = "url";


    public JsonMovieReviewParser(String json) {
        super(json);
    }

    @Override
    public MovieReview makeObject(JSONObject jsonObject) throws JSONException {

        String author = jsonObject.getString(REVIEW_AUTHOR);
        String content = jsonObject.getString(REVIEW_CONTENT);
        String url = jsonObject.getString(REVIEW_URL);

        return new MovieReview(author, content, url);
    }
}
