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
 * 本地视频页面
 */
public class VideoPager extends BasePager{

    private TextView tv;

    public VideoPager(Context context) {
        super(context);
    }

    /**
     * 初始化页面
     * @return 返回页面视图
     */
    @Override
    public View intiView() {
        LogUtil.e("本地视频页面初始化开始...");
        tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(12);
        tv.setTextColor(Color.RED);
        return tv;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        LogUtil.e("本地视频数据初始化开始...");
        tv.setText("本地视频 ");
        super.initData();
    }
}
