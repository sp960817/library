package com.example.administrator.library;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String Fpassword = intent.getStringExtra("password");
        ClipboardManager clipboardManager =(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("password",""+Fpassword+"");
        clipboardManager.setPrimaryClip(clipData);
    }
}
