package com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

/**
 * Single note image click listener
 */

public class NoteClickListener implements View.OnClickListener {

    private final String TAG = "NoteClickListener";

    private static final int NOTE_IMAGE_FADE_ANIMATION_DURATION = 200;

    private final Note note;

    public NoteClickListener(Note note) {
        this.note = note;
    }

    @Override
    public void onClick(View v) {

        Note note = getNote();

        if(note != null){

            animateChanges((ImageView) v, note);
        }
    }

    private Note getNote() {
        return note;
    }

    private void animateChanges(final ImageView noteImageView, final Note note){

        AnimatorSet noteAnimatorSet = getNoteImageFadeAnimatorSet(noteImageView, note);
        noteAnimatorSet.start();
    }

    private ObjectAnimator getNoteImageFadeOutAnimator(final ImageView noteImageView, final Note note){
        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(noteImageView, View.ALPHA, 1f, 0);
        fadeOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Drawable noteImageDrawable;

                Log.i(TAG, "isFingerIndexVisible(): " + note.isFingerIndexVisible());

                if(!note.isFingerIndexVisible()){
                    noteImageDrawable = note.getNoteFingerIndexDrawable();
                } else {
                    noteImageDrawable = note.getNoteTitleDrawable();
                }

                if(noteImageDrawable != null){
                    noteImageView.setImageDrawable(noteImageDrawable);
                    note.setFingerIndexVisible(!note.isFingerIndexVisible());
                }
            }
        });
        fadeOutAnimator.setDuration(NOTE_IMAGE_FADE_ANIMATION_DURATION);

        return fadeOutAnimator;
    }

    private ObjectAnimator getNoteImageFadeInAnimator(final ImageView noteImageView){
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(noteImageView, View.ALPHA, 0, 1f);
        fadeInAnimator.setDuration(200);

        return fadeInAnimator;
    }

    private AnimatorSet getNoteImageFadeAnimatorSet(final ImageView noteImageView, final Note note){
        AnimatorSet noteAnimatorSet = new AnimatorSet();
        noteAnimatorSet.playSequentially(getNoteImageFadeOutAnimator(noteImageView, note), getNoteImageFadeInAnimator(noteImageView));
        noteAnimatorSet.setInterpolator(new LinearInterpolator());

        return noteAnimatorSet;
    }
}
