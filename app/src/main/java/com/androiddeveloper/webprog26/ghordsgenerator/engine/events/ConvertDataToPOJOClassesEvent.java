package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity}
 * that {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord}s
 * should be converted to POJOs
 */

public class ConvertDataToPOJOClassesEvent {

    private final String mJsonString;

    public ConvertDataToPOJOClassesEvent(String jsonString) {
        this.mJsonString = jsonString;
    }

    public String getJsonString() {
        return mJsonString;
    }
}
