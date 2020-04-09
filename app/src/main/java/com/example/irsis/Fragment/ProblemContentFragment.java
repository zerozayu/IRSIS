package com.example.irsis.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.irsis.R;
import com.example.irsis.myclass.Problem;

import org.litepal.LitePal;

public class ProblemContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_problem_content,container,false);
        return view;
    }

    //refresh方法
    public void refresh(int problemImage, final String problemName, String problemContent){
        View problemContentLayout=view.findViewById(R.id.visibility_layout);
        problemContentLayout.setVisibility(View.VISIBLE);
        final ImageView problemImageId =view.findViewById(R.id.problem_image);
        TextView problemNameText=view.findViewById(R.id.problem_name);
        TextView problemContentText=view.findViewById(R.id.problem_content);
//        //删除这个问题
//        Button button_handled=view.findViewById(R.id.button_handled);
//        button_handled.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LitePal.deleteAll(Problem.class,"name=?",problemName);
//            }
//        });
        problemImageId.setImageResource(problemImage);//刷新问题的图片
        problemNameText.setText(problemName);//刷新问题详情的名字
        problemContentText.setText(problemContent);//刷新问题详情的内容

    }
}
