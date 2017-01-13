package com.fyp.kwl.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int timeOfClick = 0;
    static int position;   //  position of clicking on listview
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.lvMainMenu);
        listMainMenu();
        clickOption();


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
                                    break;
                                case 1:
                                    Toast.makeText(MainActivity.this, "single search clicked", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(MainActivity.this, "single setting clicked", Toast.LENGTH_SHORT).show();
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
//        lv.setOnItemClickListener(new DoubleClickListener());
//        new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        Toast.makeText(MainActivity.this, "favourite cliked", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(MainActivity.this, "search clicked", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(MainActivity.this, "setting clicked", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }
    }
}
