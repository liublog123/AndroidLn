package com.example.databasetestpractice;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 4);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开始装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", "454");
                values.put("price", "16.96");
                db.insert("Book", null, values); // 插入第一条数据
                values.clear();
                // 开始装第二组数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", "510");
                values.put("price", "19.95");
                db.insert("Book", null, values); // 插入第二条数据

                Toast.makeText(MainActivity.this, "add succeed!", Toast.LENGTH_SHORT).show();
            }
        });

        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
            }
        });

        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                db.delete("Book", "id in (?,?,?)" , new String[]{"7","14","15"});
//                db.delete("Book", "pages > ? ", new String[]{"500"});
                Toast.makeText(MainActivity.this, "delete data!", Toast.LENGTH_SHORT).show();
            }
        });


        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db.query("Book", null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        // 遍历Cursor
//                            String name = cursor.getString(cursor.getColumnIndex("name"));
//                            String author = cursor.getString(cursor.getColumnIndex("author"));
//                            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
//                            double price = cursor.getDouble(cursor.getColumnIndex("price"));
                            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                            String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                            int pages = cursor.getInt(cursor.getColumnIndexOrThrow("pages"));
                            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                            Log.d("MainActivity", "book name is "+ name);
                            Log.d("MainActivity", "book author is "+ author);
                            Log.d("MainActivity", "book pages is "+ pages);
                            Log.d("MainActivity", "book price is "+ price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

        Button replaceData = (Button) findViewById(R.id.replace_data);
        replaceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction(); // 开启事务
                try {
                    db.delete("Book",null,null);
//                    if (true){
//                        // 手动抛出一个异常，让事务失败
//                        throw new NullPointerException();
//                    }

                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", "720");
                    values.put("price", "21.85");
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful();
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });
    }
}