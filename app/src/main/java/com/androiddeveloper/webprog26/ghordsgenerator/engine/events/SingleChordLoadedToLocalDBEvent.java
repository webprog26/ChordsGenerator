package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity} that another chord
 * has been succesfully loaded to local database. Used to update it's {@link android.widget.ProgressBar} status
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
