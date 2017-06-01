package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers.MainAppScreenManager;


/**
 * Helper class to show user messages via {@link Snackbar}
 */

public class UIMessageHelper {

    private final View mContextView;
    private final MainAppScreenManager mainAppScreenManager;

    private static final int UI_MESSAGE_DURATION = 5000;

    public UIMessageHelper(final MainAppScreenManager mainAppScreenManager) {
        this.mainAppScreenManager = mainAppScreenManager;

        this.mContextView = ((Activity) mainAppScreenManager.getContext()).findViewById(mainAppScreenManager.getContainerViewId());
    }

    /**
     * Shows user message
     * @param fromChord {@link String}
     * @param toChord {@link String}
     */
    public void sendUiWrongChordMessage(final String fromChord, final String toChord){
        final MainAppScreenManager mainAppScreenManager = getMainAppScreenManager();
        final Context context = mainAppScreenManager.getContext();

        showSnackbarMessage(context.getString(R.string.wrong_chord, fromChord, toChord),
                context.getString(R.string.why), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainAppScreenManager.showReference(fromChord);
                    }
                });
    }

    /**
     * Shows snackbar message directly
     * @param message {@link String}
     * @param action {@link String}
     * @param actionListener {@link View.OnClickListener}
     */
    private void showSnackbarMessage(String message, String action, View.OnClickListener actionListener){

        Snackbar uiMessageSnackbar = Snackbar.make(getContextView(), message, UI_MESSAGE_DURATION);

        if(action != null){
            if(actionListener != null){
                uiMessageSnackbar.setAction(action, actionListener);
            } else {
                uiMessageSnackbar.setAction(action, null);
            }
        }

        uiMessageSnackbar.show();
    }



    private View getContextView() {
        return mContextView;
    }

    private MainAppScreenManager getMainAppScreenManager() {
        return mainAppScreenManager;
    }
}
