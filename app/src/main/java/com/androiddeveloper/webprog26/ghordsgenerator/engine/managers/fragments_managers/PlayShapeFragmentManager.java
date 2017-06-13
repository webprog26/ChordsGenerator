package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers.PlayShapesFragmentEventsHandler;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.NotePlayer;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks.PlayShapeFragmentCallback;
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
 * Manages {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.PlayShapesFragment}
 * operations
 */

public class PlayShapeFragmentManager extends SuperFragmentManager implements NotePlayer{

    private static final String TAG = "PSFManager";

    private final ChordShape mChordShape;
    private final Fretboard mFretboard;
    private final Resources mResources;
    private final AssetManager mAssetManager;
    private final PlayShapeFragmentCallback mPlayShapeFragmentCallback;
    private final PlayShapesFragmentEventsHandler mPlayShapesFragmentEventsHandler;

    private SoundPool mSoundPool;
    private boolean shouldDrawBar = false;
    private int barStartPlace = ChordShape.NO_BAR_PLACE;
    private int barEndPlace = ChordShape.NO_BAR_PLACE;

    public PlayShapeFragmentManager(ChordShape chordShape, Resources resources, AssetManager assetManager, PlayShapeFragmentCallback playShapeFragmentCallback) {
        this.mChordShape = chordShape;
        this.mResources = resources;
        this.mAssetManager = assetManager;
        this.mFretboard = new Fretboard(chordShape.getMutedStringsHolder());
        this.mPlayShapeFragmentCallback = playShapeFragmentCallback;

        this.mPlayShapesFragmentEventsHandler = new PlayShapesFragmentEventsHandler(this);

        initBarPlaces();

//Uncomment to see GuitarString loading logs
//*********************************************************************************************************************************
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
//*********************************************************************************************************************************
    }

    @Override
    public void onStart() {
        getPlayShapesFragmentEventsHandler().subscribe();
    }

    @Override
    public void onStop() {
        getPlayShapesFragmentEventsHandler().unsubscribe();
    }

    /**
     * Starts initialization notes with drawables and sounds
     */
    public void initNotesWithDrawablesAndSounds(){
        EventBus.getDefault().post(new InitNotesWithDrawablesEvent());
    }

    /**
     * Sets drawables and sounds to current {@link ChordShape} notes
     */
    @SuppressWarnings("deprecation")
    public void setDrawablesAndSoundsToNotes(){
        ChordShape chordShape = getChordShape();

        if(chordShape != null){

            ArrayList<Note> notes = chordShape.getNotes();

            if(notes != null){

                if(notes.size() > 0){

                    Resources resources = getResources();

                    for(Note note: notes){

                        if(note != null){
                            Log.i(TAG, "finger index " + note.getNoteFingerIndex());

                            if(note.getNoteFingerIndex() != 0){//not a "free string" note

                                note.setNoteTitleDrawable(resources.getDrawable(NoteBitmapsHelper.getNoteDrawable(note.getNoteTitle())));
                                note.setNoteFingerIndexDrawable(resources.getDrawable(FingerIndexDrawableIDHelper.getFingerIndexDrawableId(note.getNoteFingerIndex())));

                            }
                            //inits notes of current ChordShape with their sounds predefined in JSON
                            initNoteWithSound(note);
                        }
                    }
                }
            }
        }
        //inits "fretboard strings" with notes
        initFretboardStringsWithNotes();

        //Notifying PlayShapesFragment that notes and "guitar strings" have been initialized successfully
        EventBus.getDefault().post(new NotesInitializedWithDrawablesEvent());
    }

    /**
     * Starts removing drawables and sounds from notes to avoid device unnecessary memory using
     * when app is not in the foreground state
     */
    @SuppressWarnings("deprecation")
    public void removeDrawablesAndSoundsFromNotes(){
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

    /**
     * Inits "fretboard strings" with notes
     */
    private void initFretboardStringsWithNotes(){

        ArrayList<Note> notes = getChordShape().getNotes();

        if(notes != null){

            if(notes.size() > 0){

                for(Note note: notes){
                    Log.i(TAG, note.getNoteTitle());
                }

                Fretboard fretboard = getFretboard();

                int noteIndex = 0;

                for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

                    GuitarString guitarString = fretboard.getGuitarString(i);

                    if(!guitarString.isMuted()){

                        guitarString.setNote(notes.get(noteIndex));
                        noteIndex++;
                    }
                }
            }

        }
    }

    /**
     * Removes sound from single {@link Note}
     * @param note {@link Note}
     */
    private void removeSoundFromNote(Note note){

        if(note != null){

            if(note.getNoteSound() != Note.NO_SOUND){

                note.setNoteSound(Note.NO_SOUND);

            }

        }
    }

    /**
     * Inits {@link ChordShape} bar depending on JSON info if current shape has one
     */
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


    /**
     * Release resources using by {@link SoundPool}
     */
    public void releaseSoundPool(){
        SoundPool soundPool = getSoundPool();
        if(soundPool != null){
            soundPool.release();
            setSoundPool(null);
        }
    }

    /**
     * Creates {@link SoundPool} if needed
     */
    @SuppressWarnings("deprecation")
    public void createSoundPool(){
        SoundPool soundPool = getSoundPool();
        if(soundPool == null){
            setSoundPool(new SoundPool(10, AudioManager.STREAM_MUSIC,0));
        }
    }

    /**
     * Inits single note with sound
     * @param note {@link Note}
     */
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

    /**
     * Inits current String with it's coordinates: left side (startX), right side (endX)
     * @param startX float
     * @param endX float
     * @param index int
     */
    public void setStringsCoordinates(float startX, float endX, int index){
        Log.i(TAG, "setStringsCoordinates " + "start " + startX + " end " + endX + " index " + index);
        GuitarString guitarString = getFretboard().getGuitarString(index);
        guitarString.setStartX(startX);
        guitarString.setEndX(endX);
    }

    /**
     * Inits "guitar string" playable height depending on it's "pressing" position. It means that
     * "free" "guitar string" will accept touches at any place of "guitar fretboard", but the pressed
     * one will not accept touches behind the place it was pressed, in our case behind the place note is located
     * @param playableY float
     * @param index int
     */
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

    public PlayShapeFragmentCallback getPlayShapeFragmentCallback() {
        return mPlayShapeFragmentCallback;
    }

    private PlayShapesFragmentEventsHandler getPlayShapesFragmentEventsHandler() {
        return mPlayShapesFragmentEventsHandler;
    }

    @Override
    public void playNote(int noteSound) {
        Log.i(TAG, "noteSound: " + noteSound);
        SoundPool soundPool = getSoundPool();

        if(soundPool != null){
            soundPool.play(noteSound, 1, 1, 0, 0, 1);
        }
    }
}
