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

public class DistrictSearch extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;
    TextToSpeech ttsobject;
    public int result;
    private ArrayAdapter<String> adapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_building_type);

        lv = (ListView)findViewById(R.id.lvType);

        ttsobject = MainActivity.ttsobject;

        listDistrict();
        lvActions();

        lv.setTextFilterEnabled(true);
    }

    private void listDistrict() {
        //  Create list of items
        String[] showItems = {"東區" ,"灣仔區" , "中西區" , "南區" , "油尖旺區" , "深水埗區" , "九龍城區" , "黃大仙區" , "觀塘區" , "元朗區" , "屯門區" , "大埔區" , "北區" , "沙田區" , "葵青區" , "荃灣區" , "離島區" , "西貢區",
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
                                speak("移至類型搜尋");
                                Intent intent = new Intent(DistrictSearch.this, BuildingType.class);
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
                }
                break;
            }

        }
    }
}