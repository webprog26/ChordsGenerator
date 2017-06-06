package com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

/**
 * Shapes {@link android.support.v4.app.Fragment} loader
 */

public interface ShapesFragmentLoader {

    void setFragmentWithListOfChordImages(final Chord chord);
    void setPlayableShapeFragment();
}
