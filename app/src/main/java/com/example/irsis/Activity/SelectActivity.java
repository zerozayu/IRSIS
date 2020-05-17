package com.example.irsis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
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
    SelectResultAdapter adapter;

    private List<User> userList = new ArrayList<>();
    private static String accountText;
    ResultSet rs = null;
    DatabaseActions action = new DatabaseActions();
    public static int count = 0;
    public String myname;
    public String myaccount;
    public String mypassword;
    public String myphone;
    public String mylimit;

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
        adapter = new SelectResultAdapter(userList);
        recyclerView_selectResult.setAdapter(adapter);

    }

    public void setListener() {
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountText = editText_selectByAccount.getText().toString().trim();
                userList.clear();
                getUser(accountText);
                count++;
                adapter = new SelectResultAdapter(userList);
                recyclerView_selectResult.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void getUser(String accountText) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rs = action.selectByUaccount(accountText);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = rs;
                handler.sendMessage(msg);
                Log.i("tag", String.valueOf(msg));
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    public android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        while (rs.next()) {
                            myname = ((ResultSet) msg.obj).getString("Uname");
                            myaccount = ((ResultSet) msg.obj).getString("Uaccount");
                            mypassword = ((ResultSet) msg.obj).getString("Upassword");
                            myphone = ((ResultSet) msg.obj).getString("Uphone");
                            mylimit = ((ResultSet) msg.obj).getString("Ulimit");
                            User user = new User(myname, myaccount, mypassword, myphone, mylimit);
                            userList.add(user);
                            System.out.println("userlist"+userList);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count=0;
    }
}
