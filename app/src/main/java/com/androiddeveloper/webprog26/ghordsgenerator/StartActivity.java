package com.androiddeveloper.webprog26.ghordsgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    private static final String IS_JSON_HAS_BEEN_READ_TAG = "is_json_has_been_read_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
}
