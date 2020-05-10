package com.example.irsis.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irsis.Activity.BaseActivity;
import com.example.irsis.R;
import com.example.irsis.myclass.Problem;
import com.example.irsis.myclass.User;

import org.litepal.LitePal;

import java.util.List;

public class LitePalActivity extends BaseActivity implements View.OnClickListener {

    TextView textView=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);

        Button button_createDatebase = findViewById(R.id.button_createDatabase);
        button_createDatebase.setOnClickListener(this);
        Button button_addData = findViewById(R.id.button_addData);
        button_addData.setOnClickListener(this);
        Button button_updateData = findViewById(R.id.button_updateData);
        button_updateData.setOnClickListener(this);
        Button button_deleteData = findViewById(R.id.button_deleteData);
        button_deleteData.setOnClickListener(this);
        Button button_queryData = findViewById(R.id.button_queryData);
        button_queryData.setOnClickListener(this);

        textView =findViewById(R.id.textView_result);
        textView.setText("查询结果在这");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_createDatabase:
                LitePal.getDatabase();
                break;
            default:
                break;

        }
    }

}
