package com.example.testword;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyWordsTag";
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = this.getContentResolver();
        Button buttonInsert = (Button) findViewById(R.id.add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

// 创建一个view，并且将布局加入view中
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.add, null, false);
                builder.setTitle("自定义对话框")
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取得用户输入内容，注意findViewById前面的viewDialog，表示在该view里面进行查找
                                EditText name = viewDialog.findViewById(R.id.add_name);
                                EditText mean = viewDialog.findViewById(R.id.add_mean);
                                EditText ex = viewDialog.findViewById(R.id.add_ex);
                                String strWord = name.getText().toString();
                                String strMeaning = mean.getText().toString();
                                String strSample = ex.getText().toString();
                                ContentValues values = new ContentValues();

                                values.put(Words.Word.COLUMN_NAME_WORD, strWord);
                                values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
                                values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

                                Uri newUri = resolver.insert(Words.Word.CONTENT_URI, values);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }

        });
        Button deletet = (Button) findViewById(R.id.delete);
        deletet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolver.delete(Words.Word.CONTENT_URI, null, null);
            }
        });
        Button change = (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

// 创建一个view，并且将布局加入view中
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.change, null, false);
                builder.setTitle("自定义对话框")
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取得用户输入内容，注意findViewById前面的viewDialog，表示在该view里面进行查找
                                EditText id = viewDialog.findViewById(R.id.add_id);
                                EditText name = viewDialog.findViewById(R.id.add_name);
                                EditText mean = viewDialog.findViewById(R.id.add_mean);
                                EditText ex = viewDialog.findViewById(R.id.add_ex);
                                String strid = id.getText().toString();
                                String strWord = name.getText().toString();
                                String strMeaning = mean.getText().toString();
                                String strSample = ex.getText().toString();
                                ContentValues values = new ContentValues();
                                values.put(Words.Word.COLUMN_NAME_WORD, strWord);
                                values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
                                values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

                                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + strid);
                                int result = resolver.update(uri, values, null, null);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }
        });
        Button find = (Button) findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

// 创建一个view，并且将布局加入view中
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.find, null, false);
                builder.setTitle("自定义对话框")
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取得用户输入内容，注意findViewById前面的viewDialog，表示在该view里面进行查找
                                EditText id = viewDialog.findViewById(R.id.add_id);
                                String strid = id.getText().toString();
                                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + strid);
                                Cursor cursor = resolver.query(Words.Word.CONTENT_URI,
                                        new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},
                                        null, null, null);
                                if (cursor == null) {
                                    Toast.makeText(MainActivity.this, "没有找到记录", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                //找到记录，这里简单起见，使用Log输出
                                String msg = "";
                                if (cursor.moveToFirst()) {
                                    do {
                                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(Words.Word._ID)) + ",";
                                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)) + ",";
                                        msg += "含义：" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)) + ",";
                                        msg += "示例" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)) + "\n";
                                    } while (cursor.moveToNext());
                                }
                                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
                            }

                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();


            }
        });
    }
}