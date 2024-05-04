package com.example.playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.video_view);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button replay = (Button) findViewById(R.id.replay);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 33){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_MEDIA_VIDEO},1);
        } else {
            initVideoPath(); // 初始化VideoPath
        }
        } else {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
            } else {
                initVideoPath(); // 初始化VideoPath
            }
        }
    }

    private void initVideoPath(){
        try {
//            File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
            File file = new File(Environment.getExternalStorageDirectory(), "1.mp4");
            Log.d("TAG", "Environment.getExternalStorageDirectory(): " +Environment.getExternalStorageDirectory()+"  " +file.getPath());
            videoView.setVideoPath(file.getPath()); // 指定视频文件的路径
//            videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.play) {
            if (!videoView.isPlaying()) {
                videoView.start(); // 开始播放
            }
        } else if (id == R.id.pause) {
            if (videoView.isPlaying()) {
                videoView.pause(); // 暂停播放
            }
        } else if (id == R.id.replay) {
            if (videoView.isPlaying()) {
                videoView.resume(); // 重新播放
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null){
            videoView.suspend();
        }    }
}