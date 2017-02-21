package com.hackerhaohao.mobileplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.hackerhaohao.mobileplayer.R;

/**
 * Created by ZhangHao on 2017/2/20.
 */
public class MainActivity extends Activity{

    private FrameLayout fl_main_content;

    private RadioGroup rg_bottom_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);
        fl_main_content =(FrameLayout) findViewById(R.id.fl_main_content);
        //默认选中项
        rg_bottom_tag.check(R.id.rb_video);
    }
}
