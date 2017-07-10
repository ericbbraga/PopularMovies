package br.com.ericbraga.popularmovies.utils;

/**
 * Created by ericbraga25.
 */

public class Util {

    public static final String extractYearFromFullDate(String fullDate) {
        return fullDate.replaceAll("(\\d{4}).*","$1");
    }

}
