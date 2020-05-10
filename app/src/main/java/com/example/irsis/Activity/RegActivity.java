package com.example.irsis.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irsis.JDBC.Action;
import com.example.irsis.R;

public class RegActivity extends BaseActivity implements View.OnClickListener {
    EditText name;
    EditText account;
    EditText password;
    EditText email;
    EditText limit;
    Button btn_reg;
    Action action;

    public static String Uname;
    public static String Uaccount;
    public static String Upassword;
    public static String Uemail;
    public static String Ulimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        findView();
        setOnListener();
    }

    public void findView(){
        name=findViewById(R.id.reg_name);
        account=findViewById(R.id.reg_account);
        password=findViewById(R.id.reg_password);
        email=findViewById(R.id.reg_email);
        limit=findViewById(R.id.reg_limit);
        btn_reg=findViewById(R.id.button_reg);
    }

    private void setOnListener() {
        btn_reg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_reg:
                Uname=name.getText().toString().trim();
                Uaccount=account.getText().toString().trim();
                Upassword=password.getText().toString().trim();
                Uemail=email.getText().toString().trim();
                Ulimit=limit.getText().toString().trim();
                if (Uname.equals("")){
                    Toast.makeText(RegActivity.this, "名字不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Uaccount.equals("")){
                    Toast.makeText(RegActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Upassword.equals("")){
                    Toast.makeText(RegActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (Uemail.equals("")){
                    Toast.makeText(RegActivity.this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Ulimit.equals("")){
                    Toast.makeText(RegActivity.this, "权限不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    action=new Action();
                    boolean isInsert= action.insertUser(Uname,Uaccount,Upassword,Uemail,Ulimit);
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
