package com.androiddeveloper.webprog26.ghordsgenerator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.JSONDataHasBeenReadEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ReadJSONDataEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.AppDataManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity_TAG";

    private static final String IS_JSON_HAS_BEEN_READ_TAG = "is_json_has_been_read_tag";

    @BindView(R.id.iv_app_logo)
    ImageView mIvAppLogo;

    @BindView(R.id.btn_go)
    Button mBtnGo;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
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

    private SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    private ImageView getIvAppLogo() {
        return mIvAppLogo;
    }

    private Button getBtnGo() {
        return mBtnGo;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReadJSONDataEvent(ReadJSONDataEvent readJSONDataEvent){
        new AppDataManager(getAssets()).readJSONData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJSONDataHasBeenReadEvent(JSONDataHasBeenReadEvent jsonDataHasBeenReadEvent){

        String resultString = jsonDataHasBeenReadEvent.getJSONString();

        if(resultString != null){
            Log.i(TAG, resultString);
        }
    }
}
