package com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard;


import com.androiddeveloper.webprog26.ghordsgenerator.engine.fretboard.guitar_string.GuitarString;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.MutedStringsHolder;

/**
 * Created by webpr on 23.05.2017.
 */

public class Fretboard {

    public static final int STRINGS_COUNT = 6;

    public static final String FIRST_STRING = "first_string";
    public static final String SECOND_STRING = "second_string";
    public static final String THIRD_STRING = "third_string";
    public static final String FOURTH_STRING = "fourth_string";
    public static final String FIFTH_STRING = "fifth_string";
    public static final String SIXTH_STRING = "sixth_string";

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

}
