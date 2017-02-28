package com.hackerhaohao.mobileplayer.pager;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.adapter.VideoAdapter;
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

    /**
     * 数据集合
     */
    private List<MediaItem> mediaList;

    private Handler handler = new Handler() {
        /**
         * 子类必须重写该方法，处理相关消息更新UI
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaList != null && mediaList.size() > 0){
                //有数据，不显示文本，设置适配器
                video_pager_tv.setVisibility(View.GONE);
                VideoAdapter adapter = new VideoAdapter(mediaList,context);
                video_pager_listView.setAdapter(adapter);
            } else {
                //无数据,显示文本
                video_pager_tv.setVisibility(View.VISIBLE);
            }
            //ProgressBar不显示
            video_pager_loading.setVisibility(View.GONE);
        }
    };

    public VideoPager(Context context) {
        super(context);
    }

    /**
     * 初始化页面
     * @return 返回页面视图
     */
    @Override
    public View intiView() {
        LogUtil.e("本地视频UI页面初始化开始...");
        //实例化video_pager.xml文件页面
        View view = View.inflate(context, R.layout.video_pager,null);
        video_pager_listView = (ListView) view.findViewById(R.id.video_pager_listView);
        video_pager_tv = (TextView) view.findViewById(R.id.video_pager_tv);
        video_pager_loading = (ProgressBar) view.findViewById(R.id.video_pager_loading);
        //设置list项的监听
        video_pager_listView.setOnItemClickListener(new MyOnItemClickListener());
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
     * 获取本地视频方法:在线程中进行文件读取
     * 1、遍历所有文件的后缀名，缺点是文件数目庞大的情况下程序执行速度很慢
     * 逻辑：在子线程中通过ContentProvide加载SD卡的视频信息放入集合中，利用Handler在线程中传递消息改变UI
     */
    private void getDataLocal() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                //内容解析器
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                Cursor cursor =  contentResolver.query(uri, null, null, null,null);
                if (null != cursor){
                    mediaList = new ArrayList<>();
                    while (cursor.moveToNext()){
                        MediaItem mediaItem = new MediaItem();
                        mediaList.add(mediaItem);
                        String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        mediaItem.setDisplayName(displayName);
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        mediaItem.setDuration(duration);
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                        mediaItem.setSize(size);
                        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST));
                        mediaItem.setArtist(artist);
                        //视频播放地址
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        mediaItem.setData(data);
                        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                        mediaItem.setTitle(title);
                    }
                    //关闭游标
                    cursor.close();
                }
                //传递消息
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    //list监听器
    class  MyOnItemClickListener implements AdapterView.OnItemClickListener{

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p/>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param parent   The AdapterView where the click happened.
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //是否需要判断集合为空？
            if (mediaList != null && mediaList.size() > 0){
                MediaItem mediaItem = mediaList.get(position);
                Toast.makeText(context,mediaItem.toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(mediaItem.getData()),"vedio/*");
                context.startActivity(intent);
            }
        }
    }
}
