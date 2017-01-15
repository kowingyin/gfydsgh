package com.fyp.kwl.myapplication.search;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fyp.kwl.myapplication.MainActivity;
import com.fyp.kwl.myapplication.R;

public class GeneralSearch extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;
    EditText et;
    TextToSpeech ttsobject;
    public int result;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_general_search);

        lv = (ListView)findViewById(R.id.lvGeneral);
        et = (EditText)findViewById(R.id.etSearchBuild);

        ttsobject = MainActivity.ttsobject;

        listBuilding();
        editBoxActions();


//        lv.setTextFilterEnabled(true);
//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                GeneralSearch.this.adapter.getFilter().filter(s);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                GeneralSearch.this.adapter.getFilter().filter(s);
//
//            }
//        });
    }

    private void listBuilding() {
        //  Create list of items
        String[] showItems = {"萬宜大廈",
                "萬邦行",
                "萬年大廈",
                "環球商場",
                "交易廣場",
                "中央廣場",
                "皇后中心",
                "新世界大廈"};

        //  Build Adapter
        adapter = new ArrayAdapter<String>(this, R.layout.menu_list_show_item, showItems);

        //  Configure the list view to the Adapter

        lv.setAdapter(adapter);
    }
    private void editBoxActions(){
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeOfClick++;
                SearchMenu.position = position;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  output sound
                        if (timeOfClick == 1){
                            speak("搜尋列，雙擊使用語音輸入");
                            //  open activity
                        }else if (timeOfClick == 2){
                            Intent intent = null;
                            switch (GeneralSearch.position){
                                case 0:
//                                    Toast.makeText(MainActivity.this, "favorite cliked", Toast.LENGTH_SHORT).show();
//                                    speak("move to general searching page");
                                    speak("移至普通搜尋");
                                    intent = new Intent(GeneralSearch.this, GeneralSearch.class);
                                    break;
                                case 1:
//                                    Toast.makeText(MainActivity.this, "search clicked", Toast.LENGTH_SHORT).show();
//                                    speak("move to selecting ");
                                    speak("移至地區搜尋");
                                    intent = new Intent(GeneralSearch.this, SearchMenu.class);
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
