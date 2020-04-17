package com.example.irsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.irsis.Fragment.ProblemContentFragment;

public class ProblemContentActivity extends BaseActivity {
    //自定义方法传参
    public static void actionStart(Context context, int problemImageId,String problemName, String problemContent ,byte[] image){
        Intent intent =new Intent(context, ProblemContentActivity.class);
        intent.putExtra("problem_imageId",problemImageId);
        intent.putExtra("problem_name",problemName);
        intent.putExtra("problem_content",problemContent);
        intent.putExtra("image",image);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_content);

        //获取传入的问题图片
        int problemImageId =getIntent().getIntExtra("problem_imageId",0);
        //获取传入的问题名称
        String problemName =getIntent().getStringExtra("problem_name");
        //获取传入的问题内容
        String problemContent = getIntent().getStringExtra("problem_content");
        //获取传入的问题照片
        byte[] image=getIntent().getByteArrayExtra("image");


        //得到ProblemDetailsFragment的实例
        ProblemContentFragment problemContentFragment = (ProblemContentFragment)
                getSupportFragmentManager().findFragmentById(R.id.problem_content_fragment);
        //刷新ProblemDetailsFragment界面
        problemContentFragment.refresh(problemImageId,problemName,problemContent,image);
    }
}
