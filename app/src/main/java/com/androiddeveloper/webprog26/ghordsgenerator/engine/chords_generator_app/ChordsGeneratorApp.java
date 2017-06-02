package com.androiddeveloper.webprog26.ghordsgenerator.engine.chords_generator_app;

import android.app.Application;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.db.ChordsDBProvider;

/**
 * Application class to create single Database provider instance
 */

public class ChordsGeneratorApp extends Application {

    private static ChordsDBProvider mChordsDBProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        mChordsDBProvider = new ChordsDBProvider(this);
    }

    public static ChordsDBProvider getChordsDBProvider(){
        return mChordsDBProvider;
    }
}
