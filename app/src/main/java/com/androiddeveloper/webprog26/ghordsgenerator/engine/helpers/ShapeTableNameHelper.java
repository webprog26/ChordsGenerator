package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.content.res.Resources;

import com.androiddeveloper.webprog26.ghordsgenerator.R;

/**
 * Gets {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} table title
 * from {@link Resources}
 */

public class ShapeTableNameHelper {

    private final Resources mResources;

    public ShapeTableNameHelper(Resources resources) {
        this.mResources = resources;
    }

    private static final String C_MAJ_ORDINARY = "C";

    /**
     * Returns current {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord}
     * {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape}
     * {@link android.database.sqlite.SQLiteDatabase} table title depending on choirds title
     * @param chordTitle {@link String}
     * @return String
     */
    public String getChordShapesTableName(String chordTitle){

        final Resources resources = getResources();

        if(resources == null){
            return null;
        }

        switch (chordTitle){

            case C_MAJ_ORDINARY:

                return resources.getString(R.string.c_maj_ordinary);

            default:
                return null;
        }
    }

    private Resources getResources() {
        return mResources;
    }
}
