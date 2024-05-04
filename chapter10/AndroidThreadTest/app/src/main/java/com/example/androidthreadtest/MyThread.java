package com.example.androidthreadtest;

import android.widget.TextView;

public class MyThread implements Runnable{
    public TextView text;
    public MyThread(TextView text){
        this.text = text;
    }
    @Override
    public void run() {
        //
        text.setText("Nice to meet you!");
    }
}
