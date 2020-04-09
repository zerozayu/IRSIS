package com.example.irsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FilePersistenceActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_persistence);

        edit = findViewById(R.id.file_edit);
        button = findViewById(R.id.button_fileCommit);
        button.setOnClickListener(this);

        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            edit.setText(inputText);
            edit.setSelection(inputText.length());
            Toast.makeText(this, "启用上次保存的内容继续编辑。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_fileCommit:
                String inputText = edit.getText().toString();
                sava(inputText);
                Toast.makeText(this, "保存成功，下次打开可以继续编辑。", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }


    //save方法用于保存inputText
    public void sava(String inputText) {
        BufferedWriter bw = null;
        try {
            //造对象造流
            FileOutputStream fos = openFileOutput("data.txt", Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            //2.操作
            bw.write(inputText);
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
    public String load() {
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        ;
        try {
            FileInputStream fis = openFileInput("data.txt");
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

}
