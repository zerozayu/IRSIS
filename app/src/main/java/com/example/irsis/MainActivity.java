package com.example.irsis;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/*
 * 1.活动的启动模式：standard、singleTop、singleTask、singleInstance
 *       在AndroidManifest.xml中通过给<activity>标签指定android:launchMode属性来选择启动模式
 *   1.1 standard：每当启动一个新的活动，在返回栈中入栈，并处于栈顶的位置。
 *   1.2 singleTop：每当启动一个新的活动，如果栈顶已经是该活动，则直接使用他，不会再重新创建新的活动实例。
 *   1.3 singleTask：每次启动活动系统会首先在返回栈中检查是否存在该活动的实例，如果已经存在就直接使用该实例，并把
 *       在此活动之上的所有活动统统出栈；如果没有发现则创建一个新的活动实例。
 *   1.4 singleInstance：指定为singleInstance模式的活动会启用一个新的返回栈来管理这个活动。
 *
 *
 * */


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    //重写onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_toProblemActivity = findViewById(R.id.button_toProblemActivity);
        button_toProblemActivity.setOnClickListener(this);

        Button button_toChatActivity = findViewById(R.id.button_toChatActivity);
        button_toChatActivity.setOnClickListener(this);

        Button button_toAdminActivity = findViewById(R.id.button_toAdminActivity);
        button_toAdminActivity.setOnClickListener(this);
    }

    //重写onClick方法
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_toProblemActivity:
                intent = new Intent(MainActivity.this, ProblemActivity.class);
                startActivity(intent);
                break;
            case R.id.button_toChatActivity:
                intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.button_toAdminActivity:
                intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
                break;
            default:
                break;

//                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//                dialog.setTitle("这是一个弹出式对话框");
//                dialog.setMessage("这是重要的信息。");
//                dialog.setCancelable(false);
//                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                dialog.show();

//                int progress =progressBar.getProgress();
//                progress+=10;
//                progressBar.setProgress(progress);

//                imageView.setImageResource(R.drawable.img_2);

//                String inputText =editText.getText().toString();
//                Toast.makeText(MainActivity.this,inputText,Toast.LENGTH_SHORT).show();

        }
    }

    //重写menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "你点击了add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "你点击了remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
