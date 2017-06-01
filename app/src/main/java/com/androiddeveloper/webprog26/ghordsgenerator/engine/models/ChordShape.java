package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by webpr on 31.05.2017.
 */

public class ChordShape implements Serializable{

    public static final int NO_BAR_PLACE = -1;

    private final int mShapePosition;
    private final int mStartFretPosition;
    private final String mImageTitle;
    private final ArrayList<Note> mNotes;
    private final boolean mHasMutedStrings;
    private final MutedStringsHolder mMutedStringsHolder;
    private final boolean mHasBar;
    private final int mStartBarPlace;
    private final int mEndBarPlace;

    public ChordShape(int shapePosition,
                      int startFretPosition,
                      String imageTitle,
                      ArrayList<Note> notes,
                      boolean hasMutedStrings,
                      MutedStringsHolder mutedStringsHolder,
                      boolean hasBar,
                      int startBarPlace,
                      int endBarPlace) {
        this.mShapePosition = shapePosition;
        this.mStartFretPosition = startFretPosition;
        this.mImageTitle = imageTitle;
        this.mNotes = notes;
        this.mHasMutedStrings = hasMutedStrings;
        this.mMutedStringsHolder = mutedStringsHolder;
        this.mHasBar = hasBar;
        this.mStartBarPlace = startBarPlace;
        this.mEndBarPlace = endBarPlace;
    }

    public int getShapePosition() {
        return mShapePosition;
    }

    public int getStartFretPosition() {
        return mStartFretPosition;
    }

    public String getImageTitle() {
        return mImageTitle;
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    public boolean isHasMutedStrings() {
        return mHasMutedStrings;
    }

    public MutedStringsHolder getMutedStringsHolder() {
        return mMutedStringsHolder;
    }

    public boolean isHasBar() {
        return mHasBar;
    }

    public int getStartBarPlace() {
        return mStartBarPlace;
    }

    public int getEndBarPlace() {
        return mEndBarPlace;
    }

    @Override
    public String toString() {

        String bar;

        if(isHasBar()){
            bar = "with bar";
        } else {
            bar = "with no bar";
        }

        return "Chord shape: " + "\n"
                + "position: " + getShapePosition() + "\n"
                + "start fret position: " + getStartFretPosition() + "\n"
                + "with image: " + getImageTitle() + "\n"
                + "has " + getNotes().size() + " notes" + "\n"
                + "with muted strings " + getMutedStringsHolder().toString() + "\n"
                + bar + "\n"
                + "bar starts at " + getStartBarPlace() + "\n"
                + "bar ends at " + getEndBarPlace();
    }
}
