package com.example.irsis.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.irsis.R;

public class AdminActivity extends BaseActivity implements View.OnClickListener {

    Intent intent;
    Button btn_regisiterUser;
    Button btn_deleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findView();
        setListener();
    }

    public void findView(){
        btn_regisiterUser = findViewById(R.id.btn_registerUser);
        btn_deleteUser = findViewById(R.id.btn_deleteUser);
    }

    public void setListener(){
        btn_regisiterUser.setOnClickListener(this);
        btn_deleteUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registerUser:
                intent = new Intent(AdminActivity.this, RegActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_deleteUser:
                intent = new Intent(AdminActivity.this, SelectActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

}
