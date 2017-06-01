package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

/**
 * Created by webpr on 25.05.2017.
 */

public class FretNumbersTransformHelper {

    private static final String FRET_1 = "I";
    private static final String FRET_2 = "II";
    private static final String FRET_3 = "III";
    private static final String FRET_4 = "IV";
    private static final String FRET_5 = "V";
    private static final String FRET_6 = "VI";
    private static final String FRET_7 = "VII";
    private static final String FRET_8 = "VIII";
    private static final String FRET_9 = "IX";
    private static final String FRET_10 = "X";
    private static final String FRET_11 = "XI";
    private static final String FRET_12 = "XII";

    public static String getFretToStartString(int startFretPosition){
        switch (startFretPosition){

            case 1:
                return FRET_1;
            case 2:
                return FRET_2;
            case 3:
                return FRET_3;
            case 4:
                return FRET_4;
            case 5:
                return FRET_5;
            case 6:
                return FRET_6;
            case 7:
                return FRET_7;
            case 8:
                return FRET_8;
            case 9:
                return FRET_9;
            case 10:
                return FRET_10;
            case 11:
                return FRET_11;
            case 12:
                return FRET_12;
            default:
                return null;
        }
    }
}
