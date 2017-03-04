package com.hackerhaohao.mobileplayer.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.utils.Utils;


/**
 * Created by ZhangHao on 2017/3/1.
 * 控制视频播放的Activity
 */
public class VideoPlayerActivity extends Activity implements View.OnClickListener {

    private VideoView video_player_vv;

    private LinearLayout vpControllerTop;
    private LinearLayout vpControllerTopStatus;
    private TextView vpControllerVideoName;
    private ImageView vpControllerVideoBattery;
    private TextView vpControllerVideoSystemTime;
    private LinearLayout bgPlayerControl;
    private Button vpControllerVideoVoice;
    private SeekBar vpControllerVoiceSeekBar;
    private Button vpControllerVideoSwitch;
    private LinearLayout vpControllerBottom;
    private TextView vpControllerVideoCurrentDuration;
    private SeekBar vpControllerVideoSeekBar;
    private TextView vpControllerVideoDuration;
    private LinearLayout bgPlayerControlBottom;
    private Button vpControllerVideoExit;
    private Button vpControllerVideoPre;
    private Button vpControllerVideoPlayPause;
    private Button vpControllerVideoNext;
    private Button vpControllerVideoFullScreen;

    private static final int PROGRESS = 0;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-03-05 00:18:09 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_video_player);
        video_player_vv = (VideoView) findViewById(R.id.video_player_vv);
        vpControllerTop = (LinearLayout)findViewById( R.id.vp_controller_top );
        vpControllerTopStatus = (LinearLayout)findViewById( R.id.vp_controller_top_status );
        vpControllerVideoName = (TextView)findViewById( R.id.vp_controller_video_name );
        vpControllerVideoBattery = (ImageView)findViewById( R.id.vp_controller_video_battery );
        vpControllerVideoSystemTime = (TextView)findViewById( R.id.vp_controller_video_system_time );
        bgPlayerControl = (LinearLayout)findViewById( R.id.bg_player_control );
        vpControllerVideoVoice = (Button)findViewById( R.id.vp_controller_video_voice );
        vpControllerVoiceSeekBar = (SeekBar)findViewById( R.id.vp_controller_voice_seekBar );
        vpControllerVideoSwitch = (Button)findViewById( R.id.vp_controller_video_switch );
        vpControllerBottom = (LinearLayout)findViewById( R.id.vp_controller_bottom );
        vpControllerVideoCurrentDuration = (TextView)findViewById( R.id.vp_controller_video_currentDuration );
        vpControllerVideoSeekBar = (SeekBar)findViewById( R.id.vp_controller_video_seekBar );
        vpControllerVideoDuration = (TextView)findViewById( R.id.vp_controller_video_duration );
        bgPlayerControlBottom = (LinearLayout)findViewById( R.id.bg_player_control_bottom );
        vpControllerVideoExit = (Button)findViewById( R.id.vp_controller_video_exit );
        vpControllerVideoPre = (Button)findViewById( R.id.vp_controller_video_pre );
        vpControllerVideoPlayPause = (Button)findViewById( R.id.vp_controller_video_play_pause );
        vpControllerVideoNext = (Button)findViewById( R.id.vp_controller_video_next );
        vpControllerVideoFullScreen = (Button)findViewById( R.id.vp_controller_video_fullScreen );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-03-05 00:18:09 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     * 该方法可以使用switch case方式来写
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.vp_controller_video_voice:
            break;
            case  R.id.vp_controller_video_switch:
            break;
            case  R.id.vp_controller_video_exit:
            break;
            case  R.id.vp_controller_video_pre:
            break;
            //播放按钮被点击
            case  R.id.vp_controller_video_play_pause:
            //1、如果在播放状态就暂停，设置图片可播放，2、如果暂停状态就播放，设置图片可暂停
                if (video_player_vv.isPlaying()){
                    video_player_vv.pause();
                    vpControllerVideoPlayPause.setBackgroundResource(R.drawable.vp_controller_video_play_start_selector);
                }else {
                    video_player_vv.start();
                    vpControllerVideoPlayPause.setBackgroundResource(R.drawable.vp_controller_video_play_pause_selector);
                }
                break;
            case  R.id.vp_controller_video_next:
            break;case  R.id.vp_controller_video_fullScreen:
            break;
            default:
            break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PROGRESS:
                    //根据视频播放位置设置当前已经播放时长
                    vpControllerVideoCurrentDuration.setText(new Utils().stringForTime(video_player_vv.getCurrentPosition()));
                    //根据视频播放位置设置seekBar的当前进度
                    vpControllerVideoSeekBar.setProgress(video_player_vv.getCurrentPosition());
                    //每一秒钟更新一次，先移除消息，再发送消息
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //初始化页面控件
        findViews();
        //得到播放地址
        Uri uri = getIntent().getData();
        video_player_vv.setVideoURI(uri);
        setListener();
        //设置Android系统提供的播放控制bar
        //video_player_vv.setMediaController(new MediaController(this));
    }

    private void setListener() {
        //设置播放准备监听
        video_player_vv.setOnPreparedListener(new MyOnPreparedListener());
        //设置播放完成监听
        video_player_vv.setOnCompletionListener(new MyOnCompletionListener());
        //设置播放出错监听
        video_player_vv.setOnErrorListener(new MyOnErrorListener());
        vpControllerVideoVoice.setOnClickListener( this );
        vpControllerVideoSwitch.setOnClickListener( this );
        vpControllerVideoExit.setOnClickListener( this );
        vpControllerVideoPre.setOnClickListener(this);
        vpControllerVideoPlayPause.setOnClickListener(this);
        vpControllerVideoNext.setOnClickListener(this);
        vpControllerVideoFullScreen.setOnClickListener(this);
        //视频进度条的监听
        vpControllerVideoSeekBar.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());
    }

    class VideoOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        /**
         *在seekbar拖动的时候回调方法
         * 如果fromUser 是true则说明seekbar的移动是人为的否则是控件自动移动
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                video_player_vv.seekTo(progress);
            }
        }
        //当开始滑动seekbar时候回调该方法
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        //当结束滑动seekabr时候回调该方法
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        /**
         * Called when the media file is ready for playback.
         * @param mp the MediaPlayer that is ready for playback
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            //准备完毕开始播放
            video_player_vv.start();
            //设置视频总时长
            vpControllerVideoDuration.setText(new Utils().stringForTime(video_player_vv.getDuration()));
            //设置视频的总长度为video_seekBar的多少等分
            vpControllerVideoSeekBar.setMax(video_player_vv.getDuration());
            //发送消息更新UI
            handler.sendEmptyMessage(PROGRESS);
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
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    }
}
