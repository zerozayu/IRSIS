package com.example.irsis;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.irsis.Fragment.ProblemNameFragment;
import com.example.irsis.adapter.ProblemAdapter;
import com.example.irsis.myclass.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);


//        //获取碎片的实例从而使用碎片里的方法
//        ProblemNameFragment problemNameFragment= (ProblemNameFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.problem_name_fragment);
    }

}
