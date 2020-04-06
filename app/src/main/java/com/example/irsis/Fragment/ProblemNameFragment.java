package com.example.irsis.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irsis.ProblemContentActivity;
import com.example.irsis.R;
import com.example.irsis.myclass.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemNameFragment extends Fragment {
    private boolean isTwoPane;

    private List<Problem> getProblem(){
        List<Problem> problemList =new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            Problem problem =new Problem();
            problem.setName("问题-"+i);
            problem.setContent("问题描述：这是一个问题描述！");
            problem.setImageId(R.drawable.problem128);
            problemList.add(problem);
        }
        return problemList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_name, container, false);
        RecyclerView problemNameRecyclerView=view.findViewById(R.id.problem_name_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        problemNameRecyclerView.setLayoutManager(layoutManager);
        ProblemAdapter adapter =new ProblemAdapter(getProblem());
        problemNameRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.problem_content_layout)!=null){
            isTwoPane =true;
        }else{
            isTwoPane=false;
        }
    }


    //内部类ProblemAdapter作为RecycleView的适配器
    class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder>{

        private List<Problem> mProblemList;

        public ProblemAdapter(List<Problem> mProblemList) {
            this.mProblemList = mProblemList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Problem problem= mProblemList.get(holder.getAdapterPosition());
                    if(isTwoPane){
                        //如果是双页模式，则刷新ProblemContentFragment中的内容
                        ProblemContentFragment problemContentFragment= (ProblemContentFragment) getFragmentManager()
                                .findFragmentById(R.id.problem_content_fragment);
                        problemContentFragment.refresh(problem.getName(),problem.getContent());
                    }
                    else {
                        //如果是单页模式，直接启动ProblemContentActivity
                        ProblemContentActivity.actionStart(getActivity(),problem.getName(),problem.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Problem problem= mProblemList.get(position);
            holder.problemNameText.setText(problem.getName());
        }

        @Override
        public int getItemCount() {
            return mProblemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView problemNameText;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                problemNameText=itemView.findViewById(R.id.problem_name);
            }
        }
    }
}
