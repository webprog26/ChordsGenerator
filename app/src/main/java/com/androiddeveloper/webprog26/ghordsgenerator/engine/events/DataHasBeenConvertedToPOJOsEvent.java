package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import java.util.ArrayList;

/**
 * CNotifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity}
 * that JSON data has benn successfully converted to POJOs
 */

public class DataHasBeenConvertedToPOJOsEvent {

    private final ArrayList<Chord> mChords;

    public DataHasBeenConvertedToPOJOsEvent(ArrayList<Chord> chords) {
        this.mChords = chords;
    }

    public ArrayList<Chord> getChords() {
        return mChords;
    }
}
