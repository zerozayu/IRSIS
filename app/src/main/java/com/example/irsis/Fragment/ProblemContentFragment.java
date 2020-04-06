package com.example.irsis.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.irsis.R;

public class ProblemContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_problem_content,container,false);
        return view;
    }
    public void refresh(String problemName,String problemContent){
        View problemContentLayout=view.findViewById(R.id.visibility_layout);
        problemContentLayout.setVisibility(View.VISIBLE);
        TextView problemNameText=view.findViewById(R.id.problem_name);
        TextView problemContentText=view.findViewById(R.id.problem_content);
        problemNameText.setText(problemName);//刷新问题详情的名字
        problemContentText.setText(problemContent);//刷新问题详情的内容
    }
}
