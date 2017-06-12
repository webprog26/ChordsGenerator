package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import java.util.ArrayList;

/**
 * CNotifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity}
 * that JSON data has benn successfully converted to POJOs
 */

public class DataHasBeenConvertedToPOJOsEvent extends SuperEvent{

    public DataHasBeenConvertedToPOJOsEvent(ArrayList<Chord> chords) {
        super(chords);
    }
}
