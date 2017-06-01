package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.BitmapsArrayLoadedEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesBitmapsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.LoadBitmapsFromAssetsHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by webpr on 01.06.2017.
 */

public class ChordShapesFragmentManager {

    private static final String TAG = "ChordManager";

    private ArrayList<Bitmap> mChordShapesBitmaps = new ArrayList<>();
    private final String mChordShapesTableTitle;
    private final AssetManager mAssetManager;

    public ChordShapesFragmentManager(String chordShapesTableTitle, AssetManager assetManager) {
        this.mChordShapesTableTitle = chordShapesTableTitle;
        this.mAssetManager = assetManager;
    }

    public void loadShapesBitmaps(){
        EventBus.getDefault().post(new LoadChordShapesBitmapsEvent(getChordShapesTableTitle()));
    }

    private String getChordShapesTableTitle() {
        return mChordShapesTableTitle;
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
