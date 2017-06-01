package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class to load chord shapes images from specific directory in assets
 */

public class LoadBitmapsFromAssetsHelper {

    private static final String TAG = "BitmapsHelper";

    /**
     * Loads single {@link Bitmap} via given path
     * @param assetManager {@link AssetManager}
     * @param fileName {@link String}
     * @return Bitmap
     */
    public static Bitmap loadBitmapFromAssets(AssetManager assetManager, String fileName){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
            Log.i(TAG, "fileName " + fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}
