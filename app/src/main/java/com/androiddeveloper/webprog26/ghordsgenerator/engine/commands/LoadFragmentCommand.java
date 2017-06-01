package com.androiddeveloper.webprog26.ghordsgenerator.engine.commands;

import android.support.v4.app.FragmentManager;

/**
 * Created by webpr on 01.06.2017.
 */

public abstract class LoadFragmentCommand implements Command {

    private final FragmentManager mFragmentManager;
    private final int mContainerViewId;

    public LoadFragmentCommand(FragmentManager fragmentManager, int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
    }

    protected FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    protected int getContainerViewId() {
        return mContainerViewId;
    }
}
