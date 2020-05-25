package com.example.irsis.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.irsis.JDBC.DatabaseActions;
import com.example.irsis.R;
import com.example.irsis.Activity.adapter.ProblemAdapter;
import com.example.irsis.myclass.Problem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    InputStream is;
    OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        Toolbar toolbar = findViewById(R.id.toolbar_problem);
        setSupportActionBar(toolbar);

        findView();
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
        refreshProblems();

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
                refreshProblems();
                break;
            default:
        }
        return true;
    }



    private void refreshProblems() {
        Toast.makeText(ProblemActivity.this,"刷新中......",Toast.LENGTH_LONG).show();

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
                        problemList.clear();
                        getProblem();
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ProblemAdapter(problemList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if(problemList.isEmpty()){
                            Toast.makeText(ProblemActivity.this,"刷新失败，请稍后再试。",Toast.LENGTH_SHORT).show();
                        }
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
                msg.what = 2;
                msg.obj = rs;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    try {
                        while (rs.next()) {

                            pname = ((ResultSet) msg.obj).getString("Pname");
                            pcontent = ((ResultSet) msg.obj).getString("Pcontent");

                            is=((ResultSet)msg.obj).getBinaryStream("Pimage");
                            File file=new File("/sdcard/Android/data/com.example.irsis/"+pname+".jpg");
                            os = new FileOutputStream(file);

                            byte[] b = new byte[1024];
                            int len = 0;
                            while((len=is.read(b))!=-1){
                                os.write(b, 0, len);
                            }

                            os.close();
                            is.close();

                            pimage=getPimage(file.getAbsolutePath());

                            Problem problem = new Problem(pname,pcontent,pimage);
                            System.out.println(problem.toString());
                            problemList.add(problem);
                        }
                        System.out.println("handler:"+problemList);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public byte[] getPimage(String filePath){
        File file=new File(filePath);
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream in = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                bos.write(b, 0, b.length);
            }
            bos.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }
}
