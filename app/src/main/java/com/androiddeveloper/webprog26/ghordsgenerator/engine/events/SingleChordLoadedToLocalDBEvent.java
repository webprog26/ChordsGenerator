package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Created by webpr on 31.05.2017.
 */

public class SingleChordLoadedToLocalDBEvent {

    private final String mChordTitle;

    public SingleChordLoadedToLocalDBEvent(String chordTitle) {
        this.mChordTitle = chordTitle;
    }

    public String getChordTitle() {
        return mChordTitle;
    }
}
