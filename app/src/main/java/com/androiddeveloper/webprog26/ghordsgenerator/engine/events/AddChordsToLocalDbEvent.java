package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import java.util.ArrayList;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity} that chords should be added
 * to local database
 */

public class AddChordsToLocalDbEvent {

    private final ArrayList<Chord> mChords;

    public AddChordsToLocalDbEvent(ArrayList<Chord> chords) {
        this.mChords = chords;
    }

    public ArrayList<Chord> getChords() {
        return mChords;
    }
}
