package com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.MainAppScreenManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.SpinnerReseter;


/**
 * Listener for user interaction with layout_spinners
 */

public class SpinnerListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener{

    private static final String TAG = "SpinnerListener";
    private boolean isBlocked;

    private static final String EMPTY_POSITION = "--";

    private static final String FLAT_CHORD_PARAM = "b";
    private static final String SHARP_CHORD_PARAM = "#";

    private static final String C_CHORD = "C";
    private static final String F_CHORD = "F";

    private static final String H_CHORD = "H";
    private static final String E_CHORD = "E";


    private static final int C_POSITION = 0;
    private static final int F_POSITION = 3;
    private static final int H_POSITION = 7;
    private static final int E_POSITION = 2;

    private final SpinnerReseter mSpinnerReseter;
    private final MainAppScreenManager mainAppScreenManager;
    //Empty Chord instance to ctore parameters chosen vy user via layout_spinners
    private Chord mChord = new Chord();

    public SpinnerListener(MainAppScreenManager mainAppScreenManager, SpinnerReseter spinnerReseter) {
        this.mainAppScreenManager = mainAppScreenManager;
        this.mSpinnerReseter = spinnerReseter;
        setBlocked(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        setBlocked(false);
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemSelected");
        Chord chord = getChord();
        chord.setChordType(Chord.NO_TYPE);
        chord.setChordAlteration(Chord.NO_ALTERATION);


        switch (adapterView.getId()){
           case R.id.sp_chord_title:
               chord.setChordTitle(adapterView.getSelectedItem().toString());
               break;
           case R.id.sp_chord_type:
                if(!adapterView.getSelectedItem().toString().equalsIgnoreCase(EMPTY_POSITION)){
                    chord.setChordType(adapterView.getSelectedItem().toString());
                }
               break;
            case R.id.sp_chord_alteration:
                if(!adapterView.getSelectedItem().toString().equalsIgnoreCase(EMPTY_POSITION)) {
                    chord.setChordAlteration(adapterView.getSelectedItem().toString());
                }
                break;
       }
        Log.i(TAG, "isBlocked(): " + isBlocked());
        if(!isBlocked){

        if(chord.getChordTitle() != null && chord.getChordType() != null && chord.getChordAlteration() != null){

            String toChord = null;
            int toPosition = -1;

            //Handling C flat, F flat
            if(chord.getChordAlteration().equalsIgnoreCase(FLAT_CHORD_PARAM)){

                //C flat case
                if(chord.getChordTitle().equalsIgnoreCase(C_CHORD)){
                    toChord = H_CHORD;
                    toPosition = H_POSITION;
                }

                //F flat case
                if(chord.getChordTitle().equalsIgnoreCase(F_CHORD)){
                    toChord = E_CHORD;
                    toPosition = E_POSITION;
                }
            }

            //Handling H sharp, E sharp cases
            if(chord.getChordAlteration().equalsIgnoreCase(SHARP_CHORD_PARAM)){

                //H sharp case
                if(chord.getChordTitle().equalsIgnoreCase(H_CHORD)){
                    toChord = C_CHORD;
                    toPosition = C_POSITION;
                }

                //E sharp case
                if(chord.getChordTitle().equalsIgnoreCase(E_CHORD)){
                    toChord = F_CHORD;
                    toPosition = F_POSITION;
                }
            }


            if(toChord != null){
                getMainAppScreenManager().sendUiMessage(chord, toChord);
                changeChordTitleAndResetParams(toChord, toPosition);
            }

                   Log.i(TAG, "Loading fragment ");
                   getMainAppScreenManager().setCurrentChord(getChord());
                   getMainAppScreenManager().setFragmentWithListOfChordImages(getChord());
                   setBlocked(true);
               }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i(TAG, "onNothingSelected");
    }


    private Chord getChord() {
        return mChord;
    }

    private MainAppScreenManager getMainAppScreenManager(){
        return mainAppScreenManager;
    }


    /**
     * In case of chosing unexisting chord resets layout_spinners and changes user selection to right chord
     * @param chordTitle {@link String}
     * @param position int
     */
    private void changeChordTitleAndResetParams(final String chordTitle, final int position){
        getChord().setChordTitle(chordTitle);
        getChord().setChordAlteration(Chord.NO_ALTERATION);
        getSpinnerReseter().resetChordsParamsSpinner();
        getSpinnerReseter().setChordsTitleSpinnerPosition(position);
    }

    public SpinnerReseter getSpinnerReseter() {
        return mSpinnerReseter;
    }

    private boolean isBlocked() {
        return isBlocked;
    }

    private void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
