package com.hackerhaohao.mobileplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by ZhangHao on 2017/2/20.
 */
public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        TextView tv = new TextView(this);
        tv.setText("---主页面---");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(16);
        setContentView(tv);
    }
}
