package com.androiddeveloper.webprog26.ghordsgenerator.engine.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordsUploadedToDatabaseEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.SingleChordLoadedToLocalDBEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.MutedStringsHolder;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Note;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by webpr on 31.05.2017.
 */

public class ChordsDBProvider {

    private static final String TAG = "ChordsDBProvider";


    private static final String SHAPE_NOTES = "shape_notes";

    private static final String SHAPE_NOTE_TITLE = "shape_note_title";
    private static final String SHAPE_NOTE_FRET = "shape_note_fret";
    private static final String SHAPE_NOTE_FINGER_INDEX = "shape_note_finger_index";
    private static final String SHAPE_NOTE_PLACE = "note_place";
    private static final String SHAPE_NOTE_SOUND_PATH = "note_sound_path";

    private final ChordsDBHelper mChordsDBHelper;

    public ChordsDBProvider(final Context context) {
        this.mChordsDBHelper = new ChordsDBHelper(context);
    }

    private ChordsDBHelper getChordsDBHelper() {
        return mChordsDBHelper;
    }

    public void insertChordsToDB(final ArrayList<Chord> chords) throws Exception{
        for(Chord chord: chords){

            if(insertChordToDb(chord) == -1){
                throw new Exception("Something went wrong while uploading chord " + chord.getChordTitle() + " to local db");
            }

            EventBus.getDefault().post(new SingleChordLoadedToLocalDBEvent(chord.getChordTitle()));
        }

        EventBus.getDefault().post(new ChordsUploadedToDatabaseEvent());
    }

    /**
     * Inserts {@link Chord} to local {@link android.database.sqlite.SQLiteDatabase}
     * @param chord {@link Chord}
     * @return long
     */
    private long insertChordToDb(Chord chord){
        Log.i(TAG, "insertChordToDb() " + chord.getChordTitle());
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChordsDBHelper.CHORD_TITLE, chord.getChordTitle());
        contentValues.put(ChordsDBHelper.CHORD_SECOND_TITLE, chord.getChordSecondTitle());
        contentValues.put(ChordsDBHelper.CHORD_TYPE, chord.getChordType());
        contentValues.put(ChordsDBHelper.CHORD_ALTERATION, chord.getChordAlteration());

        String chordShapesTableName = chord.getChordShapesTableName();

        if(chordShapesTableName != null){

            contentValues.put(ChordsDBHelper.CHORD_SHAPES_TABLE_NAME, chordShapesTableName);

            for(ChordShape chordShape: chord.getChordShapes()){
                insertChordShapeToDb(chordShapesTableName, chordShape);
            }
        }
        return getChordsDBHelper().getWritableDatabase().insert(ChordsDBHelper.CHORDS_TABLE, null, contentValues);
    }

    /**
     * Inserts {@link ChordShape} to local {@link android.database.sqlite.SQLiteDatabase}
     * @param shapesTableTitle {@link String}
     * @param chordShape {@link ChordShape}
     */
    private void insertChordShapeToDb(String shapesTableTitle, ChordShape chordShape){
        Log.i(TAG, "insertChordShapeToDb() " + chordShape.getShapePosition() + " to table " + shapesTableTitle);
        Log.i(TAG, "shape has bar " + chordShape.isHasBar());

        int startBarPlace = ChordShape.NO_BAR_PLACE;
        int endBarPlace = ChordShape.NO_BAR_PLACE;

        MutedStringsHolder mutedStringsHolder = chordShape.getMutedStringsHolder();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ChordsDBHelper.SHAPE_POSITION, chordShape.getShapePosition());
        contentValues.put(ChordsDBHelper.SHAPE_START_FRET_POSITON, chordShape.getStartFretPosition());



        String notesListString = getNotesArrayToString(chordShape.getNotes());

        if(notesListString != null && notesListString.length() > 0){
            contentValues.put(ChordsDBHelper.SHAPE_NOTES, notesListString);
        }

        contentValues.put(ChordsDBHelper.SHAPE_IMAGE_TITLE, chordShape.getImageTitle());


        contentValues.put(ChordsDBHelper.SHAPE_HAS_MUTED_STRINGS, String.valueOf(chordShape.isHasMutedStrings()));

        contentValues.put(ChordsDBHelper.SHAPE_SIXTH_STRING_MUTED, String.valueOf(mutedStringsHolder.isSixthStringMuted()));
        contentValues.put(ChordsDBHelper.SHAPE_FIFTH_STRING_MUTED, String.valueOf(mutedStringsHolder.isFifthStringMuted()));
        contentValues.put(ChordsDBHelper.SHAPE_FOURTH_STRING_MUTED, String.valueOf(mutedStringsHolder.isFourthStringMuted()));
        contentValues.put(ChordsDBHelper.SHAPE_THIRD_STRING_MUTED, String.valueOf(mutedStringsHolder.isThirdStringMuted()));
        contentValues.put(ChordsDBHelper.SHAPE_SECOND_STRING_MUTED, String.valueOf(mutedStringsHolder.isSecondStringMuted()));
        contentValues.put(ChordsDBHelper.SHAPE_FIRST_STRING_MUTED, String.valueOf(mutedStringsHolder.isFirstStringMuted()));

        if(chordShape.isHasBar()){
            startBarPlace = chordShape.getStartBarPlace();
            endBarPlace = chordShape.getEndBarPlace();
        }

        contentValues.put(ChordsDBHelper.SHAPE_HAS_BAR, String.valueOf(chordShape.isHasBar()));
        contentValues.put(ChordsDBHelper.SHAPE_BAR_START_PLACE, startBarPlace);
        contentValues.put(ChordsDBHelper.SHAPE_BAR_END_PLACE, endBarPlace);
        Log.i(TAG, "contentValues.size(): " + contentValues.size());
        getChordsDBHelper().getWritableDatabase().insert(shapesTableTitle, null, contentValues);
    }

    private String getNotesArrayToString(ArrayList<Note> notes){
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for(Note note: notes){
            JSONObject singleNoteJsonObject = new JSONObject();

            try {
                singleNoteJsonObject.put(SHAPE_NOTE_TITLE, note.getNoteTitle());
                singleNoteJsonObject.put(SHAPE_NOTE_FRET, note.getNoteFret());
                singleNoteJsonObject.put(SHAPE_NOTE_FINGER_INDEX, note.getNoteFingerIndex());
                singleNoteJsonObject.put(SHAPE_NOTE_PLACE, note.getNotePlace());
                singleNoteJsonObject.put(SHAPE_NOTE_SOUND_PATH, note.getNoteSoundPath());
            } catch (JSONException e){
                e.printStackTrace();
            }

            jsonArray.put(singleNoteJsonObject);

        }

        try {
            json.put(SHAPE_NOTES, jsonArray);
            Log.i(TAG, json.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }

        return json.toString();
    }
}
