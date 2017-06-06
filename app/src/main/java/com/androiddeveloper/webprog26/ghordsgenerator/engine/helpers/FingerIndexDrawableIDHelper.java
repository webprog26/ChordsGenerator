package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;


import com.androiddeveloper.webprog26.ghordsgenerator.R;

/**
 * Gets finger index image from {@link android.content.res.Resources}
 */

public class FingerIndexDrawableIDHelper {

    //Finger index images references
    private static final int FIRST_FINGER_INDEX = R.drawable.first_finger;
    private static final int SECOND_FINGER_INDEX = R.drawable.second_finger;
    private static final int THIRD_FINGER_INDEX = R.drawable.third_finger;
    private static final int FOURTH_FINGER_INDEX = R.drawable.fourth_finger;

    /**
     * Returns reference to chord note finger index image in {@link android.content.res.Resources},
     * based on it's finger index loaded from JSON
     * @param fingerIndex int
     * @return int
     */
    public static int getFingerIndexDrawableId(int fingerIndex){
        switch (fingerIndex){
            case 1:
                return FIRST_FINGER_INDEX;

            case 2:
                return SECOND_FINGER_INDEX;

            case 3:
                return THIRD_FINGER_INDEX;

            case 4:
                return FOURTH_FINGER_INDEX;

            default:
                return -1;
        }
    }
}
