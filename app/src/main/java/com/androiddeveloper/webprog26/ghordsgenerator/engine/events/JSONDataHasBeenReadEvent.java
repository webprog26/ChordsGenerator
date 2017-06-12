package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity} that
 * JSON data has been successfully read from assets
 */

public class JSONDataHasBeenReadEvent extends SuperEvent{

    public JSONDataHasBeenReadEvent(String JSONString) {
        super(JSONString);
    }
}
