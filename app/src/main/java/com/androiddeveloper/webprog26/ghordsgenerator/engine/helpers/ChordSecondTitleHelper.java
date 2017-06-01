package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.content.res.Resources;

import com.androiddeveloper.webprog26.ghordsgenerator.R;

/**
 * Created by webpr on 01.06.2017.
 */

public class ChordSecondTitleHelper {

    private static final String C_SHARP = "C#";
    private static final String D_FLAT = "Db";
    private static final String D_SHARP = "D#";
    private static final String F_SHARP = "F#";
    private static final String G_FLAT = "Gb";
    private static final String G_SHARP = "G#";
    private static final String A_FLAT = "Ab";
    private static final String A_SHARP = "A#";
    private static final String H_FLAT = "Hb";


    private final String[] mChordTitles;
    private final String[] mChordParams;

    private final String SHARP;
    private final String FLAT;

    public ChordSecondTitleHelper(Resources resources) {
        this.mChordTitles = resources.getStringArray(R.array.chords_titles);
        this.mChordParams = resources.getStringArray(R.array.chords_params);

        SHARP = mChordParams[2];
        FLAT = mChordParams[1];
    }

    public String getChordSecondTitle(String chordTitle){

        StringBuilder stringBuilder = new StringBuilder();

        switch (chordTitle){

            case C_SHARP:
                stringBuilder.append(mChordTitles[1]);
                stringBuilder.append(getFLAT());
                return stringBuilder.toString();

            case D_FLAT:
                stringBuilder.append(mChordTitles[0]);
                stringBuilder.append(getSHARP());
                return stringBuilder.toString();

            case D_SHARP:
                stringBuilder.append(mChordTitles[2]);
                stringBuilder.append(getFLAT());
                return stringBuilder.toString();

            case F_SHARP:
                stringBuilder.append(mChordTitles[4]);
                stringBuilder.append(getFLAT());
                return stringBuilder.toString();

            case G_FLAT:
                stringBuilder.append(mChordTitles[3]);
                stringBuilder.append(getSHARP());
                return stringBuilder.toString();


            case G_SHARP:
                stringBuilder.append(mChordTitles[5]);
                stringBuilder.append(getFLAT());
                return stringBuilder.toString();

            case A_FLAT:
                stringBuilder.append(mChordTitles[4]);
                stringBuilder.append(getSHARP());
                return stringBuilder.toString();

            case A_SHARP:
            case H_FLAT:
                stringBuilder.append(mChordTitles[6]);
                return stringBuilder.toString();


            default:
                return null;
        }
    }

    public String getSHARP() {
        return SHARP;
    }

    public String getFLAT() {
        return FLAT;
    }
}
