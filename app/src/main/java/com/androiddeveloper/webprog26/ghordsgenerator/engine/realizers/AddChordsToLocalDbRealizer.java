package com.androiddeveloper.webprog26.ghordsgenerator.engine.realizers;

import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.chords_generator_app.ChordsGeneratorApp;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import java.util.ArrayList;

/**
 * Adds {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} instances
 * to loacal {@link android.database.sqlite.SQLiteDatabase}
 */

public class AddChordsToLocalDbRealizer implements Realizer {

    private static final String TAG = "ACTLDRealizer";

    private final ArrayList<Chord> mChords;

    public AddChordsToLocalDbRealizer(ArrayList<Chord> chords) {
        this.mChords = chords;
    }

    @Override
    public void realize() {
        try {
            ChordsGeneratorApp.getChordsDBProvider().insertChordsToDB(getChords());
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }


    private ArrayList<Chord> getChords() {
        return mChords;
    }
}
