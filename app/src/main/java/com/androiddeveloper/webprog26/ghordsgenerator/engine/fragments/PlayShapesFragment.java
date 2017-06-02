package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners.FretTouchListener;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.FretNumbersTransformHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.FretViewsHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
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
 * Fragment, that shows separate {@link ChordShape} on a fretboard and lets user to play it by touching screen fretboard
 */

public class PlayShapesFragment extends Fragment {

    private static final String TAG = "PlayShapesFragment";

    //Tag to receive chosen ChordShape as an argument
    public static final String CHORD_SHAPE_TO_PLAY = "chord_shape_to_play";

    private static final int FIRST_STRING_LAST_FRET_INDEX = 4;
    private static final int FRETS_PER_STRING_NUMBER = 5;

    private static final int STRING_IMAGE_VIEW_INDEX = 0;

    private static final int TRASH_HOLD_SIZE = 20;

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

    private FretViewsHelper mFretViewsHelper;
    private Unbinder unbinder;

    /**
     * Creates new {@link PlayShapesFragment} instance and fills it with a chosen {@link ChordShape}
     * @param chordShapeToPlay {@link ChordShape}
     * @return PlayShapesFragment
     */
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

                //Creating PlayShapeFragmentManager instance
                mPlayShapeFragmentManager = new PlayShapeFragmentManager(chordShape,
                                                                         getActivity().getResources(),
                                                                         getActivity().getAssets());
                //Creating android.media.SoundPool to play guitar sounds
                mPlayShapeFragmentManager.createSoundPool();

                //initializing notes with drawables
                mPlayShapeFragmentManager.initNotesWithDrawables();
                Log.i(TAG, "in PlayShapesFragment chord shape is " + chordShape.toString());

