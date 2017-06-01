package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import java.io.Serializable;

/**
 * Created by webpr on 01.06.2017.
 */

public class ChordInfoHolder implements Serializable{

    private final String mChordTitle;
    private final String mChordSecondTitle;
    private final int mClickedShapePosition;
    private final String mChordShapesTableName;

    public ChordInfoHolder(String chordTitle,
                           String chordSecondTitle,
                           int clickedShapePosition,
                           String chordShapesTableName) {
        this.mChordTitle = chordTitle;
        this.mChordSecondTitle = chordSecondTitle;
        this.mClickedShapePosition = clickedShapePosition;
        this.mChordShapesTableName = chordShapesTableName;
    }

    public String getChordTitle() {
        return mChordTitle;
    }

    public String getChordSecondTitle() {
        return mChordSecondTitle;
    }

    public int getClickedShapePosition() {
        return mClickedShapePosition;
    }

    public String getChordShapesTableName() {
        return mChordShapesTableName;
    }
}
