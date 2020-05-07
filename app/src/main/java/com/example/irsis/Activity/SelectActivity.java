package com.example.irsis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irsis.Activity.adapter.SelectResultAdapter;
import com.example.irsis.JDBC.DatabaseActions;
import com.example.irsis.R;
import com.example.irsis.myclass.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends BaseActivity {
    EditText editText_selectByAccount;
    Button btn_select;
    RecyclerView recyclerView_selectResult;

    private List<User> userList=new ArrayList<>();
    private static String accountText;
    ResultSet rs=null;
    DatabaseActions action=new DatabaseActions();
    public String myname;
    public String myaccount;
    public String mypassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        findView();
        setListener();
    }

    public void findView() {
        editText_selectByAccount = findViewById(R.id.editText_selectByAccount);
        btn_select = findViewById(R.id.btn_select);

        recyclerView_selectResult = findViewById(R.id.recyclerView_selectResult);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_selectResult.setLayoutManager(layoutManager);
        SelectResultAdapter adapter = new SelectResultAdapter(userList);
        recyclerView_selectResult.setAdapter(adapter);

    }

    public void setListener() {
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountText=editText_selectByAccount.getText().toString().trim();
                getUser(accountText);
            }
        });
    }

    public void getUser(String accountText){
        new Thread(new Runnable() {
            @Override
            public void run() {
                rs=action.selectByUaccount(accountText);
                Message msg =handler.obtainMessage();
                msg.what=1;
                msg.obj=rs;
                handler.sendMessage(msg);
            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    public android.os.Handler handler =new android.os.Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 1:
                    try {
                        if(rs.next()){
                            myname="姓名："+((ResultSet) msg.obj).getString("Uname");
                            myaccount="账号：" +((ResultSet) msg.obj).getString("Uaccount");
                            mypassword="密码："+((ResultSet) msg.obj).getString("Upassword");
                            User user=new User(myname,myaccount,mypassword);
                            userList.add(user);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

}
