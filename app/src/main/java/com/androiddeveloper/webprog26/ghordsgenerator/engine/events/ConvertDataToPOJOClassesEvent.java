package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Created by webpr on 31.05.2017.
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
