package com.example.zongm.testapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    InnerRecevier innerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().clearFlags(FLAG_HOMEKEY_DISPATCHED);//屏蔽菜单键
        //this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        setContentView(R.layout.activity_main2);


        //创建广播
        innerReceiver = new InnerRecevier();
        //动态注册广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //启动广播
        registerReceiver(innerReceiver, intentFilter);
    }


    @Override
    public void onAttachedToWindow() {

        super.onAttachedToWindow();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.e("zmm","onKeyDown--------------->"+keyCode);
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//// 监控返回键
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
//// 监控菜单键
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
//            Toast.makeText(getApplicationContext(), "HOME 键已被禁用...",
//                Toast.LENGTH_LONG).show();
//// 屏蔽Home键
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (innerReceiver != null) {
            unregisterReceiver(innerReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

}
