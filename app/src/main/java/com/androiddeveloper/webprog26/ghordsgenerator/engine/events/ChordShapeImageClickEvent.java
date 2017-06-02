package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.MainActivity}
 * than {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} {@link android.graphics.Bitmap}
 * image was clicked
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
