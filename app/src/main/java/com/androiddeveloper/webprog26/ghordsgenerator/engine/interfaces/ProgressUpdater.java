package com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces;

/**
 * Created by webpr on 12.06.2017.
 */

public interface ProgressUpdater {

    void changeProgressBarVisibility();
    void setProgressBarMax(int max);
    void setProgressBarProgress(int progress);
}
