package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.ChordShapesFragment}
 * that {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape}s {@link android.graphics.Bitmap}
 * images should be loaded from assets
 */

public class LoadChordShapesBitmapsEvent extends SuperEvent{

    public LoadChordShapesBitmapsEvent(String chordShapesTableName) {
        super(chordShapesTableName);
    }
}
