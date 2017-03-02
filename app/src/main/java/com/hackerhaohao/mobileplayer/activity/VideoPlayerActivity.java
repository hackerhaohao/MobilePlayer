package com.hackerhaohao.mobileplayer.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hackerhaohao.mobileplayer.R;

/**
 * Created by ZhangHao on 2017/3/1.
 * 控制视频播放的Activity
 */
public class VideoPlayerActivity extends Activity{

    private VideoView video_player_vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_player);
        video_player_vv = (VideoView) findViewById(R.id.video_player_vv);
        //得到播放地址
        Uri uri = getIntent().getData();
        video_player_vv.setVideoURI(uri);
        //设置监听
        video_player_vv.setOnPreparedListener(new MyOnPreparedListener());
        //设置Android系统提供的播放控制bar
        //video_player_vv.setMediaController(new MediaController(this));
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        /**
         * Called when the media file is ready for playback.
         *
         * @param mp the MediaPlayer that is ready for playback
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            //开始播放
            mp.start();
        }
    }
}
