package com.example.uilayouttest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.relativelayout);
//        setContentView(R.layout.framelayout);
        setContentView(R.layout.percentlayout);
        this.setTitle("你好");
    }
}