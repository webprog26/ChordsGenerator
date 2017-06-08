package com.androiddeveloper.webprog26.ghordsgenerator.engine.commands;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.ChordShapesFragment;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.PlayShapesFragment;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;

/**
 * Loads play sgape fragment
 */

public class LoadPlayShapeFragmentCommand extends LoadFragmentCommand {

    private static final String TAG = "LPSFCommand";

    private final static String PLAY_SHAPE_FRAGMENT_TAG = "play_shape_fragment_tag";

    private final ChordShape mChordShape;

    public LoadPlayShapeFragmentCommand(FragmentManager fragmentManager, int containerViewId, ChordShape chordShape) {
        super(fragmentManager, containerViewId);
        this.mChordShape = chordShape;
    }

    @Override
    public void execute() {

        FragmentManager fragmentManager = getFragmentManager();

        PlayShapesFragment playShapesFragment = (PlayShapesFragment) fragmentManager.findFragmentByTag(PLAY_SHAPE_FRAGMENT_TAG);

        if(playShapesFragment != null){
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(getContainerViewId(), PlayShapesFragment.newInstance(getChordShape()), PLAY_SHAPE_FRAGMENT_TAG).commit();
        } else {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).add(getContainerViewId(), PlayShapesFragment.newInstance(getChordShape()), PLAY_SHAPE_FRAGMENT_TAG).commit();
        }
    }

    private ChordShape getChordShape() {
        return mChordShape;
    }
}
