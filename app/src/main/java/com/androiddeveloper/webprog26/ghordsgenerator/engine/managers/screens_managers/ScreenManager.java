package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers;

import android.support.v4.app.FragmentManager;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.ShapesFragmentLoader;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

/**
 * Abstract pattern for app's screens managers
 */

public abstract class ScreenManager implements ShapesFragmentLoader{

    private final FragmentManager mFragmentManager;
    private final int containerViewId;

    ScreenManager(FragmentManager mFragmentManager, int containerViewId) {
        this.mFragmentManager = mFragmentManager;
        this.containerViewId = containerViewId;
    }

    public abstract void onStart();
    public abstract void onStop();


    FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    public int getContainerViewId() {
        return containerViewId;
    }

    @Override
    public void setFragmentWithListOfChordImages(Chord chord) {

    }

    @Override
    public void setPlayableShapeFragment() {

    }
}
