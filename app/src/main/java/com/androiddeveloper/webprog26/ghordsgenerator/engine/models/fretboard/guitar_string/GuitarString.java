package com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;


/**
 * Created by webpr on 23.05.2017.
 */

public class GuitarString {

    private static final String TAG = "GuitarString";

    public static final float NO_COORDINATE = -1;

    private final String mTitle;
    private final boolean isMuted;
    private float startX;
    private float endX;
    private float playableY;

    private Note mNote;


    public GuitarString(String title, boolean isMuted) {
        this.mTitle = title;
        this.isMuted = isMuted;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(Note note) {
        this.mNote = note;
    }

    public float getPlayableY() {
        return playableY;
    }

    public void setPlayableY(float playableY) {
        this.playableY = playableY;
    }
}
