package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Created by webpr on 31.05.2017.
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
