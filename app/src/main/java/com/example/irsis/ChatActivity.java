package com.example.irsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.irsis.adapter.MsgAdapter;
import com.example.irsis.myclass.Msg;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Msg> msgList =new ArrayList<>();
    private EditText inputText;
    protected Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    private void initMsgs(){
        Msg msg1=new Msg("你好！",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("你好？你是？",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3=new Msg("我是盖顺！你的儿子！",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initMsgs();//初始化消息数据
        inputText=findViewById(R.id.input_text);
        send=findViewById(R.id.button_send);
        msgRecyclerView=findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter =new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg =new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    //当有新消息时，刷新RecyclerView中的显示
                    adapter.notifyItemInserted(msgList.size()-1);
                    //将RecyclerView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    //清空输入框中的内容
                    inputText.setText("");
                }
            }
        });
    }
}