               if(mFretViewsHelper == null){
                   //Creating new FretViewsHelper instance
                   mFretViewsHelper = new FretViewsHelper(getActivity(), getFretboardGridLayout(), mPlayShapeFragmentManager, getStringMutedImageViews());
               }
            }
        }
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();
        if(playShapeFragmentManager != null){
            //Releasing SoundPool resources
            playShapeFragmentManager.releaseSoundPool();
            //Removing notes drawables
            playShapeFragmentManager.removeDrawablesFromNotes();

            if(mFretViewsHelper != null){
                //Removing notes images
                mFretViewsHelper.removeNotesImages();
                mFretViewsHelper = null;
            }

            mPlayShapeFragmentManager = null;
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

    /**
     * Handles {@link InitNotesWithDrawablesEvent} Calls {@link PlayShapeFragmentManager} to add drawables
     * to the {@link ChordShape} notes
     * @param initNotesWithDrawablesEvent
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onInitNotesWithDrawablesEvent(InitNotesWithDrawablesEvent initNotesWithDrawablesEvent){
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            playShapeFragmentManager.setDrawablesToNotes();

        }
    }

    /**
     * Actually main method of the class. Handles {@link NotesInitializedWithDrawablesEvent} which
     * means that notes are completely ready to be showed, played and (or) clicked
     * @param notesInitializedWithDrawablesEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotesInitializedWithDrawablesEvent(NotesInitializedWithDrawablesEvent notesInitializedWithDrawablesEvent){

        final PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            final ChordShape chordShape = playShapeFragmentManager.getChordShape();

            if(chordShape != null){

                //Show ChordShape's start fret position number
                getTvFretToStart().setText(FretNumbersTransformHelper.getFretToStartString(chordShape.getStartFretPosition()));

                if(mFretViewsHelper != null){

                   //Show muted strings markers
                   mFretViewsHelper.initMutedStrings();

                   //Initializing notes with images
                   mFretViewsHelper.initNotesImages();

                if(playShapeFragmentManager.isShouldDrawBar()){
                        //Draw bar images
                        mFretViewsHelper.drawBar();
                    }

                }

                final GridLayout fretboardGridLayout = getFretboardGridLayout();

                if(fretboardGridLayout != null){

                    //This callback is used to calculate "fretboard strings" coordinates to handle user touches
                    //hence they'll play sound
                    fretboardGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {

                        fretboardGridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        if(mPlayShapeFragmentManager != null){
                            int stringIndex = 0;

                                if(mFretViewsHelper != null){

                                    //Calculating and remembering "fretboard strings" tsrt end end X-coordinates
                                    for(int i = (fretboardGridLayout.getChildCount() - 1); i >= FIRST_STRING_LAST_FRET_INDEX ; i-= FRETS_PER_STRING_NUMBER){
                                        RelativeLayout fretRelativeLayout = mFretViewsHelper.getFretLayout(i);

                                        if(fretRelativeLayout != null){

                                            ImageView stringImageView = mFretViewsHelper.getStringImageView(fretRelativeLayout, STRING_IMAGE_VIEW_INDEX);

                                            if(stringImageView != null){
                                                //Write calculated coordinates to "fretboard strings" hence they will "know"
                                                //when to play
                                                playShapeFragmentManager.setStringsCoordinates((fretRelativeLayout.getX() + stringImageView.getX() - TRASH_HOLD_SIZE),
                                                        (fretRelativeLayout.getX() + stringImageView.getX()) + stringImageView.getWidth() + TRASH_HOLD_SIZE, stringIndex);
                                                stringIndex++;
                                            }
                                        }
                                    }
                            }

                            //Calculating notes Y-coordinates on each "string" to avoid playing sound back of the note
                            //which is impossible in real life
                            Fretboard fretboard = playShapeFragmentManager.getFretboard();

                            if(fretboard != null){

                                for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

                                    GuitarString currentGuitarString = fretboard.getGuitarString(i);

                                    if(currentGuitarString != null){

                                        Note currentNote = currentGuitarString.getNote();

                                        if(currentNote != null){

                                            float stringPlayableY;

                                            if(currentNote.getNoteFret() == 0){
                                                stringPlayableY = 0;
                                            } else {
                                                stringPlayableY = (float) fretboardGridLayout.getChildAt(currentNote.getNoteFret() - 1).getBottom();
                                            }


                                            Log.i(TAG, currentGuitarString.getTitle() + " with note " + currentGuitarString.getNote().getNoteTitle()
                                                    + " stringPlayableY " + stringPlayableY);
                                            playShapeFragmentManager.setStringPlayableY(stringPlayableY, i);

                                        } else {
                                            Log.i(TAG, "currentNote is null at " + currentGuitarString.getTitle());
                                        }
                                    } else {
                                        Log.i(TAG, "currentGuitarString is null");
                                    }
                                }
                            } else {
                                Log.i(TAG, "fretboard is null");
                            }
                            //At this time our "guitar fretboard" is ready to interact with user touches
                            catchFretboardTouches();
                        }
                            fretboardGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
                        }
                    });
                }

            }
        }
//*********************************************************************************************************************************
          //Uncomment to see detailed logs of the current ChordShape

//        for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){
//
//            GuitarString guitarString = getPlayShapeFragmentManager().getFretboard().getGuitarString(i);
//
//            if(guitarString != null){
//
//                if(!guitarString.isMuted()){
//
//                    Note note = guitarString.getNote();
//
//                    if(note != null){
//
//                        if(note.getNoteTitleDrawable() != null){
//
//                            Log.i(TAG, "note.getNoteTitleDrawable(): " + note.getNoteTitleDrawable().toString());
//                        } else {
//                            Log.i(TAG, "note.getNoteTitleDrawable(): null");
//                        }
//
//                        if(note.getNoteFingerIndexDrawable() != null){
//
//                            Log.i(TAG, "note.getNoteFingerIndexDrawable().toString(): " + note.getNoteFingerIndexDrawable().toString());
//                        } else {
//                            Log.i(TAG, "note.getNoteFingerIndexDrawable(): null");
//                        }
//
//                        if(note.getNoteSound() != Note.NO_SOUND){
//                            Log.i(TAG, "note.getNoteSound(): " + note.getNoteSound());
//                        } else {
//                            Log.i(TAG, "note got no sound");
//                        }
//                    } else {
//                        Log.i(TAG, "note is null");
//                    }
//                }
//            }
//
//        }
//*********************************************************************************************************************************
    }

    /**
     * Adds FretTouchListener to the fretboardGridLayout
     */
    private void catchFretboardTouches(){
        getFretboardGridLayout().setOnTouchListener(new FretTouchListener(getPlayShapeFragmentManager()));
    }

    public GridLayout getFretboardGridLayout() {
        return mGlFret;
    }

    public TextView getTvFretToStart() {
        return mTvFretToStart;
    }

    public ImageView[] getStringMutedImageViews() {
        return mStringMutedImageViews;
    }
}
