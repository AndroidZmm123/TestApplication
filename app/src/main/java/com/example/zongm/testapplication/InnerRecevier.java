package com.example.zongm.testapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author zongm on 2018/6/27
 */
public class InnerRecevier extends BroadcastReceiver {

    final String SYSTEM_DIALOG_REASON_KEY = "reason";

    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    //Toast.makeText(MainActivity.this, "Home键被监听", Toast.LENGTH_SHORT).show();
                    //Log.e("zmm","home键---------------->");
                    //Intent intent1 = new Intent(context, Main2Activity.class);
                    //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //PendingIntent pendingIntent =
                    //    PendingIntent.getActivity(context, 0, intent1, 0);
                    //try {
                    //    pendingIntent.send();
                    //} catch (PendingIntent.CanceledException e) {
                    //    e.printStackTrace();
                    //}


                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    //Toast.makeText(MainActivity.this, "多任务键被监听", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
