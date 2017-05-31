package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import android.graphics.drawable.Drawable;

/**
 * Created by webpr on 31.05.2017.
 */

public class Note {

    private final String mNoteTitle;
    private final int mNoteFret;
    private final int mNoteFingerIndex;
    private final int mNotePlace;
    private final String mNoteSoundPath;

    private Drawable mNoteTitleDrawable;
    private Drawable mNoteFingerIndexDrawable;

    public Note(String noteTitle,
                int noteFret,
                int noteFingerIndex,
                int notePlace,
                String noteSoundPath) {
        this.mNoteTitle = noteTitle;
        this.mNoteFret = noteFret;
        this.mNoteFingerIndex = noteFingerIndex;
        this.mNotePlace = notePlace;
        this.mNoteSoundPath = noteSoundPath;
    }

    public String getNoteTitle() {
        return mNoteTitle;
    }

    public int getNoteFret() {
        return mNoteFret;
    }

    public int getNoteFingerIndex() {
        return mNoteFingerIndex;
    }

    public int getNotePlace() {
        return mNotePlace;
    }

    public String getNoteSoundPath() {
        return mNoteSoundPath;
    }

    public Drawable getNoteTitleDrawable() {
        return mNoteTitleDrawable;
    }

    public void setNoteTitleDrawable(Drawable noteTitleDrawable) {
        this.mNoteTitleDrawable = noteTitleDrawable;
    }

    public Drawable getNoteFingerIndexDrawable() {
        return mNoteFingerIndexDrawable;
    }

    public void setNoteFingerIndexDrawable(Drawable noteFingerIndexDrawable) {
        this.mNoteFingerIndexDrawable = noteFingerIndexDrawable;
    }
}
