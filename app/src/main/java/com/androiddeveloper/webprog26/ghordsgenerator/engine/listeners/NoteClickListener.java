package com.androiddeveloper.webprog26.ghordsgenerator.engine.listeners;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

/**
 * Created by webpr on 30.05.2017.
 */

public class NoteClickListener implements View.OnClickListener {

    private final Note note;

    public NoteClickListener(Note note) {
        this.note = note;
    }

    @Override
    public void onClick(View v) {

        Note note = getNote();

        if(note != null){

            Drawable noteImageDrawable;
            if(!note.isFingerIndexVisible()){
                noteImageDrawable = note.getNoteFingerIndexDrawable();
                note.setFingerIndexVisible(true);
            } else {
                noteImageDrawable = note.getNoteTitleDrawable();
                note.setFingerIndexVisible(false);
            }

            if(noteImageDrawable != null){
                ((ImageView) v).setImageDrawable(noteImageDrawable);
            }

        }
    }

    private Note getNote() {
        return note;
    }
}
