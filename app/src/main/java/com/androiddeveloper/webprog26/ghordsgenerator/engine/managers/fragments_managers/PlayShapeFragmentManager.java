package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.FingerIndexDrawableIDHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.NoteBitmapsHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by webpr on 01.06.2017.
 */

public class PlayShapeFragmentManager {

    private static final String TAG = "PSFManager";

    private final ChordShape mChordShape;
    private final Fretboard mFretboard;
    private final Resources mResources;
    private final AssetManager mAssetManager;

    private SoundPool mSoundPool;
    private boolean shouldDrawBar = false;
    private int barStartPlace = ChordShape.NO_BAR_PLACE;
    private int barEndPlace = ChordShape.NO_BAR_PLACE;

    public PlayShapeFragmentManager(ChordShape chordShape, Resources resources, AssetManager assetManager) {
        this.mChordShape = chordShape;
        this.mResources = resources;
        this.mAssetManager = assetManager;
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

                        if(note != null){
                            Log.i(TAG, "finger index " + note.getNoteFingerIndex());

                            if(note.getNoteFingerIndex() != 0){

                                note.setNoteTitleDrawable(resources.getDrawable(NoteBitmapsHelper.getNoteDrawable(note.getNoteTitle())));
                                note.setNoteFingerIndexDrawable(resources.getDrawable(FingerIndexDrawableIDHelper.getFingerIndexDrawableId(note.getNoteFingerIndex())));

                            }
                            initNoteWithSound(note);
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

                        if(note != null){

                            Log.i(TAG, "finger index " + note.getNoteFingerIndex());

                            if(note.getNoteFingerIndex() != 0){

                                if(note.getNoteTitleDrawable() != null){

                                    note.setNoteTitleDrawable(null);

                                }

                                if(note.getNoteFingerIndexDrawable() != null){
                                    note.setNoteFingerIndexDrawable(null);
                                }
                            }


                            removeSoundFromNote(note);
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

                for(int i = 0; i < notes.size(); i++){

                    GuitarString guitarString = fretboard.getGuitarString(i);

                    if(!guitarString.isMuted()){

                        guitarString.setNote(notes.get(i));
                    }
                }
            }

        }
    }

    private void removeSoundFromNote(Note note){

        if(note != null){

            if(note.getNoteSound() != Note.NO_SOUND){

                note.setNoteSound(Note.NO_SOUND);

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
        }
    }

    private void initNoteWithSound(Note note){

        if(note != null){

            SoundPool soundPool = getSoundPool();

            if(soundPool != null){

                try {
                    AssetManager assetManager = getAssetManager();

                    if(assetManager != null){

                        AssetFileDescriptor descriptor;

                        String noteSoundPath = note.getNoteSoundPath();

                        if(noteSoundPath != null){

                            descriptor = assetManager.openFd(noteSoundPath);
                            note.setNoteSound(soundPool.load(descriptor, 0));
                        }
                    }

                } catch (IOException ioe){
                    ioe.printStackTrace();
                }

            }
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

    private SoundPool getSoundPool() {
        return mSoundPool;
    }

    private void setSoundPool(SoundPool soundPool) {
        this.mSoundPool = soundPool;
    }

    public int getBarStartPlace() {
        return barStartPlace;
    }

    private void setBarStartPlace(int barStartPlace) {
        this.barStartPlace = barStartPlace;
    }

    public boolean isShouldDrawBar() {
        return shouldDrawBar;
    }

    private void setShouldDrawBar(boolean shouldDrawBar) {
        this.shouldDrawBar = shouldDrawBar;
    }

    public int getBarEndPlace() {
        return barEndPlace;
    }

    private void setBarEndPlace(int barEndPlace) {
        this.barEndPlace = barEndPlace;
    }

    private Resources getResources() {
        return mResources;
    }

    private AssetManager getAssetManager() {
        return mAssetManager;
    }
}
