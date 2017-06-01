package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.BitmapsArrayLoadedEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesBitmapsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.LoadBitmapsFromAssetsHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by webpr on 01.06.2017.
 */

public class ChordShapesFragmentManager {

    private static final String TAG = "ChordManager";

    private ArrayList<Bitmap> mChordShapesBitmaps = new ArrayList<>();
    private final String mChordShapesTableName;
    private final AssetManager mAssetManager;

    public ChordShapesFragmentManager(String chordShapesTableTitle, AssetManager assetManager) {
        this.mChordShapesTableName = chordShapesTableTitle;
        this.mAssetManager = assetManager;
    }

    public void loadShapesBitmaps(){
        String chordShapesTableName = getChordShapesTableName();

        if(chordShapesTableName != null){
            EventBus.getDefault().post(new LoadChordShapesBitmapsEvent(chordShapesTableName));
        }
    }

    private String getChordShapesTableName() {
        return mChordShapesTableName;
    }

    public void addShapesBitmapsToList(ArrayList<String> bitmapsPathList){
        for(String bitmapPath: bitmapsPathList){
            getChordShapesBitmaps().add(LoadBitmapsFromAssetsHelper.loadBitmapFromAssets(getAssetManager(), bitmapPath));
        }
        EventBus.getDefault().post(new BitmapsArrayLoadedEvent(getChordShapesBitmaps()));
    }

    public ArrayList<Bitmap> getChordShapesBitmaps() {
        return mChordShapesBitmaps;
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }
}
