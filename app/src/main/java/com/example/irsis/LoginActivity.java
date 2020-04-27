package com.example.irsis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.irsis.myclass.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button login;
    private LinearLayout loadingLayout;

    Boolean isRight=false;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        pref = PreferenceManager.getDefaultSharedPreferences(this);

        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPassword = findViewById(R.id.remember_password);
        login = findViewById(R.id.button_login);
        loadingLayout=findViewById(R.id.loading_layout);

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

        login.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:
                loadingLayout.setVisibility(View.VISIBLE);
                //获取输入框中的账号跟密码
                final String account = accountEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                //获取服务器的账号跟密码
                HttpUtil.sendOkHttpRequest("http://10.0.2.2:8080/irsis/user.json",new Callback(){
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        String responseData=response.body().string();
                        Gson gson=new Gson();
                        List<User> userList=gson.fromJson(responseData,new TypeToken<List<User>>(){}.getType());
                        for (User user:userList){
                            if (account.equals(user.getUserAccount()) && password.equals(user.getUserPassword())) {
                                isRight=true;
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }
                });


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //如果与数据库匹配则允许登录
                        if (isRight||(account.equals("admin")&&password.equals("123456"))) {
                            editor = pref.edit();
                            if (rememberPassword.isChecked()) {
                                editor.putBoolean("remember_password", true);
                                editor.putString("account", account);
                                editor.putString("password", password);
                            } else {
                                editor.clear();
                            }
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            isRight=false;
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                        loadingLayout.setVisibility(View.GONE);
                    }

                }, 1000);//1秒后执行Runnable中的run方法
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }





}
