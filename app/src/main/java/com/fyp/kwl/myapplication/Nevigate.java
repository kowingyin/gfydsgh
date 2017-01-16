package com.fyp.kwl.myapplication;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Nevigate extends AppCompatActivity {
    TextToSpeech ttsobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nevigate_nevigate);

        ttsobject = MainActivity.ttsobject;
    }
}
