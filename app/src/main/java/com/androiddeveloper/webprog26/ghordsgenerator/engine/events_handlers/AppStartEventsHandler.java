package com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers;

import android.util.Log;
import android.widget.ProgressBar;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.AddChordsToLocalDbEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordsUploadedToDatabaseEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ConvertDataToPOJOClassesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.DataHasBeenConvertedToPOJOsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.JSONDataHasBeenReadEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ReadJSONDataEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.SingleChordLoadedToLocalDBEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.StartEventsCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.app_data_manager.AppDataManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by webpr on 12.06.2017.
 */

public class AppStartEventsHandler extends AppEventsHandler {

    private static final String TAG = "AppEventsHandler";

    private final AppDataManager mAppDataManager;
    private final StartEventsCallback mStartEventsCallback;

    public AppStartEventsHandler(AppDataManager appDataManager) {
        this.mAppDataManager = appDataManager;
        this.mStartEventsCallback = appDataManager.getStartEventsCallback();
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
     * Handles {@link ReadJSONDataEvent}. Starts reading data from JSON file in assets directory
     * @param readJSONDataEvent {@link ReadJSONDataEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReadJSONDataEvent(ReadJSONDataEvent readJSONDataEvent){
        getAppDataManager().readJSONData();
    }

    /**
     * Handles {@link JSONDataHasBeenReadEvent}. Start converting read data to POJO classes
     * @param jsonDataHasBeenReadEvent {@link JSONDataHasBeenReadEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJSONDataHasBeenReadEvent(JSONDataHasBeenReadEvent jsonDataHasBeenReadEvent){
        Log.i(TAG, "onJSONDataHasBeenReadEvent");
        Object eventObject = jsonDataHasBeenReadEvent.getEventObject();
        String resultString = null;

        if(eventObject instanceof String){
            resultString = (String) eventObject;
        }

        if(resultString != null){
            EventBus.getDefault().post(new ConvertDataToPOJOClassesEvent(resultString));
        }
    }

    /**
     * Handles {@link ConvertDataToPOJOClassesEvent}. Converts data to POJO classes
     * @param convertDataToPOJOClassesEvent {@link ConvertDataToPOJOClassesEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onConvertDataToPOJOClassesEvent(ConvertDataToPOJOClassesEvent convertDataToPOJOClassesEvent){
        Log.i(TAG, "onConvertDataToPOJOClassesEvent");
        Object eventObject = convertDataToPOJOClassesEvent.getEventObject();
        String jsonString = null;

        if(eventObject instanceof String){
            jsonString = (String) eventObject;
        }

        if(jsonString != null){
            getAppDataManager().convertJSONDateToPOJOClasses(jsonString);
        }
    }

    /**
     * Handles {@link DataHasBeenConvertedToPOJOsEvent}. Starts uploading data to local {@link android.database.sqlite.SQLiteDatabase}
     * and shows {@link ProgressBar}
     * @param dataHasBeenConvertedToPOJOsEvent {@link DataHasBeenConvertedToPOJOsEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataHasBeenConvertedToPOJOsEvent(DataHasBeenConvertedToPOJOsEvent dataHasBeenConvertedToPOJOsEvent){
        Log.i(TAG, "onDataHasBeenConvertedToPOJOsEvent");

        Object eventObject = dataHasBeenConvertedToPOJOsEvent.getEventObject();
        ArrayList<Chord> chords = null;

        if(eventObject instanceof ArrayList){

            chords = (ArrayList<Chord>) eventObject;
        }

        if(chords != null){

            int chordsSize = chords.size();

            if(chordsSize > 0){

                AppDataManager appDataManager = getAppDataManager();

                appDataManager.setChordsCount(chordsSize);
                Log.i(TAG, "chordsSize: " + chordsSize);

                StartEventsCallback startEventsCallback = getStartEventsCallback();

                if(startEventsCallback != null){
                    startEventsCallback.changeProgressBarVisibility();
                    startEventsCallback.setProgressBarMax(chordsSize);
                }

                for(Chord chord: chords){
                    chord.setChordShapesTableName(getAppDataManager().getShapeTableNameHelper().getChordShapesTableName(chord.getChordTitle()));
                }

                EventBus.getDefault().post(new AddChordsToLocalDbEvent(chords));
            }
        }
    }

    /**
     * Handles {@link AddChordsToLocalDbEvent}. Adds chords and their shapes to local {@link android.database.sqlite.SQLiteDatabase}
     * @param addChordsToLocalDbEvent {@link AddChordsToLocalDbEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onAddChordsToLocalDbEvent(AddChordsToLocalDbEvent addChordsToLocalDbEvent){
        Object eventObject = addChordsToLocalDbEvent.getEventObject();
        ArrayList<Chord> chords = null;

        if(eventObject instanceof ArrayList){
            chords = (ArrayList<Chord>) eventObject;
        }

        if(chords != null){
            getAppDataManager().addChordsToLocalDB(chords);
        }
    }

    /**
     * Handles {@link SingleChordLoadedToLocalDBEvent}. Updates {@link ProgressBar}
     * @param singleChordLoadedToLocalDBEvent {@link SingleChordLoadedToLocalDBEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSingleChordLoadedToLocalDBEvent(SingleChordLoadedToLocalDBEvent singleChordLoadedToLocalDBEvent){
        Object eventObject = singleChordLoadedToLocalDBEvent.getEventObject();
        String chordTitle = null;

        if(eventObject instanceof String){
            chordTitle = (String) eventObject;
        }

        if(chordTitle != null){

            AppDataManager appDataManager = getAppDataManager();

            appDataManager.setChordsLoadedCount(appDataManager.getChordsLoadedCount() + 1);


            StartEventsCallback startEventsCallback = getStartEventsCallback();

            if(startEventsCallback != null){

                startEventsCallback.updateUserMessage(chordTitle);

                int progress = (appDataManager.getChordsLoadedCount() * 100) / appDataManager.getChordsCount();

                startEventsCallback.setProgressBarProgress(progress);
                Log.i(TAG, "getPbLoading().getProgress(): " + progress);

            }
        }
    }

    /**
     * Handles {@link ChordsUploadedToDatabaseEvent}. Hides {@link ProgressBar}, makes btnGo enabled,
     * so user can process with the app
     * @param chordsUploadedToDatabaseEvent {@link ChordsUploadedToDatabaseEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChordsUploadedToDatabaseEvent(ChordsUploadedToDatabaseEvent chordsUploadedToDatabaseEvent){

       StartEventsCallback startEventsCallback = getStartEventsCallback();

        if(startEventsCallback != null){

            startEventsCallback.writeBooleanToSharedPreferences(true);

            int totalChordsCount = getAppDataManager().getChordsCount();

            startEventsCallback.setProgressBarProgress(totalChordsCount);

            startEventsCallback.updateUserContainerVisibility();

            startEventsCallback.updateButtonGoState(true);
        }

    }

    private AppDataManager getAppDataManager() {
        return mAppDataManager;
    }

    private StartEventsCallback getStartEventsCallback() {
        return mStartEventsCallback;
    }
}
