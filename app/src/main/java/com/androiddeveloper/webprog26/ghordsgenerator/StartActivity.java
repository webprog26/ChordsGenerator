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
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.app_data_manager.AppDataManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

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

                LinearLayout llLoading = getLlLoading();

                if(llLoading.getVisibility() == View.INVISIBLE){
                    llLoading.setVisibility(View.VISIBLE);
                }

                getTvLoading().setText(getString(R.string.reading_chords_data));

                EventBus.getDefault().post(new ReadJSONDataEvent());

            } else {

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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReadJSONDataEvent(ReadJSONDataEvent readJSONDataEvent){
        getAppDataManager().readJSONData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJSONDataHasBeenReadEvent(JSONDataHasBeenReadEvent jsonDataHasBeenReadEvent){
        Log.i(TAG, "onJSONDataHasBeenReadEvent");
        String resultString = jsonDataHasBeenReadEvent.getJSONString();

        if(resultString != null){
            EventBus.getDefault().post(new ConvertDataToPOJOClassesEvent(resultString));
        }
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onConvertDataToPOJOClassesEvent(ConvertDataToPOJOClassesEvent convertDataToPOJOClassesEvent){
        Log.i(TAG, "onConvertDataToPOJOClassesEvent");
        getAppDataManager().convertJSONDateToPOJOClasses(convertDataToPOJOClassesEvent.getJsonString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataHasBeenConvertedToPOJOsEvent(DataHasBeenConvertedToPOJOsEvent dataHasBeenConvertedToPOJOsEvent){
        Log.i(TAG, "onDataHasBeenConvertedToPOJOsEvent");
        ArrayList<Chord> chords = dataHasBeenConvertedToPOJOsEvent.getChords();

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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onAddChordsToLocalDbEvent(AddChordsToLocalDbEvent addChordsToLocalDbEvent){
        getAppDataManager().addChordsToLocalDB(addChordsToLocalDbEvent.getChords());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSingleChordLoadedToLocalDBEvent(SingleChordLoadedToLocalDBEvent singleChordLoadedToLocalDBEvent){
        AppDataManager appDataManager = getAppDataManager();

        appDataManager.setChordsLoadedCount(appDataManager.getChordsLoadedCount() + 1);

        getTvLoading().setText(getString(R.string.loading_text, singleChordLoadedToLocalDBEvent.getChordTitle()));
        getPbLoading().setProgress((appDataManager.getChordsLoadedCount() * 100) / appDataManager.getChordsCount());
        Log.i(TAG, "getPbLoading().getProgress(): " + getPbLoading().getProgress());
    }

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
