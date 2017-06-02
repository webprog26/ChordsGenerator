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
 * Created by webpr on 30.05.2017.
 */

public class FretViewsHelper {

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

    @SuppressWarnings("deprecation")
    private void setBarImage(int index){
        ImageView stringImageView = getStringImageView(getFretLayout(index), NOTE_IMAGE_VIEW_INDEX);
        stringImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        stringImageView.requestLayout();
        stringImageView.setImageDrawable(getNotesResources().getDrawable(R.drawable.bar));
        stringImageView.bringToFront();
    }

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

    private void removeImageFromSingleNote(final Note note){
        if(note.getNoteTitleDrawable() != null){

            ImageView stringImageView = getStringImageView(getFretLayout(note.getNotePlace()), NOTE_IMAGE_VIEW_INDEX);
            stringImageView.setImageDrawable(null);
            stringImageView.setOnClickListener(null);
        }
    }

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

    public RelativeLayout getFretLayout(int fretIndex){
        return (RelativeLayout) getFretBoardLayout().getChildAt(fretIndex);
    }

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
