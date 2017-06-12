package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.PlayShapeActivity}
 * that {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} {@link java.util.ArrayList}
 * successfully loaded from local database
 */

public class ChordShapesListReadyEvent extends SuperEvent{

    public ChordShapesListReadyEvent() {
        super(null);
    }
}
