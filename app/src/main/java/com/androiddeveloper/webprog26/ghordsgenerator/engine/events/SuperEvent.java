package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Created by webpr on 12.06.2017.
 */

public class SuperEvent {

    private final Object mEventObject;

    public SuperEvent(Object eventObject) {
        this.mEventObject = eventObject;
    }

    public Object getEventObject() {
        return mEventObject;
    }
}
