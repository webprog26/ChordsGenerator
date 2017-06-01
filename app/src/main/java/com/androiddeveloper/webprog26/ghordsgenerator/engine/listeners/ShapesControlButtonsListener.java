package com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners;

import android.view.View;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers.PlayShapeActivityManager;


/**
 * Created by webpr on 18.05.2017.
 */

public class ShapesControlButtonsListener implements View.OnClickListener {

    private final PlayShapeActivityManager mPlayShapeActivityManager;

    public ShapesControlButtonsListener(PlayShapeActivityManager playShapeActivityManager) {
        this.mPlayShapeActivityManager = playShapeActivityManager;
    }

    @Override
    public void onClick(View v) {

        PlayShapeActivityManager playShapeActivityManager = getPlayShapeActivityManager();

        switch (v.getId()){
            case R.id.btn_next:
                playShapeActivityManager.setNextPlayableShape();
                break;
            case R.id.btn_previous:
                playShapeActivityManager.setPreviousPlayableShape();
                break;
        }
    }

    private PlayShapeActivityManager getPlayShapeActivityManager() {
        return mPlayShapeActivityManager;
    }
}
