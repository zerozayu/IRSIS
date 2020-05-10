package com.example.irsis.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.irsis.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SubmitProblemActivity extends BaseActivity implements View.OnClickListener {
    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;

    private EditText edit_problemName=null;
    private EditText edit_problemContent=null;
    private ImageView picture_problem;
    private ImageView button_takePhoto;
    private Button button_submitProblem;

    byte[] images=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_problem);

        findView();
        setListener();

        //继续上次编辑
        String problemNameText = loadProblemName();
        String problemContentText = loadProblemContent();
        if (!TextUtils.isEmpty(problemNameText) || !TextUtils.isEmpty(problemContentText)) {
            edit_problemName.setText(problemNameText);
            edit_problemName.setSelection(problemNameText.length());
            edit_problemContent.setText(problemContentText);
            edit_problemContent.setSelection(problemContentText.length());
            Toast.makeText(this, "继续上次编辑", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_takePhoto_problem:
                File outputProblemImage = new File(getExternalCacheDir(), "output_problemImage");
                try {
                    if (outputProblemImage.exists()) {
                        outputProblemImage.delete();
                    }
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

            case R.id.button_submitProblem:


                if (images==null){
                    //弹出对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("通知");
                    builder.setMessage("必须有照片！");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }else {
                    //弹出对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("通知");
                    builder.setMessage("提交成功");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {



                            onBackPressed();
                            edit_problemName.setText("");
                            edit_problemContent.setText("");
                            finish();
                        }
                    });
                    builder.show();
                }

                break;


            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String problemName = edit_problemName.getText().toString();
        String problemContent = edit_problemContent.getText().toString();
        saveProblemName(problemName);
        saveProblemContent(problemContent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        //将拍摄的照片显示出来
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture_problem.setImageBitmap(bitmap);
                        images=img(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;
        }
    }



    public void findView(){
        edit_problemName = findViewById(R.id.editText_problemName);
        edit_problemContent = findViewById(R.id.editText_problemContent);
        picture_problem = findViewById(R.id.picture_problem);
        button_takePhoto = findViewById(R.id.button_takePhoto_problem);
        button_submitProblem = findViewById(R.id.button_submitProblem);
    }
    public void setListener(){
        button_takePhoto.setOnClickListener(this);
        button_submitProblem.setOnClickListener(this);
    }

    //save方法用于保存inputText
    public void saveProblemName(String problemNameText) {
        BufferedWriter bw = null;
        try {
            //造对象造流
            FileOutputStream fos = openFileOutput("problemName.txt", Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            //2.操作
            bw.write(problemNameText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //3.关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void saveProblemContent(String problemContentText) {
        BufferedWriter bw = null;
        try {
            //造对象造流
            FileOutputStream fos = openFileOutput("problemContent.txt", Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            //2.操作
            bw.write(problemContentText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //3.关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //load方法用于从文件中读取数据
    public String loadProblemName() {
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();

        try {
            FileInputStream fis = openFileInput("problemName.txt");
            br = new BufferedReader(new InputStreamReader(fis));

            String line = "";
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    public String loadProblemContent() {
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        ;
        try {
            FileInputStream fis = openFileInput("problemContent.txt");
            br = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    private byte[] img(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
