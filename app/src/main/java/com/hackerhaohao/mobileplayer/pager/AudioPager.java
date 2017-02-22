package com.hackerhaohao.mobileplayer.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hackerhaohao.mobileplayer.base.BasePager;
import com.hackerhaohao.mobileplayer.utils.LogUtil;

/**
 * Created by ZhangHao on 2017/2/21.
 * 本地音乐页面
 */
public class AudioPager extends BasePager{

    private TextView tv;

    public AudioPager(Context context) {
        super(context);
    }

    /**
     * 初始化页面
     * @return 返回页面视图
     */
    @Override
    public View intiView() {
        LogUtil.e("本地音乐页面初始化开始...");
        tv = new TextView(context);
        tv.setTextSize(22);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        LogUtil.e("本地音乐数据初始化开始...");
        tv.setText("本地音乐");
        super.initData();
    }
}
