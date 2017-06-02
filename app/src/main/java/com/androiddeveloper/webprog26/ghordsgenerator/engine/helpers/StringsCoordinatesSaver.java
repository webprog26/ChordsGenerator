package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.content.SharedPreferences;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.StringCoordinatesHolder;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.Fretboard;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.fretboard.guitar_string.GuitarString;

/**
 * Created by webpr on 02.06.2017.
 */

public class StringsCoordinatesSaver {

    public static final int FIRST_STRING = 0;
    public static final int SECOND_STRING = 1;
    public static final int THIRD_STRING = 2;
    public static final int FOURTH_STRING = 3;
    public static final int FIFTH_STRING = 4;
    public static final int SIXTH_STRING = 5;

    public static final String FIRST_STRING_START_X = "first_string_start_x";
    public static final String FIRST_STRING_END_X = "first_string_start_x";

    public static final String SECOND_STRING_START_X = "second_string_start_x";
    public static final String SECOND_STRING_END_X = "second_string_start_x";

    public static final String THIRD_STRING_START_X = "third_string_start_x";
    public static final String THIRD_STRING_END_X = "third_string_start_x";

    public static final String FOURTH_STRING_START_X = "fourth_string_start_x";
    public static final String FOURTH_STRING_END_X = "fourth_string_start_x";

    public static final String FIFTH_STRING_START_X = "fifth_string_start_x";
    public static final String FIFTH_STRING_END_X = "fifth_string_start_x";

    public static final String SIXTH_STRING_START_X = "sixth_string_start_x";
    public static final String SIXTH_STRING_END_X = "sixth_string_start_x";


    public static void saveStringsCoordinatesToSharedPreferences(final SharedPreferences sharedPreferences, Fretboard fretboard){
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        saveSingleStringCoordinates(editor,
                                    new String[]{FIRST_STRING_START_X, FIRST_STRING_END_X},
                                    new StringCoordinatesHolder(fretboard.getGuitarString(FIRST_STRING)));

        saveSingleStringCoordinates(editor,
                                    new String[]{SECOND_STRING_START_X, SECOND_STRING_END_X},
                                    new StringCoordinatesHolder(fretboard.getGuitarString(SECOND_STRING)));

        saveSingleStringCoordinates(editor,
                                    new String[]{THIRD_STRING_START_X, THIRD_STRING_END_X},
                                    new StringCoordinatesHolder(fretboard.getGuitarString(THIRD_STRING)));

        saveSingleStringCoordinates(editor,
                                    new String[]{FOURTH_STRING_START_X, FOURTH_STRING_END_X},
                                    new StringCoordinatesHolder(fretboard.getGuitarString(FOURTH_STRING)));

        saveSingleStringCoordinates(editor,
                                    new String[]{FIFTH_STRING_START_X, FIFTH_STRING_END_X},
                                    new StringCoordinatesHolder(fretboard.getGuitarString(FIFTH_STRING)));

        saveSingleStringCoordinates(editor,
                                    new String[]{SIXTH_STRING_START_X, SIXTH_STRING_END_X},
                                    new StringCoordinatesHolder(fretboard.getGuitarString(SIXTH_STRING)));

    }

    private static void saveSingleStringCoordinates(final SharedPreferences.Editor editor,
                                                    String[] keys,
                                                    StringCoordinatesHolder stringCoordinatesHolder){
        editor.putFloat(keys[0], stringCoordinatesHolder.getStringStartX());
        editor.putFloat(keys[1], stringCoordinatesHolder.getStringEndX());
        editor.apply();
    }
}
