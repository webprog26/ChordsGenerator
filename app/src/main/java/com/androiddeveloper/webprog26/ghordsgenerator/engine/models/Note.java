package com.androiddeveloper.webprog26.ghordsgenerator.engine.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Note POJO class
 */

public class Note implements Serializable{

    public static final int NO_SOUND = -1;

    private final String mNoteTitle;
    private final int mNoteFret;
    private final int mNoteFingerIndex;
    private final int mNotePlace;
    private final String mNoteSoundPath;

    private Drawable mNoteTitleDrawable;
    private Drawable mNoteFingerIndexDrawable;
    private int mNoteSound = NO_SOUND;
    private boolean isFingerIndexVisible = false;

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

    public int getNoteSound() {
        return mNoteSound;
    }

    public void setNoteSound(int noteSound) {
        this.mNoteSound = noteSound;
    }

    public boolean isFingerIndexVisible() {
        return isFingerIndexVisible;
    }

    public void setFingerIndexVisible(boolean fingerIndexVisible) {
        isFingerIndexVisible = fingerIndexVisible;
    }
}
