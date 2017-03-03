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
        //设置播放准备监听
        video_player_vv.setOnPreparedListener(new MyOnPreparedListener());
        //设置播放完成监听
        video_player_vv.setOnCompletionListener(new MyOnCompletionListener());
        //设置播放出错监听
        video_player_vv.setOnErrorListener(new MyOnErrorListener());
        //设置Android系统提供的播放控制bar
        //video_player_vv.setMediaController(new MediaController(this));
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        /**
         * Called when the media file is ready for playback.
         * @param mp the MediaPlayer that is ready for playback
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            //开始播放
            video_player_vv.start();
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{

        /**
         * Called when the end of a media source is reached during playback.
         * @param mp the MediaPlayer that reached the end of the file
         */
        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        /**
         * Called to indicate an error.
         * @param mp    the MediaPlayer the error pertains to
         * @param what  the type of error that has occurred:
         *              <ul>
         *              <li>{@link #MEDIA_ERROR_UNKNOWN}
         *              <li>{@link #MEDIA_ERROR_SERVER_DIED}
         *              </ul>
         * @param extra an extra code, specific to the error. Typically
         *              implementation dependent.
         *              <ul>
         *              <li>{@link #MEDIA_ERROR_IO}
         *              <li>{@link #MEDIA_ERROR_MALFORMED}
         *              <li>{@link #MEDIA_ERROR_UNSUPPORTED}
         *              <li>{@link #MEDIA_ERROR_TIMED_OUT}
         *              <li><code>MEDIA_ERROR_SYSTEM (-2147483648)</code> - low-level system error.
         *              </ul>
         * @return True if the method handled the error, false if it didn't.
         * Returning false, or not having an OnErrorListener at all, will
         * cause the OnCompletionListener to be called.
         */
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    }
}
