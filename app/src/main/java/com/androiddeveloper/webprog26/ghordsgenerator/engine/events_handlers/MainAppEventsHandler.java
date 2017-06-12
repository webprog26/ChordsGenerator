package com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers;

import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.PlayShapeActivity;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordShapeImageClickEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.OnChordShapeImageClickCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers.MainAppScreenManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordInfoHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by webpr on 12.06.2017.
 */

public class MainAppEventsHandler extends AppEventsHandler {

    private static final String TAG = "MainAppEventsHandler";

    private final OnChordShapeImageClickCallback mOnChordShapeImageClickCallback;

    public MainAppEventsHandler(OnChordShapeImageClickCallback onChordShapeImageClickCallback) {
        this.mOnChordShapeImageClickCallback = onChordShapeImageClickCallback;
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    /**
     * Handles {@link ChordShapeImageClickEvent}. Starts {@link PlayShapeActivity} and packs in it's Intent {@link ChordInfoHolder}
     * that contains {@link Chord} data
     * @param chordShapeImageClickEvent {@link ChordShapeImageClickEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChordShapeImageClickEvent(ChordShapeImageClickEvent chordShapeImageClickEvent){
        Log.i(TAG, "onChordShapeImageClickEvent");

        Object eventObject = chordShapeImageClickEvent.getEventObject();
        int clickedShapePosition = -1;

        if(eventObject instanceof Integer){
            clickedShapePosition = (int) eventObject;
        }

        if(clickedShapePosition != -1){

            OnChordShapeImageClickCallback onChordShapeImageClickCallback = getmOnChordShapeImageClickCallback();

            if(onChordShapeImageClickCallback != null){

                onChordShapeImageClickCallback.onChordImageClicked(clickedShapePosition);

            }

        }

    }

    private OnChordShapeImageClickCallback getmOnChordShapeImageClickCallback() {
        return mOnChordShapeImageClickCallback;
    }
}
