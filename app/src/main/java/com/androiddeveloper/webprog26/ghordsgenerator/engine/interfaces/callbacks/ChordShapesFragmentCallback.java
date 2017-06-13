package com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Handles calls from {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers.ChordShapesFragmentEventsHandler}
 */

public interface ChordShapesFragmentCallback {

    void onBitmapsArrayLoaded(ArrayList<Bitmap> bitmaps);
}
