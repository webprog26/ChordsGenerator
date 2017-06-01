package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by webpr on 01.06.2017.
 */

public class BitmapsArrayLoadedEvent {

    private final ArrayList<Bitmap> mBitmaps;

    public BitmapsArrayLoadedEvent(ArrayList<Bitmap> bitmaps) {
        this.mBitmaps = bitmaps;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return mBitmaps;
    }
}
