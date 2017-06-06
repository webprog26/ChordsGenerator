package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners.NoteClickListener;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import java.util.ArrayList;


/**
 * Manages operations with {@link View} elements of app's virtual "fretboard"
 * in {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.PlayShapesFragment}
 */

public class FretViewsHelper {

    //Index of nnote image in single fret layout
    private static final int NOTE_IMAGE_VIEW_INDEX = 1;

    private final GridLayout fretBoardLayout;
    private final PlayShapeFragmentManager playShapeFragmentManager;
    private final ImageView[] mStringMutedImageViews;
    private final Resources notesResources;

    public FretViewsHelper(final Context context, final GridLayout fretBoardLayout,
                           final PlayShapeFragmentManager playShapeFragmentManager,
                           final ImageView[] stringMutedImageViews) {
        this.fretBoardLayout = fretBoardLayout;
        this.playShapeFragmentManager = playShapeFragmentManager;
        this.mStringMutedImageViews = stringMutedImageViews;
        this.notesResources = context.getResources();
    }

    /**
     * Shows markers of muted "guitar strings" depending on JSON info for the current {@link ChordShape}
     */
    public void initMutedStrings(){
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            Fretboard fretboard = getPlayShapeFragmentManager().getFretboard();

            if(fretboard != null){

                for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

                    GuitarString guitarString = fretboard.getGuitarString((Fretboard.STRINGS_COUNT - 1) - i);

                    if(guitarString != null){

                        if(guitarString.isMuted()){

                            ImageView stringIsMutedImageView = getStringMutedImageViews()[i];

                            if(stringIsMutedImageView != null){

                                if(stringIsMutedImageView.getVisibility() == View.INVISIBLE){

                                    stringIsMutedImageView.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Shows lines of bar if current {@link ChordShape} has one
     * @param index int
     */
    @SuppressWarnings("deprecation")
    private void setBarImage(int index){
        ImageView stringImageView = getStringImageView(getFretLayout(index), NOTE_IMAGE_VIEW_INDEX);
        stringImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        stringImageView.requestLayout();
        stringImageView.setImageDrawable(getNotesResources().getDrawable(R.drawable.bar));
        stringImageView.bringToFront();
    }

    /**
     * Removes note images to avoid device unnecessary memory using when app is not in the foreground state
     */
    public void removeNotesImages(){
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null) {

            ChordShape chordShape = playShapeFragmentManager.getChordShape();

            if(chordShape != null){

                ArrayList<Note> notes = chordShape.getNotes();

                if(notes != null){

                    if(notes.size() > 0){

                        for (final Note note : notes) {

                            if(note != null){

                                removeImageFromSingleNote(note);

                            }
                        }

                    }

                }
            }

        }
    }

    /**
     * Shows current {@link ChordShape} notes images
     */
    public void initNotesImages(){

        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null) {

            ChordShape chordShape = playShapeFragmentManager.getChordShape();

            if(chordShape != null){

                ArrayList<Note> notes = chordShape.getNotes();

                if(notes != null){

                    if(notes.size() > 0){

                            for (final Note note : notes) {

                                if(note != null){

                                    initSingleNoteWithImages(note);

                                }
                            }

                    }

                }
            }

        }
    }

    /**
     * Removes single note image
     * @param note {@link Note}
     */
    private void removeImageFromSingleNote(final Note note){
        if(note.getNoteTitleDrawable() != null){

            ImageView stringImageView = getStringImageView(getFretLayout(note.getNotePlace()), NOTE_IMAGE_VIEW_INDEX);
            stringImageView.setImageDrawable(null);
            stringImageView.setOnClickListener(null);
        }
    }

    /**
     * Shows single note image
     * @param note {@link Note}
     */
    private void initSingleNoteWithImages(final Note note){
        if(note.getNoteTitleDrawable() != null){

            ImageView stringImageView = getStringImageView(getFretLayout(note.getNotePlace()), NOTE_IMAGE_VIEW_INDEX);
            stringImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            stringImageView.requestLayout();
            stringImageView.setImageDrawable(note.getNoteTitleDrawable());
            stringImageView.bringToFront();
            stringImageView.setOnClickListener(new NoteClickListener(note));
        }
    }

    /**
     * Returns the reference to single fret layout
     * @param fretIndex int
     * @return RelativeLayout
     */
    public RelativeLayout getFretLayout(int fretIndex){
        return (RelativeLayout) getFretBoardLayout().getChildAt(fretIndex);
    }

    /**
     * Shows current {@link ChordShape} bar images in a loop
     */
    public void drawBar(){

        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            final int barStartPlace = playShapeFragmentManager.getBarStartPlace();
            final int barEndPlace = playShapeFragmentManager.getBarEndPlace();

            if(barStartPlace != ChordShape.NO_BAR_PLACE
                    && barEndPlace != ChordShape.NO_BAR_PLACE){

                for(int i = barStartPlace; i < barEndPlace; i += 5){
                    setBarImage(i);
                }
            }
        }
    }


    /**
     * Returns a reference to single fret {@link ImageView}.
     * Depending on @param index it could be "guitar string" or note image
     * @param fretLayout {@link RelativeLayout}
     * @param index int
     * @return ImageView
     */
    public ImageView getStringImageView(RelativeLayout fretLayout, int index){
        return (ImageView) fretLayout.getChildAt(index);
    }

    private Resources getNotesResources() {
        return notesResources;
    }

    private ImageView[] getStringMutedImageViews() {
        return mStringMutedImageViews;
    }

    private GridLayout getFretBoardLayout() {
        return fretBoardLayout;
    }

    private PlayShapeFragmentManager getPlayShapeFragmentManager() {
        return playShapeFragmentManager;
    }
}
