package com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.InitNotesWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.NotesInitializedWithDrawablesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks.PlayShapeFragmentCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Handles events for {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.PlayShapesFragment}
 */

public class PlayShapesFragmentEventsHandler extends AppEventsHandler {

    private final PlayShapeFragmentManager mPlayShapeFragmentManager;
    private final PlayShapeFragmentCallback mPlayShapeFragmentCallback;

    public PlayShapesFragmentEventsHandler(PlayShapeFragmentManager playShapeFragmentManager) {
        this.mPlayShapeFragmentManager = playShapeFragmentManager;
        this.mPlayShapeFragmentCallback = playShapeFragmentManager.getPlayShapeFragmentCallback();
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
     * Handles {@link InitNotesWithDrawablesEvent} Calls {@link PlayShapeFragmentManager} to add drawables
     * to the {@link ChordShape} notes
     * @param initNotesWithDrawablesEvent {@link InitNotesWithDrawablesEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onInitNotesWithDrawablesEvent(InitNotesWithDrawablesEvent initNotesWithDrawablesEvent){
        PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        if(playShapeFragmentManager != null){

            playShapeFragmentManager.setDrawablesAndSoundsToNotes();

        }
    }

    /**
     * Actually main method of the class. Handles {@link NotesInitializedWithDrawablesEvent} which
     * means that notes are completely ready to be showed, played and (or) clicked
     * @param notesInitializedWithDrawablesEvent {@link InitNotesWithDrawablesEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotesInitializedWithDrawablesEvent(NotesInitializedWithDrawablesEvent notesInitializedWithDrawablesEvent){

        PlayShapeFragmentCallback playShapeFragmentCallback = getPlayShapeFragmentCallback();

        if(playShapeFragmentCallback != null){

            playShapeFragmentCallback.onFretboardReady();

        }

    }

    private PlayShapeFragmentManager getPlayShapeFragmentManager() {
        return mPlayShapeFragmentManager;
    }

    private PlayShapeFragmentCallback getPlayShapeFragmentCallback() {
        return mPlayShapeFragmentCallback;
    }
}
