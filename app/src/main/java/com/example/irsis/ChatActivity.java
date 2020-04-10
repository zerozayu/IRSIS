package com.example.irsis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.irsis.adapter.MsgAdapter;
import com.example.irsis.myclass.Msg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements View.OnClickListener {
    //拍照相关
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;


    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    protected ImageView button_listFunction;
    protected ImageView button_sendMessage;
    protected ImageView button_takePhoto;

    private LinearLayout listFunction_layout;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;


    private void initMsgs() {
        Msg msg1 = new Msg("你好！", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("你好？你是？", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("我是盖顺！你的儿子！", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initMsgs();//初始化消息数据
        inputText = findViewById(R.id.input_text);
        inputText.setOnClickListener(this);

        button_listFunction = findViewById(R.id.button_listFunction);
        button_listFunction.setOnClickListener(this);

        button_sendMessage = findViewById(R.id.button_sendMessage);
        button_sendMessage.setOnClickListener(this);

        button_takePhoto = findViewById(R.id.button_takePhoto);
        button_takePhoto.setOnClickListener(this);

        picture = findViewById(R.id.picture);

        listFunction_layout = findViewById(R.id.listFunction_layout);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sendMessage:
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    //当有新消息时，刷新RecyclerView中的显示
                    adapter.notifyItemInserted(msgList.size() - 1);
                    //将RecyclerView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    //清空输入框中的内容
                    inputText.setText("");
                }
                break;
            case R.id.button_listFunction:
                if (listFunction_layout.getVisibility() == View.GONE) {
                    listFunction_layout.setVisibility(View.VISIBLE);
                } else {
                    listFunction_layout.setVisibility(View.GONE);
                }
                break;
            case R.id.input_text:
                listFunction_layout.setVisibility(View.GONE);
                break;

            case R.id.button_takePhoto:
                //创建File对象，用于存储拍照后的图片
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

                if (outputImage.exists()) {
                    boolean delete = outputImage.delete();
                }

                try {
                    boolean newFile = outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(ChatActivity.this,
                            "com.example.irsis.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    picture.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }
}
