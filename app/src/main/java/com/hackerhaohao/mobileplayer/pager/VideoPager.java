package com.hackerhaohao.mobileplayer.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.base.BasePager;
import com.hackerhaohao.mobileplayer.utils.LogUtil;

/**
 * Created by ZhangHao on 2017/2/21.
 * 本地视频页面
 */
public class VideoPager extends BasePager{

    private ListView video_pager_listView;

    private TextView video_pager_tv;

    private ProgressBar video_pager_loading;

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
        //实例化video_pager.xml文件页面
        View view = View.inflate(context, R.layout.video_pager,null);
        video_pager_listView = (ListView) view.findViewById(R.id.video_pager_listView);
        video_pager_tv = (TextView) view.findViewById(R.id.video_pager_tv);
        video_pager_loading = (ProgressBar) view.findViewById(R.id.video_pager_loading);
        return view;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        LogUtil.e("本地视频数据初始化开始...");
        super.initData();
        //获取本地视频数据
        getDataLocal();
    }

    /**
     * 获取本地视频方法
     *
     */
    private void getDataLocal() {
        new Thread(){
            @Override
            public void run() {
                super.run();

            }
        }.start();
    }
}
