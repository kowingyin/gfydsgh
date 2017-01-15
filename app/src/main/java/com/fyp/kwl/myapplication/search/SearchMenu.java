package com.fyp.kwl.myapplication.search;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fyp.kwl.myapplication.MainActivity;
import com.fyp.kwl.myapplication.R;

public class SearchMenu extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;
    TextToSpeech ttsobject;
    public int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_menu);

        lv = (ListView)findViewById(R.id.lvSearchMenu);

        ttsobject = MainActivity.ttsobject;

        listSearchMenu();
        clickOption();
    }

    private void clickOption() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeOfClick++;
                SearchMenu.position = position;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  output sound
                        if (timeOfClick == 1){
                            switch (SearchMenu.position){
                                case 0:
//                                    Toast.makeText(MainActivity.this, "single favourite cliked", Toast.LENGTH_SHORT).show();
//                                    speak("Searching by input");
                                    speak("跟據輸入搜尋");
                                    break;
                                case 1:
//                                    Toast.makeText(MainActivity.this, "single search clicked", Toast.LENGTH_SHORT).show();
//                                    speak("searching by distinct");
                                    speak("跟據選擇地區和類型搜尋");
                                    break;
                            }
                            //  open activity
                        }else if (timeOfClick == 2){
                            Intent intent = null;
                            switch (SearchMenu.position){
                                case 0:
//                                    Toast.makeText(MainActivity.this, "favorite cliked", Toast.LENGTH_SHORT).show();
//                                    speak("move to general searching page");
                                    speak("移至普通搜尋");
                                    intent = new Intent(SearchMenu.this, GeneralSearch.class);
                                    break;
                                case 1:
//                                    Toast.makeText(MainActivity.this, "search clicked", Toast.LENGTH_SHORT).show();
//                                    speak("move to selecting ");
                                    speak("移至地區搜尋");
                                    intent = new Intent(SearchMenu.this, SearchMenu.class);
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

    private void listSearchMenu() {
        //  Create list of items
        String[] showItems = {"By Input", "By Distinct"};

        //  Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.menu_list_show_item, showItems);

        //  Configure the list view to the Adapter

        lv.setAdapter(adapter);
    }

    public void speak(String speakedText){
        if ( result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA ){
            Toast.makeText(this, "Feature not support on your device", Toast.LENGTH_SHORT).show();
        }else {
            ttsobject.speak(speakedText, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
