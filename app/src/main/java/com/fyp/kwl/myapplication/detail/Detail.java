package com.fyp.kwl.myapplication.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.fyp.kwl.myapplication.R;

public class Detail extends AppCompatActivity {
    String building;
    TextView title, content;
    Button nevigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_detail);

//        building = Intent.getIntent("building");

    }
}
