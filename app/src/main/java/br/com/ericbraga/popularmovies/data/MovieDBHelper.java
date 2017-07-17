package br.com.ericbraga.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ericbraga25.
 */

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "movie.db";
    public static final int VERSION = 1;


    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = String.format(
                "CREATE TABLE %s (" +
                        "%s INTEGER AUTO_INCREMENT," +
                        "%s VARCHAR(100)," +
                        "%s VARCHAR(100)," +
                        "%s VARCHAR(100)," +
                        "%s VARCHAR(100)," +
                        "%s REAL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL" +
                 ")",

                MovieContract.MovieEntry.TABLE_NAME,
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.TITLE_COLUMN,
                MovieContract.MovieEntry.RELEASE_DATE_COLUMN,
                MovieContract.MovieEntry.POSTER_PATH_COLUMN,
                MovieContract.MovieEntry.SYNOPSIS_COLUMN,
                MovieContract.MovieEntry.RATING_COLUMN,
                MovieContract.MovieEntry.FAVORITE_COLUMN,
                MovieContract.MovieEntry.TYPE
        );

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME, null, null);
        onCreate(sqLiteDatabase);
    }
}
