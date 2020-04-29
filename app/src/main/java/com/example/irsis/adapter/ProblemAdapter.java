package com.example.irsis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.irsis.Activity.ProblemContentActivity;
import com.example.irsis.myclass.Problem;
import com.example.irsis.R;

import java.util.List;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder> {

    private Context mContext;

    private List<Problem> mProblemList;

    //自定义内部类ViewHolder,继承自RecyclerView.ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView problemImage;
        TextView problemName;

        //构造函数传入一个View参数，通常是RecyclerView子项的最外层布局
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView;
            problemImage = itemView.findViewById(R.id.problem_image);
            problemName = itemView.findViewById(R.id.problem_name);
        }
    }


    public ProblemAdapter(List<Problem> mProblemList) {
        this.mProblemList = mProblemList;
    }

    @NonNull
    @Override
    //创建
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.problem_item, parent, false);


        final ViewHolder holder = new ViewHolder(view);
        //布局的点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Problem problem =mProblemList.get(position);
                ProblemContentActivity.actionStart(mContext, problem.getName(), problem.getContent(),problem.getIamge());
            }
        });
        //图片的点击事件
        holder.problemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Problem problem =mProblemList.get(position);
                ProblemContentActivity.actionStart(mContext, problem.getName(), problem.getContent(),problem.getIamge());
            }
        });
        return holder;
    }

    @Override
    //赋值
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Problem problem = mProblemList.get(position);
        holder.problemName.setText(problem.getName());
        Glide.with(mContext).load(problem.getIamge()).into(holder.problemImage);
    }

    @Override
    public int getItemCount() {
        return mProblemList.size();
    }

}
