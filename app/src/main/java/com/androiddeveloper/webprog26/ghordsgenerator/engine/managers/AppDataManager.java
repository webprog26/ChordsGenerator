package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers;

import android.content.res.AssetManager;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.AddChordsToLocalDbCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.ConvertDataToPOJOClassesCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.ReadJSONDataCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import java.util.ArrayList;

/**
 * Manages reading data from JSON file which is located in assets directory
 */

public class AppDataManager {

    private final AssetManager mAssetManager;

    private int mChordsCount = 0;
    private int mChordsLoadedCount = 0;

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

    public void addChordsToLocalDB(ArrayList<Chord> chords){
        new AddChordsToLocalDbCommand(chords).execute();
    }

    public int getChordsCount() {
        return mChordsCount;
    }

    public void setChordsCount(int chordsCount) {
        this.mChordsCount = chordsCount;
    }

    public int getChordsLoadedCount() {
        return mChordsLoadedCount;
    }

    public void setChordsLoadedCount(int chordsLoadedCount) {
        this.mChordsLoadedCount = chordsLoadedCount;
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }
}
