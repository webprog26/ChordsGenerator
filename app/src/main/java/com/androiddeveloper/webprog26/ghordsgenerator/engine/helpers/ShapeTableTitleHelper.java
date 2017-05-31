package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.content.res.Resources;

import com.androiddeveloper.webprog26.ghordsgenerator.R;

/**
 * Created by webpr on 31.05.2017.
 */

public class ShapeTableTitleHelper {

    private final Resources mResources;

    public ShapeTableTitleHelper(Resources resources) {
        this.mResources = resources;
    }

    private static final String C_MAJ_ORDINARY = "C";

    public String getChordShapesTableTitle(String chordTitle){

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
