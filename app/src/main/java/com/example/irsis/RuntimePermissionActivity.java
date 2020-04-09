package com.example.irsis;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RuntimePermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permisson);
        Button button_makeCall = findViewById(R.id.button_makeCall);
        button_makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断用户是否已经给过授权。两个参数（context，权限名）
                if (ContextCompat.checkSelfPermission(RuntimePermissionActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RuntimePermissionActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }
            }
        });
    }


    //打电话方法（！！！！有错误）
    @SuppressLint("MissingPermission")
    private void call() {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                }else {
                    Toast.makeText(this,"你拒绝了许可",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
