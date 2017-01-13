package com.fyp.kwl.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;
    public TextToSpeech ttsobject;
    public int result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.lvMainMenu);
        listMainMenu();
        clickOption();


        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit( int status ) {
                if ( status == TextToSpeech.SUCCESS ){
                    result = ttsobject.setLanguage(Locale.US);
                }else {
                    Toast.makeText(MainActivity.this, "Do not support", Toast.LENGTH_LONG).show();
                }

            }
        });

//        Set languages = ttsobject.getAvailableLanguages();
//        String[] supportedLanguage = (String[]) languages.toArray(new String[languages.size()]);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, supportedLanguage);
    }

    private void listMainMenu() {
        //  Create list of items
        String[] showItems = {"Favourite", "Searching", "Setting"};

        //  Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.menu_list_show_item, showItems);

        //  Configure the list view to the Adapter

        lv.setAdapter(adapter);
    }

    private void clickOption() {


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeOfClick++;
                MainActivity.position = position;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (timeOfClick == 1){
                            switch (MainActivity.position){
                                case 0:
                                    Toast.makeText(MainActivity.this, "single favourite cliked", Toast.LENGTH_SHORT).show();
                                    speak("favorite");
                                    break;
                                case 1:
                                    Toast.makeText(MainActivity.this, "single search clicked", Toast.LENGTH_SHORT).show();
                                    speak("search");
                                    break;
                                case 2:
                                    Toast.makeText(MainActivity.this, "single setting clicked", Toast.LENGTH_SHORT).show();
                                    speak("setting");
                                    break;
                            }
                        }else if (timeOfClick == 2){
                            switch (MainActivity.position){
                                case 0:
                                    Toast.makeText(MainActivity.this, "favourite cliked", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Toast.makeText(MainActivity.this, "search clicked", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(MainActivity.this, "setting clicked", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        timeOfClick = 0;
                    }
                } , 500);
            }
        });
    }
    public void speak(String speakedText){
        if ( result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA ){
            Toast.makeText(this, "Feature not support on your device", Toast.LENGTH_SHORT).show();
        }else {
            ttsobject.speak(speakedText, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
