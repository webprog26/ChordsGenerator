package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers;

import android.content.res.AssetManager;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.ConvertDataToPOJOClassesCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.ReadJSONDataCommand;

/**
 * Manages reading data from JSON file which is located in assets directory
 */

public class AppDataManager {

    private final AssetManager mAssetManager;

    public AppDataManager(AssetManager assetManager) {
        this.mAssetManager = assetManager;
    }

    /**
     * Reads data from JSON file
     */
    public void readJSONData(){
        new ReadJSONDataCommand(getAssetManager()).execute();
    }

    public void convertJSONDateToPOJOClasses(String jsonString){
        new ConvertDataToPOJOClassesCommand(jsonString).execute();
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }
}
