package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.BitmapsArrayLoadedEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesBitmapsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers.ChordShapesFragmentEventsHandler;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.LoadBitmapsFromAssetsHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.ChordShapesFragmentCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Manages loading {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.ChordShapesFragment}
 * with list of {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} images
 */

public class ChordShapesFragmentManager extends SuperFragmentManager{

    private static final String TAG = "ChordManager";

    private ArrayList<Bitmap> mChordShapesBitmaps = new ArrayList<>();
    private final String mChordShapesTableName;
    private final AssetManager mAssetManager;
    private final ChordShapesFragmentCallback mChordShapesFragmentCallback;
    private final ChordShapesFragmentEventsHandler mChordShapesFragmentEventsHandler;

    public ChordShapesFragmentManager(String chordShapesTableTitle, AssetManager assetManager, ChordShapesFragmentCallback chordShapesFragmentCallback) {
        this.mChordShapesTableName = chordShapesTableTitle;
        this.mAssetManager = assetManager;
        this.mChordShapesFragmentCallback = chordShapesFragmentCallback;
        this.mChordShapesFragmentEventsHandler = new ChordShapesFragmentEventsHandler(this);
    }

    @Override
    public void onStart() {
        getChordShapesFragmentEventsHandler().subscribe();
    }

    @Override
    public void onStop() {
        getChordShapesFragmentEventsHandler().unsubscribe();
    }

    /**
     * Starts loading Chord's {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} images
     * from assets directory
     */
    public void loadShapesBitmaps(){
        String chordShapesTableName = getChordShapesTableName();

        if(chordShapesTableName != null){
            EventBus.getDefault().post(new LoadChordShapesBitmapsEvent(chordShapesTableName));
        }
    }

    private String getChordShapesTableName() {
        return mChordShapesTableName;
    }

    /**
     * Adds loaded {@link Bitmap} images of {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord} shapes
     * to update {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.adapters.ChordShapesAdapter} info
     * @param bitmapsPathList {@link ArrayList}
     */
    public void addShapesBitmapsToList(ArrayList<String> bitmapsPathList){
        for(String bitmapPath: bitmapsPathList){
            getChordShapesBitmaps().add(LoadBitmapsFromAssetsHelper.loadBitmapFromAssets(getAssetManager(), bitmapPath));
        }
        EventBus.getDefault().post(new BitmapsArrayLoadedEvent(getChordShapesBitmaps()));
    }

    /**
     * Returns reference to {@link ArrayList} of {@link Bitmap} images
     * of {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord} shapes
     * @return {@link ArrayList}
     */
    public ArrayList<Bitmap> getChordShapesBitmaps() {
        return mChordShapesBitmaps;
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }

    public ChordShapesFragmentCallback getChordShapesFragmentCallback() {
        return mChordShapesFragmentCallback;
    }

    private ChordShapesFragmentEventsHandler getChordShapesFragmentEventsHandler() {
        return mChordShapesFragmentEventsHandler;
    }
}
