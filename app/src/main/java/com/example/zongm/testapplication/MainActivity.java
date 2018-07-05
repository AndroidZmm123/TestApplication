package com.example.zongm.testapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * 测试12:05
 */
public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private CheckBox cb;

    long[] pattern = {10, 500};//等待10毫秒开始震动.震动时长500毫秒
    private String channelId;

    private String beforeChannelId;
    int i = 0;
    private InnerRecevier innerReceiver;

    //public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(0x80000000, 0x80000000);
        setContentView(R.layout.activity_main);


        ////创建广播
        //innerReceiver = new InnerRecevier();
        ////动态注册广播
        //IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        ////启动广播
        //registerReceiver(innerReceiver, intentFilter);


        cb = findViewById(R.id.cb);

        //1:获取系统提供的通知管理服务
        notificationManager = (NotificationManager)
            getSystemService(Context.NOTIFICATION_SERVICE);

        //2：如果是8以上的系统，则新建一个消息通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setChannerl(cb.isChecked());
        }

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3:创建通知
                createNotification(cb.isChecked(), "测试通知");
            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                i++;
                setChannerl(b);
                Log.e("zmm", "onCheckedChanged------------>" + b);
            }
        });

        startAsyncWork();
    }


    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager =(ActivityManager)getApplicationContext()
            .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(),0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if (keyCode == KeyEvent.KEYCODE_BACK) {
        //    Toast.makeText(MainActivity.this, "返回键无效", Toast.LENGTH_SHORT).show();
        //    return true;//return true;拦截事件传递,从而屏蔽back键。
        //}
        //if (KeyEvent.KEYCODE_HOME == keyCode) {
        //    Toast.makeText(getApplicationContext(), "HOME 键已被禁用...", Toast.LENGTH_SHORT).show();
        //    return true;//同理
        //}
        return super.onKeyDown(keyCode, event);
    }

    void startAsyncWork() {
        // This runnable is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the thread finishes (e.g. rotation),
        // the activity instance will leak.
        Runnable work = new Runnable() {
            @Override
            public void run() {
                // Do some slow work in background
                SystemClock.sleep(20000);
            }
        };
        new Thread(work).start();
    }


    private void setChannerl(boolean checked) {

        channelId = "chat" + i;//消息通道的id，以后可以通过该id找到该消息通道
        String channelName = "聊天消息" + i;//消息通道的name
        int importance = NotificationManager.IMPORTANCE_MAX;//通知的优先级
        // .具体的请自行百度。作用就是优先级的不同。可以导致消息出现的形式不一样。
        // MAX是会震动并且出现在屏幕的上方。设置优先级为low或者min时。来通知时都不会震动，
        // 且不会直接出现在屏幕上方
        createNotificationChannel(checked, channelId, channelName, importance);
        beforeChannelId = channelId;
    }


    private void createNotification(boolean isVibrate, String content) {


        Intent intent = new Intent(this, SecondActivity.class);
        /*
         * 调用PendingIntent的静态放法创建一个 PendingIntent对象用于点击通知之后执行的操作，
         * PendingIntent可以理解为延时的Intent，在这里即为点击通知之后执行的Intent
         * 这里调用getActivity(Context context, int requestCode, Intent intent, int flag)方法
         * 表示这个PendingIntent对象启动的是Activity，类似的还有getService方法、getBroadcast方法
         */
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, channelId);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        builder
            .setContentTitle("通知1") // 创建通知的标题
            .setContentText(content) // 创建通知的内容
            .setSmallIcon(R.drawable.ic_launcher_background) // 创建通知的小图标
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher)) // 创建通知的大图标
            /*
             * 首先，无论你是使用自定义视图还是系统提供的视图，上面4的属性一定要设置，不然这个通知显示不出来
             */
            .setWhen(System.currentTimeMillis()) // 设定通知显示的时间
            .setContentIntent(pi) // 设定点击通知之后启动的内容，这个内容由方法中的参数：PendingIntent对象决定
            //.setPriority(NotificationCompat.PRIORITY_MAX) // 设置通知的优先级
            .setAutoCancel(true); // 设置点击通知之后通知是否消失
        //.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg"))) // 设置声音
        /*
         * 设置震动，用一个 long 的数组来表示震动状态，这里表示的是先震动1秒、静止1秒、再震动1秒，这里以毫秒为单位
         * 如果要设置先震动1秒，然后停止0.5秒，再震动2秒则可设置数组为：long[]{1000, 500, 2000}。
         * 别忘了在AndroidManifest配置文件中申请震动的权限
         */

        //builder.setDefaults(NotificationCompat.DEFAULT_LIGHT | Notification.DEFAULT_SOUND);
        if (isVibrate) {
            builder.setVibrate(pattern);
        }

        /*
         * 设置手机的LED灯为蓝色并且灯亮2秒，熄灭1秒，达到灯闪烁的效果，不过这些效果在模拟器上是看不到的，
         * 需要将程序安装在真机上才能看到对应效果，如果不想设置这些通知提示效果，
         * 可以直接设置：setDefaults(Notification.DEFAULT_ALL);
         * 意味将通知的提示效果设置为系统的默认提示效果
         */
        //.setLights(Color.BLUE, 2000, 1000)
        Notification notification = builder.build(); // 创建通知（每个通知必须要调用这个方法来创建）
        /*
         * 使用从系统服务获得的通知管理器发送通知，第一个参数是通知的id，不同的通知应该有不同的id，
         * 这样当我们要取消哪条通知的时候我们调用notificationManager（通知管理器）.cancel(int id)
         * 方法并传入发送通知时的对应id就可以了。在这里如果我们要取消这条通知，
         * 我们调用notificationManager.cancel(1);就可以了
         * 第二个参数是要发送的通知对象
         */
        notificationManager.notify(1, notification);


    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(boolean isVibrate, String channelId, String channelName,
                                           int
                                               importance) {
        if (!TextUtils.isEmpty(beforeChannelId)) {
            //先删除之前的channelId对应的消息通道.
            notificationManager.deleteNotificationChannel(beforeChannelId);
        }
        //重新new一个消息通道。
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        //是否震动
        if (isVibrate) {
            // 设置通知出现时的震动（如果 android 设备支持的话）
            channel.enableVibration(true);
            channel.setVibrationPattern(pattern);
        } else {
            // 设置通知出现时不震动
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
        }
        notificationManager.createNotificationChannel(channel);
    }


    class InnerRecevier extends BroadcastReceiver {

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
                        Toast.makeText(MainActivity.this, "Home键被监听", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(context, MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pendingIntent =
                            PendingIntent.getActivity(context, 0, intent1, 0);
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }


                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        Toast.makeText(MainActivity.this, "多任务键被监听", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (innerReceiver != null) {
            unregisterReceiver(innerReceiver);
        }
    }
}
