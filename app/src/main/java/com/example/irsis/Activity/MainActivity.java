package com.example.irsis.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.irsis.R;
import com.example.irsis.myclass.User;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.List;
import java.util.Objects;

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
    private DrawerLayout mDrawerLayout;


    //重写onCreate方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //将打电话设为默认选中

        navView.setCheckedItem(R.id.nav_call);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_call:
                        intent =new Intent(MainActivity.this,MakeCallActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_offline:
                        intent =new Intent("com.example.IRSIS.FORCE_OFFLINE");
                        sendBroadcast(intent);
                        break;
                }

                return true;
            }
        });


        //跳转向问题列表界面
        Button button_toProblemActivity = findViewById(R.id.button_toProblemActivity);
        button_toProblemActivity.setOnClickListener(this);
        //跳转向聊天界面
        Button button_toChatActivity = findViewById(R.id.button_toChatActivity);
        button_toChatActivity.setOnClickListener(this);
        //跳转向地图界面
        Button button_toLBSActivity = findViewById(R.id.button_toLBSActivity);
        button_toLBSActivity.setOnClickListener(this);
        //跳转向管理员界面
        Button button_toAdminActivity = findViewById(R.id.button_toAdminActivity);
        button_toAdminActivity.setOnClickListener(this);

        //跳转至数据库
        Button button_toLitePalActivity = findViewById(R.id.button_toLitePalActivity);
        button_toLitePalActivity.setOnClickListener(this);


    }


    //重写onClick方法
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_toProblemActivity:
                intent = new Intent(MainActivity.this, ProblemActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                List<User> userList = LitePal.findAll(User.class);
                int len = userList.size();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this, "default")
                        .setContentTitle("通知")
                        .setContentText("内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.irsis)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.irsis))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);

                startActivity(intent);
                break;
            case R.id.button_toChatActivity:
                intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.button_toLBSActivity:
                intent = new Intent(MainActivity.this, LBSActivity.class);
                startActivity(intent);
                break;
            case R.id.button_toAdminActivity:
                intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
                break;

            case R.id.button_toLitePalActivity:
                intent = new Intent(MainActivity.this, LitePalActivity.class);
                startActivity(intent);
                break;


            default:
                break;
        }
    }

    //重写menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
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
            case R.id.setting_main:
                Toast.makeText(this, "你点击了setting", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

}