package com.example.irsis.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.example.irsis.JDBC.*;
import com.example.irsis.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String ip = "cdb-hyfbs7ph.gz.tencentcdb.com";//外网地址
    private static final int port = 10106;
    private static final String dbName = "irsis";
    private static final String sqluser = "root";
    private static final String sqlpassword = "zy19980412";
    private static final String sqlurl = "jdbc:mysql://" + ip + ":" + port + "/" + dbName +
            "?serverTimezone=Asia/Chongqing&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false&verifyServerCertificate=false&autoReconnct=true&autoReconnectForPools=true&allowMultiQueries=true";

    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button login;
    private LinearLayout loadingLayout;

    private get_information get_account;
    private get_information get_password;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

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
                //loadingLayout.setVisibility(View.VISIBLE);
                //获取输入框中的账号跟密码
                String accountText = accountEdit.getText().toString().trim();
                String passwordText = passwordEdit.getText().toString().trim();
                if (accountText.equals("")) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                } else if (passwordText.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

                } else {
                    //与数据库的账号密码进行对比
                    //Contants.IS_LOGIN = true;
                    if(!accountText.equals("admin")){
                        Toast.makeText(LoginActivity.this, "用户不存在！", Toast.LENGTH_SHORT).show();
                    }
                    else if(!passwordText.equals("123456")){
                        Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

}
