package com.androiddeveloper.webprog26.ghordsgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayShapeActivity extends AppCompatActivity {

    public static final String SHAPES_TABLE_TITLE = "shapes_table_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_shape);
    }
}
