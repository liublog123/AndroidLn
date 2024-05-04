package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
//
    private NetworkChangeReceiver networkChangeReceiver;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
//        localBroadcastManager = LocalBroadcastManager.getInstance(this);  // 获取实例
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
//                intent.setComponent(new ComponentName(getPackageName(),"com.example.broadcasttest.MyBroadcastReceiver"));
//                intent.setComponent(new ComponentName("com.example.broadcasttest2","com.example.broadcasttest2.AnotherBroadcastReceiver"));
//                intent.setPackage("com.example.broadcasttest");
//                intent.setPackage(packageName);
//              com.example.broadcasttest2.AnotherBroadcastReceiver
                intent.addFlags(0x01000000);
//                sendBroadcast(intent);
                sendOrderedBroadcast(intent,null);
//                Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
//                localBroadcastManager.sendBroadcast(intent); // 发送本地广播
            }
        });
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
//        localReceiver = new LocalReceiver();
//        localBroadcastManager.registerReceiver(localReceiver,intentFilter); // 注册本地监听广播
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
//        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_LONG).show();
        }
    }



    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isAvailable()){
//                Toast.makeText(context, "network is available",Toast.LENGTH_LONG).show();
//            } else{
//                Toast.makeText(context,"network is unavailable",Toast.LENGTH_LONG).show();
//            }
            Toast.makeText(context, "network changes", Toast.LENGTH_LONG).show();
        }
    }
}