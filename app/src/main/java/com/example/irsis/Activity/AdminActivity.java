package com.example.irsis.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.irsis.R;

public class AdminActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button button = findViewById(R.id.btn_resUser);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_resUser:
                Intent intent = new Intent(AdminActivity.this, RegActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
