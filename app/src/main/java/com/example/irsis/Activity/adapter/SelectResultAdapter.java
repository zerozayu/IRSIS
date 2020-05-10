package com.example.irsis.Activity.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irsis.Activity.AdminActivity;
import com.example.irsis.Activity.MainActivity;
import com.example.irsis.Activity.MakeCallActivity;
import com.example.irsis.Activity.SelectActivity;
import com.example.irsis.JDBC.Action;
import com.example.irsis.JDBC.DatabaseActions;
import com.example.irsis.R;
import com.example.irsis.myclass.User;

import java.util.List;

public class SelectResultAdapter extends RecyclerView.Adapter<SelectResultAdapter.ViewHolder> {

    private List<User> mUser;
    Action action=new Action();

    public SelectResultAdapter(List<User> userList){
        mUser=userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.select_result_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                User user=mUser.get(position);
                String account=holder.account.getText().toString().trim();
                action.deleteUserByAccount(account);

                Activity activity= (Activity) view.getContext();
                activity.finish();
            }
        });

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user =mUser.get(position);
        holder.conut.setText("第"+ SelectActivity.count+"次查询结果：");
        holder.name.setText(user.getUserName());
        holder.account.setText(user.getUserAccount());
        holder.password.setText(user.getUserPassword());
        holder.email.setText(user.getUserEmail());
        holder.limit.setText(user.getUserLimit());
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View userView;
        TextView conut;
        TextView name;
        TextView account;
        TextView password;
        TextView email;
        TextView limit;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userView=itemView;
            conut=itemView.findViewById(R.id.sr_count);
            name=itemView.findViewById(R.id.sr_name);
            account=itemView.findViewById(R.id.sr_account);
            password=itemView.findViewById(R.id.sr_password);
            email=itemView.findViewById(R.id.sr_email);
            limit=itemView.findViewById(R.id.sr_limit);
            delete=itemView.findViewById(R.id.sr_delete);
        }
    }
}
