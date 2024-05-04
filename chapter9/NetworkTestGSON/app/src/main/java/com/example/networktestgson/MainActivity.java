package com.example.networktestgson;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request){
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp(){
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    InetAddress addr = InetAddress.getLocalHost();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            // 指定访问的服务器地址是电脑本机
//                            .url("http://localhost/get_data.xml")
                            .url("http://192.168.1.107/get_data.json")
//                            .url("http://10.0.2.2/get_data.xml")
//                            .url("http://" + addr.getHostAddress() + "/get_data.xml")
                            .build();
//                    Log.d("MainActivity", "localhost: " + addr.getHostAddress());
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void parseJSONWithGSON(String jsonData){
            Gson gson = new Gson();
            List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
//            Log.d("MainActivity", "type is : "+ new TypeToken<List<App>>(){}.getType());
            for (App app : appList){
                Log.d("MainActivity", "id is "+ app.getId());
                Log.d("MainActivity", "name is "+ app.getName());
                Log.d("MainActivity", "version is "+ app.getVersion());
            }
    }
}