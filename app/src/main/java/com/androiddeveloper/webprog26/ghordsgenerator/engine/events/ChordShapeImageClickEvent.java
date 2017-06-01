package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Created by webpr on 01.06.2017.
 */

public class ChordShapeImageClickEvent {

    private final int mClickedShapePosition;

    public ChordShapeImageClickEvent(int clickedShapePosition) {
        this.mClickedShapePosition = clickedShapePosition;
    }

    public int getClickedShapePosition() {
        return mClickedShapePosition;
    }
}
