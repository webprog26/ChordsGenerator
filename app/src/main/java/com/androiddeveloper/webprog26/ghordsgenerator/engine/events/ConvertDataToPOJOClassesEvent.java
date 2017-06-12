package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity}
 * that {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord}s
 * should be converted to POJOs
 */

public class ConvertDataToPOJOClassesEvent extends SuperEvent{

    public ConvertDataToPOJOClassesEvent(String jsonString) {
        super(jsonString);
    }
}
