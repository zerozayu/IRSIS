package com.example.irsis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irsis.Fragment.ProblemNameFragment;
import com.example.irsis.myclass.Problem;

import org.litepal.LitePal;

public class SubmitProblemActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_problemName;
    private EditText edit_problemContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_problem);
        //数据库
        LitePal.getDatabase();

        //声明组件
        edit_problemName = findViewById(R.id.editText_problemName);
        edit_problemContent = findViewById(R.id.editText_problemContent);


        Button button_submitProblem = findViewById(R.id.button_submitProblem);
        button_submitProblem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_submitProblem:
                //数据库存储操作
                Problem problem = new Problem();
                 //problem.setpId(1);
                problem.setName(edit_problemName.getText().toString());
                problem.setContent(edit_problemContent.getText().toString());
                problem.setImageId(R.drawable.problem128);
                problem.save();

                //弹出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("通知");
                builder.setMessage("提交成功");
                builder.setCancelable(false);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }
}
