package com.example.irsis.Activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.irsis.R;
import com.example.irsis.myclass.User;

import java.util.List;

public class SelectResultAdapter extends RecyclerView.Adapter<SelectResultAdapter.ViewHolder> {

    private List<User> mUser;

    public SelectResultAdapter(List<User> userList){
        mUser=userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.select_result_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user =mUser.get(position);
        holder.name.setText(user.getUserName());
        holder.account.setText(user.getUserAccount());
        holder.password.setText(user.getUserPassword());
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView account;
        TextView password;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sr_name);
            account=itemView.findViewById(R.id.sr_account);
            password=itemView.findViewById(R.id.sr_password);
        }
    }
}
