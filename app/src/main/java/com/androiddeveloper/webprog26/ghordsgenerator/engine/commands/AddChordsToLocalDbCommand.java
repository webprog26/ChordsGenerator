package com.androiddeveloper.webprog26.ghordsgenerator.engine.commands;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.realizers.AddChordsToLocalDbRealizer;

import java.util.ArrayList;

/**
 * Adds {@link Chord} to local database
 */

public class AddChordsToLocalDbCommand implements Command {

    private final ArrayList<Chord> mChords;

    public AddChordsToLocalDbCommand(ArrayList<Chord> chords) {
        this.mChords = chords;
    }

    @Override
    public void execute() {
        new AddChordsToLocalDbRealizer(getChords()).realize();
    }


    private ArrayList<Chord> getChords() {
        return mChords;
    }
}
