package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.PlayShapeActivity} that
 * {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape}s should be
 * loaded from local database
 */

public class LoadChordShapesFromLocalDBEvent extends SuperEvent{

    public LoadChordShapesFromLocalDBEvent() {
        super(null);
    }
}
