package com.androiddeveloper.webprog26.ghordsgenerator.engine.commands;

import android.support.v4.app.FragmentManager;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.ChordShapesFragment;

/**
 * Created by webpr on 01.06.2017.
 */

public class LoadChordShapesFragmentCommand extends LoadFragmentCommand {


    private static final String CHORD_SHAPES_FRAGMENT_TAG = "chord_shapes_fragment_tag";

    private final String mChordShapesTitle;

    public LoadChordShapesFragmentCommand(FragmentManager fragmentManager, int containerViewId, String chordShapesTitle) {
        super(fragmentManager, containerViewId);
        this.mChordShapesTitle = chordShapesTitle;
    }

    @Override
    public void execute() {

        FragmentManager fragmentManager = getFragmentManager();

        ChordShapesFragment chordShapesFragment = (ChordShapesFragment) fragmentManager.findFragmentByTag(CHORD_SHAPES_FRAGMENT_TAG);

        if(chordShapesFragment != null){
            fragmentManager.beginTransaction().replace(getContainerViewId(), ChordShapesFragment.newInstance(getChordShapesTitle()), CHORD_SHAPES_FRAGMENT_TAG).commit();
        } else {
            fragmentManager.beginTransaction().add(getContainerViewId(), ChordShapesFragment.newInstance(getChordShapesTitle()), CHORD_SHAPES_FRAGMENT_TAG).commit();
        }
    }

    private String getChordShapesTitle() {
        return mChordShapesTitle;
    }
}
