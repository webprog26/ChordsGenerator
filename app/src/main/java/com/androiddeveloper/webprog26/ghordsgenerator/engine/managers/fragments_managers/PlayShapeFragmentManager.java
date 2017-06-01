package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.FingerIndexDrawableIDHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.NoteBitmapsHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by webpr on 01.06.2017.
 */

public class PlayShapeFragmentManager {

    private static final String TAG = "PSFManager";

    private final ChordShape mChordShape;
    private final Fretboard mFretboard;
    private final Resources mResources;

    private SoundPool mSoundPool;
    private boolean shouldDrawBar = false;
    private int barStartPlace = ChordShape.NO_BAR_PLACE;
    private int barEndPlace = ChordShape.NO_BAR_PLACE;

    public PlayShapeFragmentManager(ChordShape chordShape, Resources resources) {
        this.mChordShape = chordShape;
        this.mResources = resources;
        this.mFretboard = new Fretboard(chordShape.getMutedStringsHolder());

        initBarPlaces();

//        for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){
//
//            GuitarString guitarString = getFretboard().getGuitarString(i);
//
//            if(guitarString.isMuted()){
//                Log.i(TAG, guitarString.getTitle() + " is muted and has no note ");
//            } else {
//                Log.i(TAG, guitarString.getTitle() + " has note " + guitarString.getNote().getNoteTitle());
//            }
//        }

    }

    public void initNotesWithDrawables(){
        EventBus.getDefault().post(new InitNotesWithDrawablesEvent());
    }

    @SuppressWarnings("deprecation")
    public void setDrawablesToNotes(){
        ChordShape chordShape = getChordShape();

        if(chordShape != null){

            ArrayList<Note> notes = chordShape.getNotes();

            if(notes != null){

                if(notes.size() > 0){

                    Resources resources = getResources();

                    for(Note note: notes){
                        Log.i(TAG, "finger index " + note.getNoteFingerIndex());

                        if(note.getNoteFingerIndex() != 0){

                            note.setNoteTitleDrawable(resources.getDrawable(NoteBitmapsHelper.getNoteDrawable(note.getNoteTitle())));
                            note.setNoteFingerIndexDrawable(resources.getDrawable(FingerIndexDrawableIDHelper.getFingerIndexDrawableId(note.getNoteFingerIndex())));

                        }

                    }
                }
            }
        }

        initFretboardStringsWithNotes();

        EventBus.getDefault().post(new NotesInitializedWithDrawablesEvent());
    }

    @SuppressWarnings("deprecation")
    public void removeDrawablesFromNotes(){
        ChordShape chordShape = getChordShape();

        if(chordShape != null){

            ArrayList<Note> notes = chordShape.getNotes();

            if(notes != null){

                if(notes.size() > 0){

                    for(Note note: notes){
                        Log.i(TAG, "finger index " + note.getNoteFingerIndex());

                        if(note.getNoteFingerIndex() != 0){

                            note.setNoteTitleDrawable(null);
                            note.setNoteFingerIndexDrawable(null);

                        }

                    }
                }
            }
        }
    }


    public ChordShape getChordShape() {
        return mChordShape;
    }

    private void initFretboardStringsWithNotes(){

        ArrayList<Note> notes = getChordShape().getNotes();

        if(notes != null){

            if(notes.size() > 0){

                Fretboard fretboard = getFretboard();

                for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

                    GuitarString guitarString = fretboard.getGuitarString(i);

                    if(!guitarString.isMuted()){

                        guitarString.setNote(notes.get(i));
                    }
                }
            }

        }
    }

    private void initBarPlaces(){
        ChordShape chordShape = getChordShape();

        if(chordShape != null){

            if(chordShape.isHasBar()){

                setShouldDrawBar(true);

                int barStartPlace = chordShape.getStartBarPlace();
                int barEndPlace = chordShape.getEndBarPlace();


                if(barStartPlace != ChordShape.NO_BAR_PLACE){
                    setBarStartPlace(barStartPlace);
                }

                if(barEndPlace != ChordShape.NO_BAR_PLACE){
                    setBarEndPlace(barEndPlace);
                }

            }
        }
    }


    public void releaseSoundPool(){
        SoundPool soundPool = getSoundPool();
        if(soundPool != null){
            soundPool.release();
            setSoundPool(null);
        }
    }

    @SuppressWarnings("deprecation")
    public void createSoundPool(){
        SoundPool soundPool = getSoundPool();
        if(soundPool == null){
            setSoundPool(new SoundPool(10, AudioManager.STREAM_MUSIC,0));
//            EventBus.getDefault().post(new LoadNotesSoundsEvent());
        }
    }

    public void setStringsCoordinates(float startX, float endX, int index){
        GuitarString guitarString = getFretboard().getGuitarString(index);
        guitarString.setStartX(startX);
        guitarString.setEndX(endX);
    }

    public void setStringPlayableY(float playableY, int index){
        GuitarString guitarString = getFretboard().getGuitarString(index);
        guitarString.setPlayableY(playableY);
    }

    public Fretboard getFretboard() {
        return mFretboard;
    }

    public SoundPool getSoundPool() {
        return mSoundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.mSoundPool = soundPool;
    }

    public int getBarStartPlace() {
        return barStartPlace;
    }

    public void setBarStartPlace(int barStartPlace) {
        this.barStartPlace = barStartPlace;
    }

    public boolean isShouldDrawBar() {
        return shouldDrawBar;
    }

    public void setShouldDrawBar(boolean shouldDrawBar) {
        this.shouldDrawBar = shouldDrawBar;
    }

    public int getBarEndPlace() {
        return barEndPlace;
    }

    public void setBarEndPlace(int barEndPlace) {
        this.barEndPlace = barEndPlace;
    }

    public Resources getResources() {
        return mResources;
    }
}
