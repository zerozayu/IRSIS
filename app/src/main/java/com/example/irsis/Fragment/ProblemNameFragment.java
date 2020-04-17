package com.example.irsis.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irsis.ProblemActivity;
import com.example.irsis.ProblemContentActivity;
import com.example.irsis.R;
import com.example.irsis.myclass.Problem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ProblemNameFragment extends Fragment {
    //获取相关联的活动里面的方法
    //ProblemActivity problemActivity = (ProblemActivity) getActivity();

    private boolean isTwoPane;
    private List<Problem> getProblem() {
        List<Problem> problemList = LitePal.findAll(Problem.class);
        return problemList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_name, container, false);

        RecyclerView problemNameRecyclerView = view.findViewById(R.id.problem_name_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        problemNameRecyclerView.setLayoutManager(layoutManager);
        ProblemAdapter adapter = new ProblemAdapter(getProblem());
        problemNameRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.problem_content_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }


    //内部类ProblemAdapter作为RecycleView的适配器
    class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder> {

        private List<Problem> mProblemList;

        public ProblemAdapter(List<Problem> mProblemList) {
            this.mProblemList = mProblemList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            //获取problem_item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_item, parent,
                    false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Problem problem = mProblemList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        //如果是双页模式，则刷新ProblemContentFragment中的内容
                        ProblemContentFragment problemContentFragment = (ProblemContentFragment) getFragmentManager()
                                .findFragmentById(R.id.problem_content_fragment);
                        problemContentFragment.refresh(problem.getImageId(), problem.getName(),
                                problem.getContent(),problem.getIamge());
                    } else {
                        //如果是单页模式，直接启动ProblemContentActivity
                        ProblemContentActivity.actionStart(getActivity(), problem.getImageId(),
                                problem.getName(), problem.getContent(),problem.getIamge());
                    }
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            Problem problem = mProblemList.get(position);
            holder.problemNameText.setText(problem.getName());
            holder.problemImage.setImageResource(problem.getImageId());
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView problemNameText;
            ImageView problemImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                problemNameText = itemView.findViewById(R.id.problem_name);
                problemImage = itemView.findViewById(R.id.problem_image);
            }
        }

        @Override
        public int getItemCount() {
            return mProblemList.size();
        }
    }
}
