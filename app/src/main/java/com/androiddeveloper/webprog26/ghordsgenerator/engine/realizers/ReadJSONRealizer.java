package com.androiddeveloper.webprog26.ghordsgenerator.engine.realizers;

import android.content.res.AssetManager;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.JSONDataHasBeenReadEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reads data from JSON in assets
 */

public class ReadJSONRealizer implements Realizer{

    private static final String TAG = "ReadJSONRealizer";

    private static final String PROPER_UTF_8_ENCODING = "UTF-8";

    //JSON file name in assets dir
    private static final String JSON_FILE_NAME = "shapes/shapes_demo.json";

    private final AssetManager mAssetManager;

    public ReadJSONRealizer(AssetManager assetManager) {
        this.mAssetManager = assetManager;
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }

    @Override
    public void realize() {
        EventBus.getDefault().post(new JSONDataHasBeenReadEvent(loadJSONFromAsset(getAssetManager(), JSON_FILE_NAME)));
    }


    /**
     * Reads .json file directly from assets directory and transform it into the {@link String}
     * @param assetManager {@link AssetManager}
     * @param jsonFilename {@link String}
     * @return {@link String}
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String loadJSONFromAsset(AssetManager assetManager, String jsonFilename) {
        Log.i(TAG, "loadJSONFromAsset()");
        String json;
        try {
            InputStream is = assetManager.open(jsonFilename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, PROPER_UTF_8_ENCODING);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.i(TAG, json);
        return json;

    }
}
