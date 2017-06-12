package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.LoadPlayShapeFragmentCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordShapesListReadyEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesFromLocalDBEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.PlayShapeActivityControlsEnabler;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordInfoHolder;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Manages {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.PlayShapesFragment} loading and
 * {@link com.androiddeveloper.webprog26.ghordsgenerator.PlayShapeActivity} control buttons state, forward and backward current chord chapes changes
 */

public class PlayShapeActivityManager extends ScreenManager{

    private static final String TAG = "PSActivityManager";

    //For now every com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord has 5 shapes
    private static final int SHAPES_PER_CHORD_COUNT = 5;

    private final ChordInfoHolder mChordInfoHolder;
    private final PlayShapeActivityControlsEnabler mPlayShapeActivityControlsEnabler;
    private ArrayList<ChordShape> mChordShapes = new ArrayList<>();

    //by default show first ChordShape
    private int currentShapePosition = 0;

    public PlayShapeActivityManager(FragmentManager mFragmentManager, int containerViewId, ChordInfoHolder mChordInfoHolder, PlayShapeActivityControlsEnabler mPlayShapeActivityControlsEnabler) {
        super(mFragmentManager, containerViewId);
        this.mChordInfoHolder = mChordInfoHolder;
        this.currentShapePosition = mChordInfoHolder.getClickedShapePosition();
        this.mPlayShapeActivityControlsEnabler = mPlayShapeActivityControlsEnabler;

        changeControlsEnabledState();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPlayableShapeFragment() {
        if(getChordShapes().size() > 0){
            final ChordShape chordShape = getChordShapes().get(getCurrentShapePosition());

            if(chordShape != null){
                new LoadPlayShapeFragmentCommand(getFragmentManager(), getContainerViewId(), chordShape).execute();
            }
            Log.i(TAG, "load Fragment with shape " + getChordShapes().get(getCurrentShapePosition()));
        }
    }

    /**
     * Forward to next {@link ChordShape}
     */
    public void setNextPlayableShape(){
        if(isNextPlayableShapeEnabled()){
            setCurrentShapePosition(getCurrentShapePosition() + 1);
            setPlayableShapeFragment();

            changeControlsEnabledState();
        }
    }

    /**
     * Backward to previous {@link ChordShape}
     */
    public void setPreviousPlayableShape(){
        if(isPreviousPlayableShapeEnabled()){
            setCurrentShapePosition(getCurrentShapePosition() - 1);
            setPlayableShapeFragment();

            changeControlsEnabledState();
        }
    }

    private int getCurrentShapePosition() {
        return currentShapePosition;
    }

    private void setCurrentShapePosition(int currentShapePosition) {
        this.currentShapePosition = currentShapePosition;
    }

    public ChordInfoHolder getChordInfoHolder() {
        return mChordInfoHolder;
    }

    /**
     * Start loading chord shapes from local {@link android.database.sqlite.SQLiteDatabase}
     */
    public void loadChordShapesFromLocalDB(){
        Log.i(TAG, "loadChordShapesFromLocalDB");
        EventBus.getDefault().post(new LoadChordShapesFromLocalDBEvent());
    }

    private ArrayList<ChordShape> getChordShapes() {
        return mChordShapes;
    }

    /**
     * Sets loaded from {@link android.database.sqlite.SQLiteDatabase} to {@link ArrayList}
     * to give user possibility to switch beetween them
     * @param chordShapes {@link ArrayList}
     */
    public void setChordShapes(ArrayList<ChordShape> chordShapes) {
        this.mChordShapes = chordShapes;
        EventBus.getDefault().post(new ChordShapesListReadyEvent());
    }

    private boolean isNextPlayableShapeEnabled(){
        return getCurrentShapePosition() < SHAPES_PER_CHORD_COUNT;
    }

    private void changeControlsEnabledState(){
        getPlayShapeActivityControlsEnabler().setNextPlayableShapeButtonEnabled(isNextPlayableShapeEnabled());
        getPlayShapeActivityControlsEnabler().setPreviousPlayableShapeButtonEnabled(isPreviousPlayableShapeEnabled());
    }

    private boolean isPreviousPlayableShapeEnabled(){
        return getCurrentShapePosition() > 0;
    }

    private PlayShapeActivityControlsEnabler getPlayShapeActivityControlsEnabler() {
        return mPlayShapeActivityControlsEnabler;
    }
}
