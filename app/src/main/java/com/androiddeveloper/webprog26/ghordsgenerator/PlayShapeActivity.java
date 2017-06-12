package com.androiddeveloper.webprog26.ghordsgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.PlayShapeActivityControlsEnabler;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners.ShapesControlButtonsListener;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers.PlayShapeActivityManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordInfoHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayShapeActivity extends AppCompatActivity implements PlayShapeActivityControlsEnabler {

    private static final String TAG = "PlayActivity_TAG";

    public static final String CHORD_INFO_HOLDER = "chord_info_holder";

    private PlayShapeActivityManager mPlayShapeActivityManager;
    private ShapesControlButtonsListener mShapesControlButtonsListener;

    @BindView(R.id.tv_chord_title)
    TextView mTvChordTitle;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.btn_previous)
    Button mBtnPrevious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_shape);



        ButterKnife.bind(this);


        Intent receivedIntent = getIntent();
        //Unpacking received ChordInfoHolder
        if(receivedIntent != null){

            final ChordInfoHolder chordInfoHolder = (ChordInfoHolder) receivedIntent.getSerializableExtra(CHORD_INFO_HOLDER);


            if(chordInfoHolder != null){

                mPlayShapeActivityManager = new PlayShapeActivityManager(getSupportFragmentManager(),
                                                                        R.id.play_chord_activity_content,
                                                                        chordInfoHolder,
                                                                        this);

                Log.i(TAG, "should show shape at " + chordInfoHolder.getClickedShapePosition() + " from shapes table "
                        + chordInfoHolder.getChordShapesTableName());

                mShapesControlButtonsListener = new ShapesControlButtonsListener(mPlayShapeActivityManager);

                //Setting chord title
                getTvChordTitle()
                        .setText(chordInfoHolder.getChordSecondTitle() == null
                                ? getString(R.string.chord_with_one_title, chordInfoHolder.getChordTitle()) :
                                getString(R.string.chord_with_two_titles,
                                        chordInfoHolder.getChordTitle(),
                                        chordInfoHolder.getChordSecondTitle()));

            }
        }

        getBtnNext().setOnClickListener(mShapesControlButtonsListener);
        getBtnPrevious().setOnClickListener(mShapesControlButtonsListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPlayShapeActivityManager().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPlayShapeActivityManager().onResume();
    }

    @Override
    protected void onStop() {
        getPlayShapeActivityManager().onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        if(mPlayShapeActivityManager != null){
            mPlayShapeActivityManager = null;
        }
        super.onDestroy();
    }

    private PlayShapeActivityManager getPlayShapeActivityManager() {
        return mPlayShapeActivityManager;
    }

    private TextView getTvChordTitle() {
        return mTvChordTitle;
    }

    private Button getBtnNext() {
        return mBtnNext;
    }

    private Button getBtnPrevious() {
        return mBtnPrevious;
    }

    @Override
    public void setNextPlayableShapeButtonEnabled(boolean enabled) {
        getBtnNext().setEnabled(enabled);
    }

    @Override
    public void setPreviousPlayableShapeButtonEnabled(boolean enabled) {
        getBtnPrevious().setEnabled(enabled);
    }
}
