package com.example.irsis.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.irsis.R;
import com.example.irsis.myclass.Problem;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

public class ProblemContentActivity extends BaseActivity {
    public static final String PROBLEM_NAME = "problem_name";
    public static final String PROBLEM_CONTENT = "problem_content";
    public static final String PROBLEM_IMAGE = "problem_image";
    String problemName=null;
    String problemContent=null;

    //自定义方法传参
    public static void actionStart(Context context, String problemName, String problemContent, byte[] problemImage) {
        Intent intent = new Intent(context, ProblemContentActivity.class);
        intent.putExtra(PROBLEM_NAME, problemName);
        intent.putExtra(PROBLEM_CONTENT, problemContent);
        intent.putExtra(PROBLEM_IMAGE, problemImage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_content);
        Intent intent = getIntent();
        //获取传入的问题名称
        problemName = intent.getStringExtra(PROBLEM_NAME);
        //获取传入的问题内容
        problemContent = intent.getStringExtra(PROBLEM_CONTENT);
        //获取传入的问题照片
        byte[] problemImage = getIntent().getByteArrayExtra(PROBLEM_IMAGE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView problemImageView = findViewById(R.id.problem_image_view);
        TextView problemContentText = findViewById(R.id.problem_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(problemName);
        Glide.with(this).load(problemImage).into(problemImageView);
        problemContentText.setText(problemContent);

        FloatingActionButton fab = findViewById(R.id.fab_resolved);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.deleteAll(Problem.class, "Pname = ? and Pcontent = ?", problemName, problemContent);
                Intent intent = new Intent(ProblemContentActivity.this, ProblemActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
