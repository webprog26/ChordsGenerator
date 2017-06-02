package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity} that
 * JSON data has been successfully read from assets
 */

public class JSONDataHasBeenReadEvent {

    private final String mJSONString;

    public JSONDataHasBeenReadEvent(String JSONString) {
        this.mJSONString = JSONString;
    }

    public String getJSONString() {
        return mJSONString;
    }
}
