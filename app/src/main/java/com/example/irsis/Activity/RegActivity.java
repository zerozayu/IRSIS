package com.example.irsis.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irsis.JDBC.Insert_Action;
import com.example.irsis.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RegActivity extends BaseActivity implements View.OnClickListener {
    TextView id;
    EditText name;
    EditText account;
    EditText password;
    Button btn_reg;
    Insert_Action insert_action;

    public static String Uname;
    public static String Uaccount;
    public static String Upassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        findView();
        setOnListener();
    }

    public void findView(){
        id=findViewById(R.id.reg_id);
        name=findViewById(R.id.reg_name);
        account=findViewById(R.id.reg_account);
        password=findViewById(R.id.reg_password);
        btn_reg=findViewById(R.id.button_reg);
    }

    private void setOnListener() {
        btn_reg.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_reg:
                Uname=name.getText().toString();
                Uaccount=account.getText().toString();
                Upassword=password.getText().toString();
                if (Uname.equals("")){
                    Toast.makeText(RegActivity.this, "名字不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Uaccount.equals("")){
                    Toast.makeText(RegActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Upassword.equals("")){
                    Toast.makeText(RegActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    insert_action=new Insert_Action();
                    insert_action.insertUser(Uname,Uaccount,Upassword);
                    Intent intent = new Intent(RegActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();

                }
                break;
            default:
                break;
        }
    }
}
