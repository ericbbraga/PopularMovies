package br.com.ericbraga.popularmovies.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericbraga on 04/07/17.
 */

public abstract class JSonParser<T> {

    private static final String RESULTS = "results";

    public abstract T makeObject(JSONObject jsonObject) throws JSONException;

    public List<T> extract(String json) throws JSonMovieParserException {

        List<T> extractedList = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray jsonResults = jsonRootObject.getJSONArray(RESULTS);

            for (int i = 0; i < jsonResults.length(); i++) {
                T object = makeObject( jsonResults.getJSONObject(i) );
                extractedList.add(object);
            }

        } catch (JSONException e) {
            throw new JSonMovieParserException(e);
        }

        return extractedList;

    }

}
