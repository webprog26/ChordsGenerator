package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.FretNumbersTransformHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by webpr on 31.05.2017.
 */

public class PlayShapesFragment extends Fragment {

    private static final String TAG = "PlayShapesFragment";

    public static final String CHORD_SHAPE_TO_PLAY = "chord_shape_to_play";

    private PlayShapeFragmentManager mPlayShapeFragmentManager;

    @BindView(R.id.gl_fret)
    GridLayout mGlFret;
    @BindView(R.id.tv_fret_to_start)
    TextView mTvFretToStart;
    @BindViews({R.id.first_string_muted,
            R.id.second_string_muted,
            R.id.third_string_muted,
            R.id.fourth_string_muted,
            R.id.fifth_string_muted,
            R.id.sixth_string_muted})
    ImageView[] mStringMutedImageViews;

    private Unbinder unbinder;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_shapes_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();

        if(args != null){

            final ChordShape chordShape = (ChordShape) args.getSerializable(CHORD_SHAPE_TO_PLAY);

            if(chordShape != null){

                mPlayShapeFragmentManager = new PlayShapeFragmentManager(chordShape,
                                                                         getActivity().getResources(),
                                                                         getActivity().getAssets());
                mPlayShapeFragmentManager.createSoundPool();

                mPlayShapeFragmentManager.initNotesWithDrawables();
                Log.i(TAG, "in PlayShapesFragment chord shape is " + chordShape.toString());
            }
        }
    }

    @Override
    public void onPause() {
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();
        if(playShapeFragmentManager != null){
            playShapeFragmentManager.releaseSoundPool();
            playShapeFragmentManager.removeDrawablesFromNotes();

        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        final PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            final ChordShape chordShape = playShapeFragmentManager.getChordShape();

            if(chordShape != null){

                getTvFretToStart().setText(FretNumbersTransformHelper.getFretToStartString(chordShape.getStartFretPosition()));

            }
        }

        for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

            GuitarString guitarString = getPlayShapeFragmentManager().getFretboard().getGuitarString(i);

            if(guitarString != null){

                if(!guitarString.isMuted()){

                    Note note = guitarString.getNote();

                    if(note != null){

                        if(note.getNoteTitleDrawable() != null){

                            Log.i(TAG, "note.getNoteTitleDrawable(): " + note.getNoteTitleDrawable().toString());
                        } else {
                            Log.i(TAG, "note.getNoteTitleDrawable(): null");
                        }

                        if(note.getNoteFingerIndexDrawable() != null){

                            Log.i(TAG, "note.getNoteFingerIndexDrawable().toString(): " + note.getNoteFingerIndexDrawable().toString());
                        } else {
                            Log.i(TAG, "note.getNoteFingerIndexDrawable(): null");
                        }

                        if(note.getNoteSound() != Note.NO_SOUND){
                            Log.i(TAG, "note.getNoteSound(): " + note.getNoteSound());
                        } else {
                            Log.i(TAG, "note got no sound");
                        }
                    } else {
                        Log.i(TAG, "note is null");
                    }
                }
            }

        }
    }

    public TextView getTvFretToStart() {
        return mTvFretToStart;
    }
}
