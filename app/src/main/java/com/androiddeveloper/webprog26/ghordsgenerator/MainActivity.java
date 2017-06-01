package com.androiddeveloper.webprog26.ghordsgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordShapeImageClickEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers.ShapeTableNameHelper;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.MainAppScreenManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.SpinnerReseter;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners.SpinnerListener;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordInfoHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SpinnerReseter{

    private static final String TAG = "MainActivity_TAG";

    private static final int DEFAULT_SPINNER_POSITION = 0;

    @BindView(R.id.sp_chord_title)
    AppCompatSpinner mSpChordTitle;

    @BindView(R.id.sp_chord_type)
    AppCompatSpinner mSpChordType;

    @BindView(R.id.sp_chord_alteration)
    AppCompatSpinner mSpChordAlteration;

    private MainAppScreenManager mainAppScreenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainAppScreenManager = new MainAppScreenManager(getSupportFragmentManager(), R.id.fl_content, this);

        //Initializing SpinnerListener instance

        SpinnerListener spinnerListener = new SpinnerListener(mainAppScreenManager, this);

        getSpChordTitle().setOnItemSelectedListener(spinnerListener);
        getSpChordType().setOnItemSelectedListener(spinnerListener);
        getSpChordAlteration().setOnItemSelectedListener(spinnerListener);

        getSpChordTitle().setOnTouchListener(spinnerListener);
        getSpChordType().setOnTouchListener(spinnerListener);
        getSpChordAlteration().setOnTouchListener(spinnerListener);

        //Todo load last used chord
        Chord chord = new Chord();
        chord.setChordTitle("C");
        chord.setChordType(Chord.NO_TYPE);
        chord.setChordAlteration(Chord.NO_ALTERATION);

        mainAppScreenManager.setCurrentChord(chord);
        mainAppScreenManager.setFragmentWithListOfChordImages(chord);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChordShapeImageClickEvent(ChordShapeImageClickEvent chordShapeImageClickEvent){
        Log.i(TAG, "onChordShapeImageClickEvent");
        final MainAppScreenManager mainAppScreenManager = getMainAppScreenManager();

        if(mainAppScreenManager != null){

            String chordTitle = mainAppScreenManager.getCurrentChord().getChordTitle();

            Intent playShapeIntent = new Intent(MainActivity.this, PlayShapeActivity.class);

            playShapeIntent.putExtra(PlayShapeActivity.CHORD_INFO_HOLDER, new ChordInfoHolder(
                    chordTitle,
                    mainAppScreenManager.getChordSecondTitleHelper().getChordSecondTitle(chordTitle),
                    chordShapeImageClickEvent.getClickedShapePosition(),
                    mainAppScreenManager.getShapeTableNameHelper().getChordShapesTableName(chordTitle)
            ));
            startActivity(playShapeIntent);

        }


    }

    @Override
    public void resetChordsParamsSpinner() {
        getSpChordAlteration().setSelection(DEFAULT_SPINNER_POSITION);
    }

    @Override
    public void setChordsTitleSpinnerPosition(int position) {
        getSpChordTitle().setSelection(position);
    }

    private AppCompatSpinner getSpChordTitle() {
        return mSpChordTitle;
    }

    private AppCompatSpinner getSpChordType() {
        return mSpChordType;
    }

    private AppCompatSpinner getSpChordAlteration() {
        return mSpChordAlteration;
    }

    private MainAppScreenManager getMainAppScreenManager() {
        return mainAppScreenManager;
    }
}
