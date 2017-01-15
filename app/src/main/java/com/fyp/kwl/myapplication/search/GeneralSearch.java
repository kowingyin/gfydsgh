package com.fyp.kwl.myapplication.search;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.kwl.myapplication.MainActivity;
import com.fyp.kwl.myapplication.R;
import com.fyp.kwl.myapplication.detail.Detail;

import java.util.ArrayList;
import java.util.Locale;

public class GeneralSearch extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;
    EditText et;
    TextToSpeech ttsobject;
    public int result;
    private ArrayAdapter<String> adapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_general_search);

        lv = (ListView)findViewById(R.id.lvGeneral);
        et = (EditText)findViewById(R.id.etSearchBuild);

        ttsobject = MainActivity.ttsobject;

        listBuilding();
        editBoxActions();
        lvActions();

        lv.setTextFilterEnabled(true);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());

            }
        });
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

    private void lvActions(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final View v = view;
                timeOfClick++;
                GeneralSearch.position = position;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String building = ((TextView) v).getText().toString();
                        //  output sound
                        if (timeOfClick == 1){
                            speak(building);
                            //  open activity
                        }else if (timeOfClick == 2){
                            //  detail
                            if (position == 0){
                                Intent intent = new Intent(GeneralSearch.this, Detail.class);
//                            intent.putExtra("building", building);
                                startActivity(intent);

                            }
                        }
                        timeOfClick = 0;
                    }
                } , 500);

            }
        });
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
//                            speak("啟動語音輸入");
                            promptSpeechInput();

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

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Not support",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et.setText(result.get(0));
                }
                break;
            }

        }
    }
}
