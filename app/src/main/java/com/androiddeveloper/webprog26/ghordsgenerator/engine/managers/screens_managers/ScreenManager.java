package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers;

import android.support.v4.app.FragmentManager;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.ShapesFragmentLoader;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

/**
 * Created by webpr on 01.06.2017.
 */

public abstract class ScreenManager implements ShapesFragmentLoader{

    private final FragmentManager mFragmentManager;
    private final int containerViewId;

    public ScreenManager(FragmentManager mFragmentManager, int containerViewId) {
        this.mFragmentManager = mFragmentManager;
        this.containerViewId = containerViewId;
    }

    public FragmentManager getFragmentManager() {
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
