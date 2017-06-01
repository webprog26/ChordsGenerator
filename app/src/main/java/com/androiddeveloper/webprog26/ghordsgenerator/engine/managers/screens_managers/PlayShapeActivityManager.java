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
 * Created by webpr on 01.06.2017.
 */

public class PlayShapeActivityManager extends ScreenManager{

    private static final String TAG = "PSActivityManager";

    private static final int SHAPES_PER_CHORD_COUNT = 5;

    private final ChordInfoHolder mChordInfoHolder;
    private final PlayShapeActivityControlsEnabler mPlayShapeActivityControlsEnabler;
    private ArrayList<ChordShape> mChordShapes = new ArrayList<>();

    private int currentShapePosition = 0;

    public PlayShapeActivityManager(FragmentManager mFragmentManager, int containerViewId, ChordInfoHolder mChordInfoHolder, PlayShapeActivityControlsEnabler mPlayShapeActivityControlsEnabler) {
        super(mFragmentManager, containerViewId);
        this.mChordInfoHolder = mChordInfoHolder;
        this.mPlayShapeActivityControlsEnabler = mPlayShapeActivityControlsEnabler;

        changeControlsEnabledState();
    }


    @Override
    public void setPlayableShapeFragment() {
        final ChordShape chordShape = getChordShapes().get(getCurrentShapePosition());

        if(chordShape != null){
            new LoadPlayShapeFragmentCommand(getFragmentManager(), getContainerViewId(), chordShape).execute();
        }
        Log.i(TAG, "load Fragment with shape " + getChordShapes().get(getCurrentShapePosition()));
    }

    public void setNextPlayableShape(){
        if(isNextPlayableShapeEnabled()){
            setCurrentShapePosition(getCurrentShapePosition() + 1);
            setPlayableShapeFragment();

            changeControlsEnabledState();
        }
    }

    public void setPreviousPlayableShape(){
        if(isPreviousPlayableShapeEnabled()){
            setCurrentShapePosition(getCurrentShapePosition() - 1);
            setPlayableShapeFragment();

            changeControlsEnabledState();
        }
    }

    public int getCurrentShapePosition() {
        return currentShapePosition;
    }

    public void setCurrentShapePosition(int currentShapePosition) {
        this.currentShapePosition = currentShapePosition;
    }

    public ChordInfoHolder getChordInfoHolder() {
        return mChordInfoHolder;
    }

    public void loadChordShapesFromLocalDB(){
        EventBus.getDefault().post(new LoadChordShapesFromLocalDBEvent());
    }

    private ArrayList<ChordShape> getChordShapes() {
        return mChordShapes;
    }

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
