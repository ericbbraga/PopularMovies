package br.com.ericbraga.popularmovies.testutils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ericbraga on 04/07/17.
 */

public class Util {
    @NonNull
    public static String readResourceAsset(Context ctx, String assetName) throws IOException {
        InputStream is = ctx.getResources().getAssets().open(assetName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

        while ( (line = bufferedReader.readLine()) != null ) {
            sb.append(line);
        }
        return sb.toString();
    }

}
