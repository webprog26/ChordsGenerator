package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;

/**
 * Created by webpr on 31.05.2017.
 */

public class PlayShapesFragment extends Fragment {

    private static final String TAG = "PlayShapesFragment";

    public static final String CHORD_SHAPE_TO_PLAY = "chord_shape_to_play";

    private PlayShapeFragmentManager mPlayShapeFragmentManager;

    public static PlayShapesFragment newInstance(ChordShape chordShapeToPlay){
        Bundle args = new Bundle();
        args.putSerializable(CHORD_SHAPE_TO_PLAY, chordShapeToPlay);

        PlayShapesFragment playShapesFragment = new PlayShapesFragment();
        playShapesFragment.setArguments(args);

        return playShapesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if(args != null){

            final ChordShape chordShape = (ChordShape) args.getSerializable(CHORD_SHAPE_TO_PLAY);

            if(chordShape != null){

                mPlayShapeFragmentManager = new PlayShapeFragmentManager(chordShape);

                Log.i(TAG, "in PlayShapesFragment chord shape is " + chordShape.toString());
            }
        }
    }

    private PlayShapeFragmentManager getPlayShapeFragmentManager() {
        return mPlayShapeFragmentManager;
    }
}
