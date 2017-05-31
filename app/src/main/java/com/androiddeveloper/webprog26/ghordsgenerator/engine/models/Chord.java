package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import java.util.ArrayList;

/**
 * Created by webpr on 31.05.2017.
 */

public class Chord {

    private final String mChordTitle;
    private final String mChordSecondTitle;
    private final String mChordType;
    private final String mChordAlteration;
    private final ArrayList<ChordShape> mChordShapes;

    public Chord(String chordTitle,
                 String chordSecondTitle,
                 String chordType,
                 String chordAlteration,
                 ArrayList<ChordShape> chordShapes) {
        this.mChordTitle = chordTitle;
        this.mChordSecondTitle = chordSecondTitle;
        this.mChordType = chordType;
        this.mChordAlteration = chordAlteration;
        this.mChordShapes = chordShapes;
    }

    public String getChordTitle() {
        return mChordTitle;
    }

    public String getChordSecondTitle() {
        return mChordSecondTitle;
    }

    public String getChordType() {
        return mChordType;
    }

    public String getChordAlteration() {
        return mChordAlteration;
    }

    public ArrayList<ChordShape> getChordShapes() {
        return mChordShapes;
    }
}
