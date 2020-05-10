package com.example.irsis.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.irsis.JDBC.DatabaseActions;
import com.example.irsis.R;
import com.example.irsis.Activity.adapter.ProblemAdapter;
import com.example.irsis.myclass.Problem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private ProblemAdapter adapter;
    FloatingActionButton fab;
    Toolbar toolbar;

    DatabaseActions action = new DatabaseActions();
    ResultSet rs = null;
    public String pname;
    public String pcontent;
    public byte[] pimage;
    List<Problem> problemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        findView();
        setSupportActionBar(toolbar);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProblemAdapter(problemList);
        recyclerView.setAdapter(adapter);

        //悬浮新增按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemActivity.this, SubmitProblemActivity.class);
                startActivity(intent);
            }
        });

        //下拉刷新
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshProblems();
            }
        });

        getProblem();
    }

    public void findView() {
        recyclerView = findViewById(R.id.problem_recyclerView);
        toolbar = findViewById(R.id.toolbar_problem);
        fab = findViewById(R.id.fab);
        swipeRefresh = findViewById(R.id.swipe_refresh);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_problem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ProblemActivity.this, SubmitProblemActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                Toast.makeText(this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "你点击了Setting", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    private void initProblems() {
        problemList.clear();
        adapter = new ProblemAdapter(problemList);
        recyclerView.setAdapter(adapter);
    }

    private void refreshProblems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initProblems();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //查询problem
    public void getProblem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rs = action.selectProblem();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = rs;
                handler.sendMessage(msg);
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
                            pname = ((ResultSet) msg.obj).getString("Pname");
                            pcontent = ((ResultSet) msg.obj).getString("Pcontent");
                            pimage = ((ResultSet) msg.obj).getBytes("Pimage");
                            Problem problem = new Problem(pname,pcontent,pimage);
                            problemList.add(problem);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
