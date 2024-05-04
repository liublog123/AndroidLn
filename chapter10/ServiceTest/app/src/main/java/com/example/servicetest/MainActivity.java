package com.example.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unbindService = (Button) findViewById(R.id.unbind_service);
        Button startIntentService = (Button) findViewById(R.id.start_intent_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        startIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_service) {
            Intent startIntent = new Intent(this, MyService.class);
            startService(startIntent); // 启动服务
        } else if (v.getId() == R.id.stop_service) {
            Intent stopIntent = new Intent(this, MyService.class);
            stopService(stopIntent); // 停止服务
        } else if (v.getId() == R.id.bind_service){
            Intent bindIntent = new Intent(this, MyService.class);
            bindService(bindIntent ,connection, BIND_AUTO_CREATE); // 绑定服务
        } else if (v.getId() == R.id.unbind_service){
            unbindService(connection); // 解绑服务
        } else if (v.getId() == R.id.start_intent_service){
            // 打印主线程的id
            Log.d("MainActivity", "Thread id is : " + Thread.currentThread().getId());
            Intent intentService = new Intent(this, MyIntentService.class);
            startService(intentService);
        }
        //        switch (v.getId()){
//            case R.id.start_service:
//                break;
//            case R.id.stop_service:
//                break;
//            default:
//                break;
    }
}