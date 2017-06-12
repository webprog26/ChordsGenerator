package com.androiddeveloper.webprog26.ghordsgenerator.engine.events;

/**
 * Notifies {@link com.androiddeveloper.webprog26.ghordsgenerator.StartActivity} that another chord
 * has been succesfully loaded to local database. Used to update it's {@link android.widget.ProgressBar} status
 */

public class SingleChordLoadedToLocalDBEvent extends SuperEvent{

    public SingleChordLoadedToLocalDBEvent(String chordTitle) {
        super(chordTitle);
    }
}
