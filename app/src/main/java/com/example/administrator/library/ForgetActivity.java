package com.example.administrator.library;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Y.SqlHelper;

/**
 * A login screen that offers login via email/password.
 */
public class ForgetActivity extends AppCompatActivity implements OnClickListener {
    EditText forget_id, forget_name;
    Button forget_get, forget_return;
    Connection connection;
    ResultSet resultSet;
    Statement statement;
    NotificationManager mNotificationManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        intview();
    }

    private void intview() {
        forget_id = (EditText) findViewById(R.id.forget_id);
        forget_name = (EditText) findViewById(R.id.forget_name);
        forget_get = (Button) findViewById(R.id.forget_get);
        forget_return = (Button) findViewById(R.id.forget_return);
        forget_get.setOnClickListener(this);
        forget_return.setOnClickListener(this);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_get:
                String id = forget_id.getText().toString().trim();
                connection = SqlHelper.openConnection();
                try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM system_readers WHERE readerid = '" + id + "'");
                    if (resultSet.next()) {
                        String name = resultSet.getString("readername");
                        String password = resultSet.getString("readerpw");
                        String Fname = forget_name.getText().toString().trim();
                        if (Fname.equals(name)) {
                            sendNo();
                        } else {
                            Toast.makeText(this, "信息错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "不存在的ID", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                }
                break;
            case R.id.forget_return:
                finish();
                break;
            default:
                break;
        }
    }

    public void sendNo() {
        if (Build.VERSION.SDK_INT >= 26) {
            String id = "channel_1";
            String description = "123";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, "123", importance);
            //  mChannel.setDescription(description);
            //  mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            //  mChannel.enableVibration(true);
            //  mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(this, id).setContentTitle("Title")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("您有一条新通知")
                    .setContentText("这是一条逗你玩的消息")
                    .setAutoCancel(true)
                    //.setContentIntent(pintent)
                    .build();
            mNotificationManager.notify(1, notification);
        } else {
            sendNotification_24();
        }
    }
    public void sendNotification_24() {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)             //一定要设置
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("您有一条新通知")
                .setContentText("这是一条逗你玩的消息")
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setLights(Color.RED, 1000, 1000)
                .build();
        mNotificationManager.notify(1, notification);
    }
}