package com.fyp.kwl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Favorite extends AppCompatActivity {
    ListView lv;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_favorite);

        lv = (ListView)findViewById(R.id.list);

        list();

    }

    private void list() {
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
}
