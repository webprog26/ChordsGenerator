package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import java.util.ArrayList;

/**
 * Created by webpr on 31.05.2017.
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
