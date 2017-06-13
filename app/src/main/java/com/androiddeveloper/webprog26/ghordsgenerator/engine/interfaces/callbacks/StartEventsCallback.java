package com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks;

/**
 * Handles calls from {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers.AppStartEventsHandler}
 */

public interface StartEventsCallback {

    void updateButtonGoState(boolean isEnabled);
    void changeProgressBarVisibility();
    void setProgressBarMax(int max);
    void setProgressBarProgress(int progress);

    void updateUserContainerVisibility();
    void updateUserMessage(String userMessageText);
    void writeBooleanToSharedPreferences(boolean value);
}
