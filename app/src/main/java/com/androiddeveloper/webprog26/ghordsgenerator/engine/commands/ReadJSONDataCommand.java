package com.androiddeveloper.webprog26.ghordsgenerator.engine.commands;

import android.content.res.AssetManager;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.realizers.ReadJSONRealizer;

/**
 * Reads data from JSON-file
 */

public class ReadJSONDataCommand implements Command {

    private final AssetManager mAssetManager;

    public ReadJSONDataCommand(AssetManager assetManager) {
        this.mAssetManager = assetManager;
    }

    @Override
    public void execute() {
        new ReadJSONRealizer(getAssetManager()).realize();
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }
}
