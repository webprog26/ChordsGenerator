package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Created by webpr on 01.06.2017.
 */

public class LoadChordShapesBitmapsEvent {

    private final String mChordShapesTableName;

    public LoadChordShapesBitmapsEvent(String chordShapesTableName) {
        this.mChordShapesTableName = chordShapesTableName;
    }

    public String getChordShapesTableName() {
        return mChordShapesTableName;
    }
}
