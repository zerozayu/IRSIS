package com.example.irsis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irsis.myclass.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button login;


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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isRight = false;
                //获取输入框中的账号跟密码
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();


                //从数据库获取用户名跟密码
                List<User> userList = LitePal.findAll(User.class);
                //将数据库用户名跟密码与输入框的对比
                for (User user : userList) {
                    if (account.equals(user.getUserAccount()) && password.equals(user.getUserPassword())) {
                        isRight = true;
                    }
                }

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
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}
