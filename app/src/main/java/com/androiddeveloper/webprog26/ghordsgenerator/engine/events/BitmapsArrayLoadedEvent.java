package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.MainActivity} that
 * {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} {@link Bitmap}
 * images successfully loaded from assets
 */

public class BitmapsArrayLoadedEvent extends SuperEvent{

    public BitmapsArrayLoadedEvent(ArrayList<Bitmap> bitmaps) {
        super(bitmaps);
    }
}
