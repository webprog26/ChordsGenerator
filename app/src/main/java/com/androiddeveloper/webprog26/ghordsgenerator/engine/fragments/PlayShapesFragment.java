package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();

        if(args != null){

            final ChordShape chordShape = (ChordShape) args.getSerializable(CHORD_SHAPE_TO_PLAY);

            if(chordShape != null){

                mPlayShapeFragmentManager = new PlayShapeFragmentManager(chordShape, getActivity().getResources());
                mPlayShapeFragmentManager.initNotesWithDrawables();
                Log.i(TAG, "in PlayShapesFragment chord shape is " + chordShape.toString());
            }
        }
    }

    @Override
    public void onPause() {
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();
        if(playShapeFragmentManager != null){
            playShapeFragmentManager.removeDrawablesFromNotes();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private PlayShapeFragmentManager getPlayShapeFragmentManager() {
        return mPlayShapeFragmentManager;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onInitNotesWithDrawablesEvent(InitNotesWithDrawablesEvent initNotesWithDrawablesEvent){
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            playShapeFragmentManager.setDrawablesToNotes();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotesInitializedWithDrawablesEvent(NotesInitializedWithDrawablesEvent notesInitializedWithDrawablesEvent){

        for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

            GuitarString guitarString = getPlayShapeFragmentManager().getFretboard().getGuitarString(i);

            if(guitarString != null){

                if(!guitarString.isMuted()){

                    Note note = guitarString.getNote();

                    if(note != null){

                        if(note.getNoteTitleDrawable() != null){

                            Log.i(TAG, "note.getNoteTitleDrawable(): " + note.getNoteTitleDrawable().toString());
                        }

                        if(note.getNoteFingerIndexDrawable() != null){

                            Log.i(TAG, "note.getNoteFingerIndexDrawable().toString(): " + note.getNoteFingerIndexDrawable().toString());
                        }

                    }
                }
            }

        }
    }
}
