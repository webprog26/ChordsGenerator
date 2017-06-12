package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.app_data_manager;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.AddChordsToLocalDbCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.ConvertDataToPOJOClassesCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.ReadJSONDataCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ReadJSONDataEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers.AppEventsHandler;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers.AppStartEventsHandler;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.ShapeTableNameHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.StartEventsCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Manages reading data from JSON file which is located in assets directory
 */

public class AppDataManager {

    private final AssetManager mAssetManager;
    private final StartEventsCallback mStartEventsCallback;
    private final AppEventsHandler mAppEventsHandler;
    private final ShapeTableNameHelper mShapeTableNameHelper;

    private int mChordsCount = 0;
    private int mChordsLoadedCount = 0;

    public AppDataManager(AssetManager assetManager, StartEventsCallback startEventsCallback, Resources resources) {
        this.mAssetManager = assetManager;
        this.mStartEventsCallback = startEventsCallback;


        this.mAppEventsHandler = new AppStartEventsHandler(this);
        mShapeTableNameHelper = new ShapeTableNameHelper(resources);
    }

    public void onStart(){
        getAppEventsHandler().subscribe();
    }

    public void onStop(){
        getAppEventsHandler().unsubscribe();
    }

    public void readData(){
        EventBus.getDefault().post(new ReadJSONDataEvent());
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

    public StartEventsCallback getStartEventsCallback() {
        return mStartEventsCallback;
    }

    public ShapeTableNameHelper getShapeTableNameHelper() {
        return mShapeTableNameHelper;
    }

    private AppEventsHandler getAppEventsHandler() {
        return mAppEventsHandler;
    }
}
