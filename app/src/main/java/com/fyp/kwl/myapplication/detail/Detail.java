package com.fyp.kwl.myapplication.detail;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.kwl.myapplication.MainActivity;
import com.fyp.kwl.myapplication.Nevigate;
import com.fyp.kwl.myapplication.R;
import com.fyp.kwl.myapplication.search.GeneralSearch;

public class Detail extends AppCompatActivity {
    String building;
    TextView title, content;
    Button nevigation;
    TextToSpeech ttsobject;
    int timeOfClick = 0;
    public int result;
    private final int REQ_CODE_SPEECH_INPUT = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_detail);

//        building = Intent.getIntent("building");
        nevigation = (Button) findViewById(R.id.button);
        title = (TextView) findViewById(R.id.tvTitle);
        content = (TextView) findViewById(R.id.tvContent);
        ttsobject = MainActivity.ttsobject;

        nevigate();
    }

    private void nevigate() {
        nevigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final View v = view;
                timeOfClick++;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  output sound
                        if (timeOfClick == 1){
                            speak("");
                            //  open activity
                        }else if (timeOfClick == 2){
                            //  detail

                        }
                        timeOfClick = 0;
                    }
                } , 500);

            }
        });
        Intent intent = new Intent(Detail.this, Nevigate.class);

    }

    public void speak(String speakedText){
        if ( result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA ){
            Toast.makeText(this, "Feature not support on your device", Toast.LENGTH_SHORT).show();
        }else {
            ttsobject.speak(speakedText, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
