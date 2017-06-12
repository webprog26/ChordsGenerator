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

import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.StartEventsCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.app_data_manager.AppDataManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity implements StartEventsCallback{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mAppDataManager = new AppDataManager(getAssets(), this, getResources());

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
        getAppDataManager().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences();

        final Button btnGo = getBtnGo();


        if(sharedPreferences != null){

            if(!sharedPreferences.getBoolean(IS_JSON_HAS_BEEN_READ_TAG, false)){
                //this is first launch or app's data was deleted, so we should read it from JSON

                updateUserContainerVisibility();

                getTvLoading().setText(getString(R.string.reading_chords_data));

                getAppDataManager().readJSONData();

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
        getAppDataManager().onStop();
        super.onStop();
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

            llLoading.setVisibility(llLoading.getVisibility() == View.VISIBLE ? View.INVISIBLE: View.VISIBLE);

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

    @Override
    public void writeBooleanToSharedPreferences(boolean value) {
        getSharedPreferences().edit().putBoolean(IS_JSON_HAS_BEEN_READ_TAG, true).apply();
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
}
