package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.commands.LoadChordShapesFragmentCommand;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.dialogs.ReferenceDialog;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.ChordSecondTitleHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.ShapeTableNameHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.UIMessageHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

/**
 * Manages {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.ChordShapesFragment}
 * loading and {@link ReferenceDialog} showing
 */

public class MainAppScreenManager extends ScreenManager{

    private static final String TAG = "MainAppScreenManager";


    private final Context mContext;
    private final UIMessageHelper mUiMessageHelper;
    private final ShapeTableNameHelper mShapeTableNameHelper;
    private final ChordSecondTitleHelper mChordSecondTitleHelper;

    private Chord mCurrentChord;

    public MainAppScreenManager(FragmentManager mFragmentManager, int containerViewId, Context context) {
        super(mFragmentManager, containerViewId);
        this.mContext = context;

        this.mUiMessageHelper = new UIMessageHelper(this);
        this.mShapeTableNameHelper = new ShapeTableNameHelper(context.getResources());
        this.mChordSecondTitleHelper = new ChordSecondTitleHelper(context.getResources());
    }


    public void sendUiMessage(final Chord chord, final String toChord){
        getUiMessageHelper().sendUiWrongChordMessage(chord.getChordTitle(), toChord);
    }

    @Override
    public void setFragmentWithListOfChordImages(Chord chord) {
        new LoadChordShapesFragmentCommand(getFragmentManager(),
                getContainerViewId(),
                getShapeTableNameHelper().getChordShapesTableName(chord.getChordTitle())).execute();
        Log.i(TAG, "should load chord " + chord.getChordTitle());
    }

    public void showReference(String referenceText){
            ReferenceDialog.newInstance(referenceText).show(getFragmentManager(), null);
    }

    public Chord getCurrentChord() {
        return mCurrentChord;
    }

    public void setCurrentChord(Chord mCurrentChord) {
        this.mCurrentChord = mCurrentChord;
    }

    public Context getContext() {
        return mContext;
    }

    private UIMessageHelper getUiMessageHelper() {
        return mUiMessageHelper;
    }

    public ShapeTableNameHelper getShapeTableNameHelper() {
        return mShapeTableNameHelper;
    }

    public ChordSecondTitleHelper getChordSecondTitleHelper() {
        return mChordSecondTitleHelper;
    }
}
