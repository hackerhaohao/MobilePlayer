package com.hackerhaohao.mobileplayer.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import com.hackerhaohao.mobileplayer.R;


public class SplashActivity extends Activity {

    private final String TAG = SplashActivity.class.getName();

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题一定要写在setContentView方法之前否则会报错
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //3秒后执行run方法,这个方法执行是在主线程中
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
                Log.e(TAG,Thread.currentThread().getName());
            }
        },3000);
    }

    protected  void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startMainActivity();
        return super.onTouchEvent(event);
    }

    //启动多个activity BUG 解决办法，在功能清单文件中设置MainActivity的启动模式是单例，或者设置标志位

    //启动软件同时点击返回按钮，通过handler调用的方法还会继续执行，所以在销毁该activity时候必须要移除消息队列中所有消息
    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
