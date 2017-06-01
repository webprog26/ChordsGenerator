package com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers;

import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import java.util.ArrayList;

/**
 * Created by webpr on 01.06.2017.
 */

public class PlayShapeFragmentManager {

    private static final String TAG = "PSFManager";

    private final ChordShape mChordShape;
    private final Fretboard mFretboard;

    public PlayShapeFragmentManager(ChordShape chordShape) {
        this.mChordShape = chordShape;
        this.mFretboard = new Fretboard(chordShape.getMutedStringsHolder());

        initFretboardStringsWithNotes();

        for(int i = 0; i < Fretboard.STRINGS_COUNT; i++){

            GuitarString guitarString = getFretboard().getGuitarString(i);

            if(guitarString.isMuted()){
                Log.i(TAG, guitarString.getTitle() + " is muted and has no note ");
            } else {
                Log.i(TAG, guitarString.getTitle() + " has note " + guitarString.getNote().getNoteTitle());
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

    private Fretboard getFretboard() {
        return mFretboard;
    }
}
