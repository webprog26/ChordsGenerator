package com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers;

import android.graphics.Bitmap;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.adapters.ChordShapesAdapter;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.chords_generator_app.ChordsGeneratorApp;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.BitmapsArrayLoadedEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesBitmapsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks.ChordShapesFragmentCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.ChordShapesFragmentManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Handles events for {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.ChordShapesFragment}
 */

public class ChordShapesFragmentEventsHandler extends AppEventsHandler {

    private static final String TAG = "CSFEventsHandler";

    private final ChordShapesFragmentManager mChordShapesFragmentManager;
    private final ChordShapesFragmentCallback mChordShapesFragmentCallback;

    public ChordShapesFragmentEventsHandler(ChordShapesFragmentManager chordShapesFragmentManager) {
        this.mChordShapesFragmentManager = chordShapesFragmentManager;
        this.mChordShapesFragmentCallback = chordShapesFragmentManager.getChordShapesFragmentCallback();
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    /**
     * Handles {@link LoadChordShapesBitmapsEvent} Adds loaded from assets bitmaps to {@link ChordShapesFragmentManager} list
     * of {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape}s images
     * @param loadChordShapesBitmapsEvent {@link LoadChordShapesBitmapsEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadChordShapesBitmapsEvent(LoadChordShapesBitmapsEvent loadChordShapesBitmapsEvent){
        Log.i(TAG, "onLoadChordShapesBitmapsEvent");
        Object eventObject = loadChordShapesBitmapsEvent.getEventObject();
        String chordShapesTableName = null;

        if(eventObject instanceof String){
            chordShapesTableName = (String) eventObject;
        }

        if(chordShapesTableName != null){

            getChordShapesFragmentManager()
                    .addShapesBitmapsToList(ChordsGeneratorApp
                            .getChordsDBProvider()
                            .getChordShapesBitmapsPath(chordShapesTableName));

        }
    }

    /**
     * Handles {@link BitmapsArrayLoadedEvent} Update {@link ChordShapesAdapter} data with
     * {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} bitmaps
     * @param bitmapsArrayLoadedEvent {@link BitmapsArrayLoadedEvent}
     */
    @SuppressWarnings("unchecked")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBitmapsArrayLoadedEvent(BitmapsArrayLoadedEvent bitmapsArrayLoadedEvent){
        Object eventObject = bitmapsArrayLoadedEvent.getEventObject();
        ArrayList<Bitmap> bitmaps = null;

        if(eventObject instanceof ArrayList){
            bitmaps = (ArrayList<Bitmap>) eventObject;
        }

        if(bitmaps != null){

            ChordShapesFragmentCallback chordShapesFragmentCallback = getChordShapesFragmentCallback();

            if(chordShapesFragmentCallback != null){

                chordShapesFragmentCallback.onBitmapsArrayLoaded(bitmaps);

            }

        }
    }

    private ChordShapesFragmentManager getChordShapesFragmentManager() {
        return mChordShapesFragmentManager;
    }

    private ChordShapesFragmentCallback getChordShapesFragmentCallback() {
        return mChordShapesFragmentCallback;
    }
}
