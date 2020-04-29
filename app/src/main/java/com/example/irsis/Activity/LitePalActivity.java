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
            case R.id.button_addData:
                User user = new User();
                user.setuId(1);
                user.setUserAccount("zhangyu@qq.com");
                user.setUserPassword("123456");
                user.setUserName("zhangyu");
                user.setLimit(true);
                user.save();

//                Problem problem=new Problem();
//                problem.setpId(1);
//                problem.setName("问题一名字");
//                problem.setContent("问题一内容");
//                problem.setImageId(R.drawable.problem128);
//                problem.save();
                Toast.makeText(this,"创建成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_updateData:
                Problem problem1=new Problem();
                problem1.setName("更新的问题一名字");
                problem1.updateAll("name=? and content=?","问题一名字","问题一内容");
                break;
            case R.id.button_deleteData:
                LitePal.deleteAll(Problem.class, "Pname = ? and Pcontent = ?", "1");
                break;
            case R.id.button_queryData:
//                List<Problem> problemList =LitePal.findAll(Problem.class);
//                for(Problem problem2:problemList){
//                    textView.append("\n数据名："   +problem2.getName()+
//                                    "\n数据内容：" +problem2.getContent()+"\n");
//                }
                List<User> userList=LitePal.findAll(User.class);
                for (User user1:userList){
                    textView.append("\nid:"+user1.getuId()+
                            "\naccount:"+user1.getUserAccount()+
                            "\npassword:"+user1.getUserPassword()+
                            "\nname:"+user1.getUserName()+
                            "\nlimit:"+user1.getLimit()+
                            "\n");
                }
                break;
            default:
                break;

        }
    }

}
