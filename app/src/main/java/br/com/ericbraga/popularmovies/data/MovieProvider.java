package br.com.ericbraga.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ericbraga25.
 */

public class MovieProvider extends ContentProvider {

    public static final int PATH_MOVIES = 100;
    public static final int PATH_SINGLE_MOVIE = 101;
    private static final UriMatcher sMatcher = buildURIMatcher();

    private MovieDBHelper mHelper;

    public static UriMatcher buildURIMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, PATH_MOVIES);
        matcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", PATH_SINGLE_MOVIE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        switch(sMatcher.match(uri)) {
            case PATH_MOVIES:
                SQLiteDatabase database = mHelper.getReadableDatabase();

                return database.query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            default:
                throw new UnsupportedOperationException();
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id;

        switch(sMatcher.match(uri)) {
            case PATH_MOVIES:
                id = insert(values);
                break;

            default:
                throw new UnsupportedOperationException();
        }

        if (id > 0) {
            return ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);

        } else {
            throw new SQLException("Unable to insert row " + uri);
        }
    }

    private long insert(ContentValues values) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        database.close();

        return id;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mHelper.getWritableDatabase();
        int deleteRows = 0;

        try {
            switch (sMatcher.match(uri)) {
                case PATH_MOVIES:
                    deleteRows = database.delete(MovieContract.MovieEntry.TABLE_NAME, null, null);
                    break;

                case PATH_SINGLE_MOVIE:
                    String id = uri.getPathSegments().get(1);
                    selection = String.format("%s=?", MovieContract.MovieEntry._ID);
                    selectionArgs = new String[]{id};
                    deleteRows = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                    break;

                default:
                    throw new UnsupportedOperationException();
            }

        } finally {
            database.close();
        }


        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updateRows = 0;

        switch (sMatcher.match(uri)) {
            case PATH_SINGLE_MOVIE:
                String id = uri.getPathSegments().get(1);
                SQLiteDatabase database = mHelper.getWritableDatabase();
                String where = String.format("%s=?",MovieContract.MovieEntry._ID );
                String[] whereArgs = new String[]{id};

                updateRows = database.update(MovieContract.MovieEntry.TABLE_NAME, values, where, whereArgs);
                database.close();
                break;

            default:
                throw new UnsupportedOperationException();
        }

        return updateRows;
    }
}
