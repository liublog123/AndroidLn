package com.example.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://com.example.databasetestprovider.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of King");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 22.85);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
                Log.d("MainActivity", "add "+ newId);
            }
        });

        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://com.example.databasetestprovider.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                Log.d("MainActivity", "Query " + cursor.getCount());
                Log.d("MainActivity", "Query " + cursor);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                        int pages = cursor.getInt(cursor.getColumnIndexOrThrow("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                        Log.d("MainActivity", "book name is "+ name);
                        Log.d("MainActivity", "book author is "+ author);
                        Log.d("MainActivity", "book pages is "+ pages);
                        Log.d("MainActivity", "book price is "+ price);
                    }
                    cursor.close();
                }
            }
        });

        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据
//                Uri uri = Uri.parse("content://com.example.databasetestprovider.provider/book/" + 3);
                Uri uri = Uri.parse("content://com.example.databasetestprovider.provider/book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("pages",1216);
                values.put("price", 24.05);
                getContentResolver().update(uri, values, null, null);
                Log.d("MainActivity", "update "+ newId);
            }
        });

        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
//                Uri uri = Uri.parse("content://com.example.databasetestprovider.provider/book/" + 3);
                Uri uri = Uri.parse("content://com.example.databasetestprovider.provider/book/" + newId);
                getContentResolver().delete(uri, null, null);
                Log.d("MainActivity", "delete "+ newId);
            }
        });
    }
}