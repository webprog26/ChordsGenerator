package com.androiddeveloper.webprog26.ghordsgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PlayShapeActivity extends AppCompatActivity {

    private static final String TAG = "PlayActivity_TAG";

    public static final String SHAPES_TABLE_NAME = "shapes_table_title";
    public static final String CLICKED_SHAPE_POSITION = "clicked_shape_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_shape);

        Intent receivedIntent = getIntent();

        if(receivedIntent != null){

            String shapesTableName = receivedIntent.getStringExtra(SHAPES_TABLE_NAME);
            int clickedShapePosition = receivedIntent.getIntExtra(CLICKED_SHAPE_POSITION, -1);

            if(shapesTableName != null && clickedShapePosition != -1){

                Log.i(TAG, "should show shape at " + clickedShapePosition + " from shapes table " + shapesTableName);
            }
        }
    }
}
