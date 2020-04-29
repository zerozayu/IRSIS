package com.example.irsis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irsis.JDBC.Insert_Action;
import com.example.irsis.R;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    EditText id;
    EditText name;
    EditText account;
    EditText password;
    Button btn_reg;
    Insert_Action charu;
    public static Integer Uid;
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
                Uid=Integer.getInteger(id.getText().toString());
                Uname=name.getText().toString();
                Uaccount=account.getText().toString();
                Upassword=password.getText().toString();
                if (id.getText().toString().equals("")){
                    Toast.makeText(RegActivity.this, "id不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Uname.equals("")){
                    Toast.makeText(RegActivity.this, "名字不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Uaccount.equals("")){
                    Toast.makeText(RegActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
                }else if (Upassword.equals("")){
                    Toast.makeText(RegActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    charu=new Insert_Action();
                    charu.insertUser(Uid,Uname,Uaccount,Upassword);
                    Intent intent=new Intent(RegActivity.this,AdminActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
            default:
                break;
        }
    }
}
