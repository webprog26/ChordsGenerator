package com.androiddeveloper.webprog26.ghordsgenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.AddChordsToLocalDbEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordsUploadedToDatabaseEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ConvertDataToPOJOClassesEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.DataHasBeenConvertedToPOJOsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.JSONDataHasBeenReadEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ReadJSONDataEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.SingleChordLoadedToLocalDBEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.ShapeTableNameHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.ButtonGoUpdater;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.ProgressUpdater;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.UserMessageWhileLoadingUpdater;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.app_data_manager.AppDataManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements ProgressUpdater, UserMessageWhileLoadingUpdater, ButtonGoUpdater {

    private static final String TAG = "StartActivity_TAG";

    private static final String IS_JSON_HAS_BEEN_READ_TAG = "is_json_has_been_read_tag";

    @BindView(R.id.iv_app_logo)
    ImageView mIvAppLogo;

    @BindView(R.id.ll_loading)
    LinearLayout mLlLoading;

    @BindView(R.id.tv_loading)
    TextView mTvLoading;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @BindView(R.id.btn_go)
    Button mBtnGo;

    private SharedPreferences mSharedPreferences;
    private AppDataManager mAppDataManager;
    private ShapeTableNameHelper mShapeTableNameHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mAppDataManager = new AppDataManager(getAssets());
        mShapeTableNameHelper = new ShapeTableNameHelper(getResources());

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        getBtnGo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences();

        final Button btnGo = getBtnGo();


        if(sharedPreferences != null){

            if(!sharedPreferences.getBoolean(IS_JSON_HAS_BEEN_READ_TAG, false)){
                //this is first launch or app's data was deleted, so we should read it from JSON

                LinearLayout llLoading = getLlLoading();

                //show ProgressBar
                if(llLoading.getVisibility() == View.INVISIBLE){
                    llLoading.setVisibility(View.VISIBLE);
                }

                getTvLoading().setText(getString(R.string.reading_chords_data));

                EventBus.getDefault().post(new ReadJSONDataEvent());

            } else {
                //app data exists, enable user to process
                if(!btnGo.isEnabled()){
                    btnGo.setEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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

                ProgressBar pbLoading = getPbLoading();

                getAppDataManager().setChordsCount(chordsSize);
                Log.i(TAG, "chordsSize: " + chordsSize);

                if(pbLoading.getVisibility() == View.INVISIBLE){
                    pbLoading.setVisibility(View.VISIBLE);
                }

                pbLoading.setMax(chordsSize);

                for(Chord chord: chords){
                    chord.setChordShapesTableName(getShapeTableNameHelper().getChordShapesTableName(chord.getChordTitle()));
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

            getTvLoading().setText(getString(R.string.loading_text, chordTitle));
            getPbLoading().setProgress((appDataManager.getChordsLoadedCount() * 100) / appDataManager.getChordsCount());
            Log.i(TAG, "getPbLoading().getProgress(): " + getPbLoading().getProgress());

        }
    }

    /**
     * Handles {@link ChordsUploadedToDatabaseEvent}. Hides {@link ProgressBar}, makes btnGo enabled,
     * so user can process with the app
     * @param chordsUploadedToDatabaseEvent {@link ChordsUploadedToDatabaseEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChordsUploadedToDatabaseEvent(ChordsUploadedToDatabaseEvent chordsUploadedToDatabaseEvent){

        getSharedPreferences().edit().putBoolean(IS_JSON_HAS_BEEN_READ_TAG, true).apply();

        Button btnGo = getBtnGo();
        LinearLayout llLoading = getLlLoading();
        ProgressBar pbLoading = getPbLoading();

        if(llLoading.getVisibility() == View.VISIBLE){

            int totalChordsCount = getAppDataManager().getChordsCount();

            if(pbLoading.getProgress() < totalChordsCount){
                pbLoading.setProgress(totalChordsCount);
            }

            llLoading.setVisibility(View.INVISIBLE);
        }

        if(!btnGo.isEnabled()){

            btnGo.setEnabled(true);
        }
    }


    @Override
    public void changeProgressBarVisibility() {
        ProgressBar progressBar = getPbLoading();

        if(progressBar != null){

            progressBar.setVisibility(progressBar.getVisibility() == View.VISIBLE ? View.INVISIBLE: View.VISIBLE);

        }
    }

    @Override
    public void setProgressBarMax(int max) {
        ProgressBar progressBar = getPbLoading();

        if(progressBar != null) {

            progressBar.setMax(max);

        }
    }

    @Override
    public void setProgressBarProgress(int progress) {
        ProgressBar progressBar = getPbLoading();

        if(progressBar != null) {

            progressBar.setProgress(progress);

        }
    }

    @Override
    public void updateUserContainerVisibility() {
        LinearLayout llLoading = getLlLoading();

        if(llLoading != null){

            llLoading.setVisibility(llLoading.getVisibility() == View.VISIBLE ? View.VISIBLE: View.INVISIBLE);

        }
    }

    @Override
    public void updateUserMessage(String userMessageText) {

        TextView tvLoading = getTvLoading();

        if(tvLoading != null){

            tvLoading.setText(getString(R.string.loading_text, userMessageText));

        }
    }

    @Override
    public void updateButtonGoState(boolean isEnabled) {
        Button btnGo = getBtnGo();

        if(btnGo != null){
            btnGo.setEnabled(isEnabled);
        }
    }

    private SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    private LinearLayout getLlLoading() {
        return mLlLoading;
    }

    private TextView getTvLoading() {
        return mTvLoading;
    }

    private ProgressBar getPbLoading() {
        return mPbLoading;
    }

    private ImageView getIvAppLogo() {
        return mIvAppLogo;
    }

    private Button getBtnGo() {
        return mBtnGo;
    }

    private AppDataManager getAppDataManager() {
        return mAppDataManager;
    }

    private ShapeTableNameHelper getShapeTableNameHelper() {
        return mShapeTableNameHelper;
    }
}
