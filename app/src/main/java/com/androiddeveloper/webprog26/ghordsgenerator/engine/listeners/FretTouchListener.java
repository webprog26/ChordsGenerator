package com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.PlayShapeFragmentManager;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;


/**
 * Fret touches listener
 */

public class FretTouchListener implements View.OnTouchListener {

    private static final String TAG = "FretTouchListener";


    private final PlayShapeFragmentManager mPlayShapeFragmentManager;
    private float touchPointX;

    public FretTouchListener(PlayShapeFragmentManager playShapeFragmentManager) {
        this.mPlayShapeFragmentManager = playShapeFragmentManager;
        setTouchPointX(0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final PlayShapeFragmentManager playShapeFragmentManager = getPlayShapeFragmentManager();

        switch (event.getAction() & MotionEvent.ACTION_MASK){

            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                setTouchPointX(event.getX());

                if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(0).getStartX()
                        && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(0).getEndX()
                        && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(0).getPlayableY()){
                    Log.i(TAG, "1");
                    Note note = playShapeFragmentManager.getFretboard().getGuitarString(0).getNote();

                    if(note != null){
                        playShapeFragmentManager.playNote(note.getNoteSound());
                    }
                }

                if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(1).getStartX()
                        && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(1).getEndX()
                        && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(1).getPlayableY()){
                    Log.i(TAG, "2");
                    Note note = playShapeFragmentManager.getFretboard().getGuitarString(1).getNote();

                    if(note != null){
                        playShapeFragmentManager.playNote(note.getNoteSound());
                    }
                }

                if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(2).getStartX()
                        && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(2).getEndX()
                        && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(2).getPlayableY()){
                    Log.i(TAG, "3");
                    Note note = playShapeFragmentManager.getFretboard().getGuitarString(2).getNote();

                    if(note != null){
                        playShapeFragmentManager.playNote(note.getNoteSound());
                    }
                }

                if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(3).getStartX()
                        && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(3).getEndX()
                        && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(3).getPlayableY()){
                    Log.i(TAG, "4");
                    Note note = playShapeFragmentManager.getFretboard().getGuitarString(3).getNote();

                    if(note != null){
                        playShapeFragmentManager.playNote(note.getNoteSound());
                    }
                }

                if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(4).getStartX()
                        && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(4).getEndX()
                        && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(4).getPlayableY()){
                    Log.i(TAG, "5");
                    Note note = playShapeFragmentManager.getFretboard().getGuitarString(4).getNote();

                    if(note != null){
                        playShapeFragmentManager.playNote(note.getNoteSound());
                    }
                }

                if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(5).getStartX()
                        && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(5).getEndX()
                        && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(5).getPlayableY()){
                    Log.i(TAG, "6");
                    Note note = playShapeFragmentManager.getFretboard().getGuitarString(5).getNote();

                    if(note != null){
                        playShapeFragmentManager.playNote(note.getNoteSound());
                    }
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");

                float currentTouchPointX = event.getX();

                if(Math.abs(currentTouchPointX - getTouchPointX()) > 40){

                    if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(0).getStartX()
                            && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(0).getEndX()
                            && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(0).getPlayableY()){
                        Log.i(TAG, "1");
                        Note note = playShapeFragmentManager.getFretboard().getGuitarString(0).getNote();

                        if(note != null){
                            playShapeFragmentManager.playNote(note.getNoteSound());
                        }
                    }

                    if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(1).getStartX()
                            && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(1).getEndX()
                            && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(1).getPlayableY()){
                        Log.i(TAG, "2");
                        Note note = playShapeFragmentManager.getFretboard().getGuitarString(1).getNote();

                        if(note != null){
                            playShapeFragmentManager.playNote(note.getNoteSound());
                        }
                    }

                    if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(2).getStartX()
                            && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(2).getEndX()
                            && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(2).getPlayableY()){
                        Log.i(TAG, "3");
                        Note note = playShapeFragmentManager.getFretboard().getGuitarString(2).getNote();

                        if(note != null){
                            playShapeFragmentManager.playNote(note.getNoteSound());
                        }
                    }

                    if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(3).getStartX()
                            && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(3).getEndX()
                            && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(3).getPlayableY()){
                        Log.i(TAG, "4");
                        Note note = playShapeFragmentManager.getFretboard().getGuitarString(3).getNote();

                        if(note != null){
                            playShapeFragmentManager.playNote(note.getNoteSound());
                        }
                    }

                    if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(4).getStartX()
                            && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(4).getEndX()
                            && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(4).getPlayableY()){
                        Log.i(TAG, "5");
                        Note note = playShapeFragmentManager.getFretboard().getGuitarString(4).getNote();

                        if(note != null){
                            playShapeFragmentManager.playNote(note.getNoteSound());
                        }
                    }

                    if(event.getX() > playShapeFragmentManager.getFretboard().getGuitarString(5).getStartX()
                            && event.getX() < playShapeFragmentManager.getFretboard().getGuitarString(5).getEndX()
                            && event.getY() > playShapeFragmentManager.getFretboard().getGuitarString(5).getPlayableY()){
                        Log.i(TAG, "6");
                        Note note = playShapeFragmentManager.getFretboard().getGuitarString(5).getNote();

                        if(note != null){
                            playShapeFragmentManager.playNote(note.getNoteSound());
                        }
                    }

                    setTouchPointX(currentTouchPointX);
                }
                return false;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                setTouchPointX(0);
                break;
        }

        return true;
    }


    private PlayShapeFragmentManager getPlayShapeFragmentManager() {
        return mPlayShapeFragmentManager;
    }

    private float getTouchPointX() {
        return touchPointX;
    }

    private void setTouchPointX(float touchPointX) {
        this.touchPointX = touchPointX;
    }
}
