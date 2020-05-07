package com.example.irsis.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.example.irsis.JDBC.*;
import com.example.irsis.R;

import org.litepal.util.DBUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button login;
    private LinearLayout loadingLayout;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static String accountText;
    private static String passwordText;
    DatabaseActions action=new DatabaseActions();
    ResultSet rs=null;
    public String  ans_string;
    public String myaccount;
    public String mypassword;





    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MultiDex.install(this);
        findView();
        setOnListener();
        setRememberPassword();
    }

    //加载View
    public void findView() {
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPassword = findViewById(R.id.remember_password);
        login = findViewById(R.id.button_login);
        loadingLayout = findViewById(R.id.loading_layout);
    }

    //监听器
    public void setOnListener() {
        login.setOnClickListener(this);
    }

    //记住密码
    public void setRememberPassword() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //记住密码默认是false
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            //如果选择了记住密码，则将账号跟密码设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPassword.setChecked(true);
        }
    }

    //OnClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                accountText = accountEdit.getText().toString().trim();
                passwordText = passwordEdit.getText().toString().trim();

                if (accountText.equals("")) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (passwordText.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

                } else {
                    //与数据库的账号密码进行对比
                    getAccount(accountText);
                    //getAccount(LoginActivity.accountText);
                }
                break;
            default:

        }
    }

    //通过account查询用户account
    public void getAccount(String acc){
        new Thread(new Runnable() {
            @Override
            public void run() {
                rs=action.selectByUaccount(acc);
                Message msg =handler.obtainMessage();
                msg.what=1;
                msg.obj=rs;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    public android.os.Handler handler =new android.os.Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 1:
                    try {
                        if(rs.next()){
                            myaccount= ((ResultSet) msg.obj).getString("Uaccount");
                            mypassword=((ResultSet) msg.obj).getString("Upassword");
                            if(!accountText.equals(myaccount)){
                                Toast.makeText(LoginActivity.this,"账号不存在",Toast.LENGTH_SHORT).show();
                            }
                            else if (!passwordText.equals(mypassword)){
                                Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                editor = pref.edit();
                                if (rememberPassword.isChecked()) {
                                    editor.putBoolean("remember_password", true);
                                    editor.putString("account", accountText);
                                    editor.putString("password", passwordText);
                                } else {
                                    editor.clear();
                                }
                                editor.apply();
                                IsLogin.setIsLoginTrue();
                                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };



}
