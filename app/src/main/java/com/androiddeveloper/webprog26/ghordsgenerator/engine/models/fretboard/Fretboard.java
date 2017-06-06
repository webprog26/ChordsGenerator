package com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard;


import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.MutedStringsHolder;

/**
 * Guitar fretboard POJO class
 */

public class Fretboard {

    public static final int STRINGS_COUNT = 6;

    private static final String FIRST_STRING = "first_string";
    private static final String SECOND_STRING = "second_string";
    private static final String THIRD_STRING = "third_string";
    private static final String FOURTH_STRING = "fourth_string";
    private static final String FIFTH_STRING = "fifth_string";
    private static final String SIXTH_STRING = "sixth_string";

    private final GuitarString[] guitarStrings;

    public Fretboard(MutedStringsHolder mutedStringsHolder) {

        guitarStrings = new GuitarString[]{
                new GuitarString(FIRST_STRING, mutedStringsHolder.isFirstStringMuted()),
                new GuitarString(SECOND_STRING, mutedStringsHolder.isSecondStringMuted()),
                new GuitarString(THIRD_STRING, mutedStringsHolder.isThirdStringMuted()),
                new GuitarString(FOURTH_STRING, mutedStringsHolder.isFourthStringMuted()),
                new GuitarString(FIFTH_STRING, mutedStringsHolder.isFifthStringMuted()),
                new GuitarString(SIXTH_STRING, mutedStringsHolder.isSixthStringMuted())
        };
    }

    public GuitarString getGuitarString(int index){
        return guitarStrings[index];
    }

    public GuitarString[] getGuitarStrings() {
        return guitarStrings;
    }
}
