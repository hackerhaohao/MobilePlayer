package com.hackerhaohao.mobileplayer.pager;

import android.content.Context;
import android.view.View;

import com.hackerhaohao.mobileplayer.base.BasePager;
import com.hackerhaohao.mobileplayer.utils.LogUtil;

/**
 * Created by ZhangHao on 2017/2/21.
 * 网络视频页面
 */
public class NetVideoPager extends BasePager{

    public NetVideoPager(Context context) {
        super(context);
    }

    /**
     * 初始化页面
     * @return 返回页面视图
     */
    @Override
    public View intiView() {
        LogUtil.e("网络视频页面初始化开始...");
        return null;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initDate() {
        LogUtil.e("网络视频数据初始化开始...");
        super.initDate();
    }
}
