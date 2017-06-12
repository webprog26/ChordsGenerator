package com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers;

import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.chords_generator_app.ChordsGeneratorApp;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordShapesListReadyEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesFromLocalDBEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers.PlayShapeActivityManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by webpr on 12.06.2017.
 */

public class PlayAppEventsHandler extends AppEventsHandler {

    private static final String TAG = "PlayAppEventsHandler";

    private final PlayShapeActivityManager mPlayShapeActivityManager;

    public PlayAppEventsHandler(PlayShapeActivityManager playShapeActivityManager) {
        this.mPlayShapeActivityManager = playShapeActivityManager;
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
     * Handles {@link LoadChordShapesFromLocalDBEvent}. Adds loaded from local {@link android.database.sqlite.SQLiteDatabase}
     * chord shapes to the {@link java.util.ArrayList} of {@link PlayShapeActivityManager}
     * @param loadChordShapesFromLocalDBEvent {@link LoadChordShapesFromLocalDBEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadChordShapesFromLocalDBEvent(LoadChordShapesFromLocalDBEvent loadChordShapesFromLocalDBEvent){
        PlayShapeActivityManager playShapeActivityManager = getPlayShapeActivityManager();

        if(playShapeActivityManager != null){
            playShapeActivityManager.setChordShapes(ChordsGeneratorApp.getChordsDBProvider().getChordShapes(
                    getPlayShapeActivityManager().getChordInfoHolder().getChordShapesTableName()));
        }
    }

    /**
     * Hanldes {@link ChordShapesListReadyEvent}. Shows {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.PlayShapesFragment}
     * @param chordShapesListReadyEvent {@link ChordShapesListReadyEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChordShapesListReadyEvent(ChordShapesListReadyEvent chordShapesListReadyEvent){
        Log.i(TAG, "onChordShapesListReadyEvent");
        PlayShapeActivityManager playShapeActivityManager = getPlayShapeActivityManager();

        if(playShapeActivityManager != null){
            playShapeActivityManager.setPlayableShapeFragment();
        }
    }

    private PlayShapeActivityManager getPlayShapeActivityManager() {
        return mPlayShapeActivityManager;
    }
}
