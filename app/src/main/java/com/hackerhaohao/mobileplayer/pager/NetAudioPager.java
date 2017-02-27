package com.hackerhaohao.mobileplayer.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.hackerhaohao.mobileplayer.base.BasePager;
import com.hackerhaohao.mobileplayer.utils.LogUtil;

/**
 * Created by ZhangHao on 2017/2/21.
 * 网络音乐页面
 */
public class NetAudioPager extends BasePager {

    private TextView tv;

    public NetAudioPager(Context context) {
        super(context);
    }

    /**
     * 初始化页面
     * @return 返回页面视图
     */
    @Override
    public View intiView() {
        LogUtil.e("网络音乐UI页面初始化开始...");
        tv = new TextView(context);
        tv.setTextColor(Color.RED);
        tv.setTextSize(23);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        LogUtil.e("网络音乐数据初始化开始...");
        tv.setText("网络音乐");
        super.initData();
    }
}
