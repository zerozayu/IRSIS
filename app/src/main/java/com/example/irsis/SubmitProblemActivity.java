package com.example.irsis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.irsis.Fragment.ProblemNameFragment;
import com.example.irsis.myclass.Problem;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SubmitProblemActivity extends BaseActivity implements View.OnClickListener {
    public static final int TAKE_PHOTO = 2;
    private Uri imageUri;

    private EditText edit_problemName;
    private EditText edit_problemContent;
    private ImageView picture_problem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_problem);
        //数据库
        LitePal.getDatabase();

        //声明组件:问题名字、问题内容、图片显示(默认invisible)、拍照按钮、提交按钮
        edit_problemName = findViewById(R.id.editText_problemName);
        edit_problemContent = findViewById(R.id.editText_problemContent);

        picture_problem = findViewById(R.id.picture_problem);


        ImageView button_takePhoto = findViewById(R.id.button_takePhoto_problem);
        button_takePhoto.setOnClickListener(this);

        Button button_submitProblem = findViewById(R.id.button_submitProblem);
        button_submitProblem.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_submitProblem:
                //数据库存储操作
                Problem problem = new Problem();
                //problem.setpId(1);
                problem.setName(edit_problemName.getText().toString());
                problem.setContent(edit_problemContent.getText().toString());
                problem.setImageId(R.drawable.problem128);
                problem.save();

                //弹出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("通知");
                builder.setMessage("提交成功");
                builder.setCancelable(false);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.show();
                break;

            case R.id.button_takePhoto_problem:
                File outputProblemImage = new File(getExternalCacheDir(), "output_problemImage");
                if (outputProblemImage.exists()) {
                    outputProblemImage.delete();
                }
                try {
                    outputProblemImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(SubmitProblemActivity.this,
                            "com.example.irsis.fileprovider", outputProblemImage);
                } else {
                    imageUri = Uri.fromFile(outputProblemImage);
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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    picture_problem.setImageBitmap(bitmap);

                }
                break;
            default:
                break;
        }
    }
}
