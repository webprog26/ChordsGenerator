package com.androiddeveloper.webprog26.ghordsgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks.OnChordShapeImageClickCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.screens_managers.MainAppScreenManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.SpinnerReseter;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners.SpinnerListener;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordInfoHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SpinnerReseter, OnChordShapeImageClickCallback{

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

        mainAppScreenManager = new MainAppScreenManager(getSupportFragmentManager(), R.id.fl_content, this, this);

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
        getMainAppScreenManager().onStart();
    }

    @Override
    protected void onStop() {
        getMainAppScreenManager().onStop();
        super.onStop();
    }

    @Override
    public void resetChordsParamsSpinner() {
        getSpChordAlteration().setSelection(DEFAULT_SPINNER_POSITION);
    }

    @Override
    public void onChordImageClicked(int position) {
        final MainAppScreenManager mainAppScreenManager = getMainAppScreenManager();

        if(mainAppScreenManager != null){

            String chordTitle = mainAppScreenManager.getCurrentChord().getChordTitle();

            if(position != -1){

                Intent playShapeIntent = new Intent(MainActivity.this, PlayShapeActivity.class);
                playShapeIntent.putExtra(PlayShapeActivity.CHORD_INFO_HOLDER, new ChordInfoHolder(
                        chordTitle,
                        mainAppScreenManager.getChordSecondTitleHelper().getChordSecondTitle(chordTitle),
                        position,
                        mainAppScreenManager.getShapeTableNameHelper().getChordShapesTableName(chordTitle)
                ));
                startActivity(playShapeIntent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }
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
