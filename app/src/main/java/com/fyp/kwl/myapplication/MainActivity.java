package com.fyp.kwl.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fyp.kwl.myapplication.search.SearchMenu;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;
    public static TextToSpeech ttsobject;
    public static int result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit( int status ) {
                if ( status == TextToSpeech.SUCCESS ){
                    result = ttsobject.setLanguage(Locale.US);
                    //new Locale("yue")
                }else {
                    Toast.makeText(MainActivity.this, "Do not support", Toast.LENGTH_LONG).show();
                }

            }
        });

        lv = (ListView)findViewById(R.id.lvMainMenu);
        listMainMenu();
        clickOption();



        speak("menu");




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
                        //  output sound
                        if (timeOfClick == 1){
                            switch (MainActivity.position){
                                case 0:
//                                    Toast.makeText(MainActivity.this, "single favourite cliked", Toast.LENGTH_SHORT).show();
                                    speak("favorite");
//                                    speak("我的最愛");
                                    break;
                                case 1:
//                                    Toast.makeText(MainActivity.this, "single search clicked", Toast.LENGTH_SHORT).show();
                                    speak("search");
//                                    speak("搜尋");
                                    break;
                                case 2:
//                                    Toast.makeText(MainActivity.this, "single setting clicked", Toast.LENGTH_SHORT).show();
                                    speak("setting");
//                                    speak("設定");
                                    break;
                            }
                            //  open activity
                        }else if (timeOfClick == 2){
                            Intent intent = null;
                            switch (MainActivity.position){
                                case 0:
//                                    Toast.makeText(MainActivity.this, "favorite cliked", Toast.LENGTH_SHORT).show();
                                    speak("move to my favorite page");
                                    intent = new Intent(MainActivity.this, SearchMenu.class);
                                    break;
                                case 1:
//                                    Toast.makeText(MainActivity.this, "search clicked", Toast.LENGTH_SHORT).show();
                                    speak("move to searching page");
                                    intent = new Intent(MainActivity.this, SearchMenu.class);
                                    break;
                                case 2:
//                                    Toast.makeText(MainActivity.this, "setting clicked", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(MainActivity.this, SearchMenu.class);
                                    speak("move to setting page");
                                    break;
                            }
                            startActivity(intent);
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
