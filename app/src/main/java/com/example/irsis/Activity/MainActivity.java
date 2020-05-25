package com.example.irsis.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
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

import com.example.irsis.R;
import com.example.irsis.myclass.User;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button button_toProblemActivity;
    Button button_toLBSActivity;
    Button button_toAdminActivity;
    Button button_toLitePalActivity;


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
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        intent = new Intent(MainActivity.this, MakeCallActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_offline:
                        intent = new Intent("com.example.IRSIS.FORCE_OFFLINE");
                        sendBroadcast(intent);
                        break;
                }

                return true;
            }
        });

        findView();
        setListener();
    }

    public void findView() {
        button_toProblemActivity = findViewById(R.id.button_toProblemActivity);
        button_toLBSActivity = findViewById(R.id.button_toLBSActivity);
        button_toAdminActivity = findViewById(R.id.button_toAdminActivity);
        button_toLitePalActivity = findViewById(R.id.button_toLitePalActivity);
    }

    public void setListener(){
        //跳转向问题列表界面
        button_toProblemActivity.setOnClickListener(this);
        //跳转向地图界面
        button_toLBSActivity.setOnClickListener(this);
        //跳转向管理员界面
        button_toAdminActivity.setOnClickListener(this);
        //跳转至数据库
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
                finish();
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
                intent = new Intent(MainActivity.this, TrackActivity.class);
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
            case R.id.main_login:
                if (IsLogin.isLogin==false) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("通知");
                    builder.setMessage("你已经登录了，是否重新登陆？");
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent("com.example.IRSIS.FORCE_OFFLINE");
                            sendBroadcast(intent);
                            IsLogin.setIsLoginFalse();
                        }
                    });
                    builder.show();
                }

                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

}
