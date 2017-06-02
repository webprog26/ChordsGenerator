package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string.GuitarString;

/**
 * Created by webpr on 02.06.2017.
 */

public class StringCoordinatesHolder  {

    private final float mStringStartX;
    private final float mStringEndX;

    public StringCoordinatesHolder(final GuitarString guitarString) {
        this.mStringStartX = guitarString.getStartX();
        this.mStringEndX = guitarString.getEndX();
    }

    public float getStringStartX() {
        return mStringStartX;
    }

    public float getStringEndX() {
        return mStringEndX;
    }
}
