package com.example.irsis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.irsis.myclass.Problem;

public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button button_sendNotice = findViewById(R.id.button_sendNotice);
        button_sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sendNotice:
                Intent intent = new Intent(this, ProblemActivity.class);
                PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification =new NotificationCompat.Builder(this,"default")
                        .setContentTitle("这是title")
                        .setContentText("这是text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.irsis)
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.irsis))
                        .build();
                manager.notify(1,notification);
                break;
            default:
                break;
        }
    }
}
