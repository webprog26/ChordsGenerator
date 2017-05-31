package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import java.util.ArrayList;

/**
 * Created by webpr on 31.05.2017.
 */

public class Chord {

    public static final String NO_TYPE = "no_type";
    public static final String NO_ALTERATION = "no_alteration";

    private String mChordTitle;
    private String mChordSecondTitle;
    private String mChordType;
    private String mChordAlteration;
    private ArrayList<ChordShape> mChordShapes;
    private String mChordShapesTableName;

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

    public Chord() {
    }

    public void setChordTitle(String chordTitle) {
        this.mChordTitle = chordTitle;
    }

    public void setChordType(String chordType) {
        this.mChordType = chordType;
    }

    public void setChordAlteration(String chordAlteration) {
        this.mChordAlteration = chordAlteration;
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

    public String getChordShapesTableName() {
        return mChordShapesTableName;
    }

    public void setChordShapesTableName(String chordShapesTableName) {
        this.mChordShapesTableName = chordShapesTableName;
    }
}
