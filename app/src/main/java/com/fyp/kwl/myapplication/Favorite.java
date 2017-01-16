package com.fyp.kwl.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.kwl.myapplication.detail.Detail;
import com.fyp.kwl.myapplication.search.GeneralSearch;

public class Favorite extends AppCompatActivity {
    ListView lv;
    private ArrayAdapter<String> adapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public int result;
    TextToSpeech ttsobject;
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    boolean myItem = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_favorite);

        lv = (ListView)findViewById(R.id.list);
        ttsobject = MainActivity.ttsobject;

        list();

    }

    private void list() {
        //  Create list of items
        String[] showItems = {"萬邦行"
                };

        //  Build Adapter
        adapter = new ArrayAdapter<String>(this, R.layout.menu_list_show_item, showItems);

        //  Configure the list view to the Adapter

        lv.setAdapter(adapter);
    }

    private void lvActions(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final View v = view;
                timeOfClick++;
                Favorite.position = position;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String building = ((TextView) v).getText().toString();
                        //  output sound
                        if (timeOfClick == 1){
                            if (myItem)
                                speak("双击取消我的最爱，"+building);
                            else
                                speak("双击复原我的最爱，"+building);
                            //  open activity
                        }else if (timeOfClick == 2){
                            //  detail
                            if (myItem)
                                speak("取消我的最爱，"+building);
                            else
                                speak("复原我的最爱，"+building);
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
