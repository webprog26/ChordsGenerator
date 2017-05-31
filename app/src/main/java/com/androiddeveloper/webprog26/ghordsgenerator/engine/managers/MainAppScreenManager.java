package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.dialogs.ReferenceDialog;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.UIMessageHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

/**
 * Created by webpr on 31.05.2017.
 */

public class MainAppScreenManager {

    private static final String TAG = "MainAppScreenManager";

    private final FragmentManager mFragmentManager;
    private final int containerViewId;
    private final Context mContext;
    private final UIMessageHelper mUiMessageHelper;


    private Chord mCurrentChord;


    public MainAppScreenManager(FragmentManager fragmentManager, int containerViewId, Context context) {
        this.mFragmentManager = fragmentManager;
        this.containerViewId = containerViewId;
        this.mContext = context;

        this.mUiMessageHelper = new UIMessageHelper(this);
    }

    public void sendUiMessage(final Chord chord, final String toChord){
        getUiMessageHelper().sendUiWrongChordMessage(chord.getChordTitle(), toChord);
    }

    public void setFragmentWithListOfChordImages(final Chord chord){
        Log.i(TAG, "should load chord " + chord.getChordTitle());
    }


    public void showReference(String referenceText){
            ReferenceDialog.newInstance(referenceText).show(getFragmentManager(), null);
    }


    public FragmentManager getFragmentManager() {
        return mFragmentManager;
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

    public int getContainerViewId() {
        return containerViewId;
    }

    public UIMessageHelper getUiMessageHelper() {
        return mUiMessageHelper;
    }
}
