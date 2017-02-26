package com.hackerhaohao.mobileplayer.pager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.base.BasePager;
import com.hackerhaohao.mobileplayer.po.MediaItem;
import com.hackerhaohao.mobileplayer.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHao on 2017/2/21.
 * 本地视频页面
 */
public class VideoPager extends BasePager{

    private ListView video_pager_listView;

    private TextView video_pager_tv;

    private ProgressBar video_pager_loading;

    private List<MediaItem> mediaList;

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
     * 1、遍历所有文件的后缀名，缺点是文件数目庞大的情况下程序执行速度很慢
     * 2、利用内容提供者，android系统在内存卡安装好之后会发出一条广播，扫描内存文件，将文件信息保存在内容提供者当中
     */
    private void getDataLocal() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                Cursor cursor =  contentResolver.query(uri, null, null, null,null);
                if (null != cursor){
                    mediaList = new ArrayList<>();
                    while (cursor.moveToNext()){
                        MediaItem mediaItem = new MediaItem();
                        mediaList.add(mediaItem);
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        Long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    }
                }
            }
        }.start();
    }
}
