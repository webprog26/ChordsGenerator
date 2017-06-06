package com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces;

/**
 * Manages {@link com.androiddeveloper.webprog26.ghordsgenerator.PlayShapeActivity} control buttons state
 */

public interface PlayShapeActivityControlsEnabler {

    void setNextPlayableShapeButtonEnabled(boolean enabled);
    void setPreviousPlayableShapeButtonEnabled(boolean enabled);
}
