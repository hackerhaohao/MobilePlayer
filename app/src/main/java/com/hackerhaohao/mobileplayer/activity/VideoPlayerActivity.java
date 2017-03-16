package com.hackerhaohao.mobileplayer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.po.MediaItem;
import com.hackerhaohao.mobileplayer.utils.LogUtil;
import com.hackerhaohao.mobileplayer.utils.Utils;
import com.hackerhaohao.mobileplayer.view.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by ZhangHao on 2017/3/1.
 * 控制视频播放的Activity
 */
public class VideoPlayerActivity extends Activity implements View.OnClickListener {
    /**
     * 播放视频seekBar
     */
    private static final int PROGRESS = 0;
    /**
     * 隐藏播放控制面板
     */
    private static final int HIDE_PLAYERCONTROLLER = 2;
    /**
     * 全屏显示
     */
    private static final int FULL_SCREEN = 1;
    /**
     * 默认屏幕显示
     */
    private static final int DEFAULT_SCREEN = 2;
    private VideoView video_player_vv;
    //播放器控制面板，实际上是一个相对布局
    private RelativeLayout video_player_controller;
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

    private MyBatteryReceive myBatteryReceive;


    /**
     * 传递的播放列表
     */
    private ArrayList<MediaItem> mediaList;
    /**
     * 播放位置
     */
    private int position;
    /**
     * 播放路径
     */
    private Uri uri;

    /**
     * 手势识别
     */
    private GestureDetector gestureDetector;
    /**
     * 是否显示控制面板
     */
    private boolean isShowPlayerController = false;
    /**
     * 当前设备的屏幕宽度
     */
    private int screenWidth = 0;
    /**
     * 当前设备的屏幕高度
     */
    private int screenHeight = 0;
    /**
     * 是否全屏显示
     */
    private boolean isFullScreen = false;
    /**
     * 视频的真实宽度
     */
    private int videoWith = 0;
    /**
     * 视频的真实高度
     */
    private int videoHeight = 0;
    /**
     * 当前音量
     */
    private int currentVoice;
    /**
     * 最大音量
     */
    private int maxVoice;
    /**
     * 声音管理器
     */
    private AudioManager am;
    /**
     * 是否静音标志位
     */
    private boolean flag = false;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-03-05 00:18:09 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_player);
        video_player_vv = (VideoView) findViewById(R.id.video_player_vv);
        video_player_controller = (RelativeLayout) findViewById(R.id.video_player_controller);
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
                flag = !flag;
                updateVoice(currentVoice,flag);
            break;
            case  R.id.vp_controller_video_switch:
            break;
            case  R.id.vp_controller_video_exit:
                finish();
            break;
            case  R.id.vp_controller_video_pre:
                playPreVideo();
                break;
            //播放按钮被点击
            case  R.id.vp_controller_video_play_pause:
                videoPlayAndPause();
                break;
            case  R.id.vp_controller_video_next:
                playNextVideo();
                break;
            case  R.id.vp_controller_video_fullScreen:
                setVideoFullScreenAndDefault();
            break;
            default:
            break;
        }
        //只要有按钮按下去就取消控制面板隐藏指令
        handler.removeMessages(HIDE_PLAYERCONTROLLER);
        handler.sendEmptyMessageDelayed(HIDE_PLAYERCONTROLLER,3000);
    }

    /**
     * 播放下一个视频
     */
    private void playNextVideo() {
        if (null != mediaList && mediaList.size() > 0){
            position++;
            if (position < mediaList.size()){
                MediaItem mediaItem = mediaList.get(position);
                video_player_vv.setVideoPath(mediaItem.getData());
                vpControllerVideoName.setText(mediaItem.getDisplayName());
                //设置按钮状态
                setButtonStatue();
            }
        }
    }

    /**
     * 播放上一个视频
     * ps:没有判断外部调用时候设置按钮状态，等测试时候再说
     */
    private void playPreVideo() {
        if (null != mediaList && mediaList.size() > 0){
            position--;
            if (position >= 0){
                MediaItem mediaItem = mediaList.get(position);
                video_player_vv.setVideoPath(mediaItem.getData());
                vpControllerVideoName.setText(mediaItem.getDisplayName());
                //设置按钮状态
                setButtonStatue();
            }
        }
    }

    /**
     * 设置上一个下一个按钮的状态 使用地方有三个初始化数据、点击上一个按钮、点击下一个按钮
     * 需要测试的当只有两个视频的时候按钮状态是否正确
     else if (mediaList.size() == 2){
     //当列表只有两个视频时候，播放第一个视频上一个按钮失效下一个按钮生效，当播放第二个视频时候上一个按钮生效下一个按钮失效
     if (position == 0){
     vpControllerVideoPre.setEnabled(false);
     vpControllerVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);

     vpControllerVideoNext.setEnabled(true);
     vpControllerVideoNext.setBackgroundResource(R.drawable.vp_controller_video_next_selector);
     }else{
     vpControllerVideoNext.setEnabled(false);
     vpControllerVideoNext.setBackgroundResource(R.drawable.btn_next_gray);

     vpControllerVideoPre.setEnabled(true);
     vpControllerVideoPre.setBackgroundResource(R.drawable.vp_controller_video_pre_selector);
     }
     }
     */
    private void setButtonStatue() {
        if (null != mediaList && mediaList.size() > 0){
            if (mediaList.size() == 1){
                //当列表只有一个视频时候上一个和下一个按钮都不可用
                vpControllerVideoPre.setEnabled(false);
                vpControllerVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
                vpControllerVideoNext.setEnabled(false);
                vpControllerVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
            } else {
                //当列表三个及其以上视频的时候，播放第一个视频上一个按钮失效下一个按钮生效，当播放最后一个视频时候上一个按钮生效下一个按钮失效其余全部有效
                if (position == 0){
                    vpControllerVideoPre.setEnabled(false);
                    vpControllerVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);

                    vpControllerVideoNext.setEnabled(true);
                    vpControllerVideoNext.setBackgroundResource(R.drawable.vp_controller_video_next_selector);
                }else if (position == mediaList.size()-1){
                    vpControllerVideoNext.setEnabled(false);
                    vpControllerVideoNext.setBackgroundResource(R.drawable.btn_next_gray);

                    vpControllerVideoPre.setEnabled(true);
                    vpControllerVideoPre.setBackgroundResource(R.drawable.vp_controller_video_pre_selector);
                } else {
                    vpControllerVideoPre.setEnabled(true);
                    vpControllerVideoPre.setBackgroundResource(R.drawable.vp_controller_video_pre_selector);
                    vpControllerVideoNext.setEnabled(true);
                    vpControllerVideoNext.setBackgroundResource(R.drawable.vp_controller_video_next_selector);
                }
            }
        }else if (null != uri){
            //当列表只有一个视频时候上一个和下一个按钮都不可用
            vpControllerVideoPre.setEnabled(false);
            vpControllerVideoPre.setBackgroundResource(R.drawable.btn_pre_gray);
            vpControllerVideoNext.setEnabled(false);
            vpControllerVideoNext.setBackgroundResource(R.drawable.btn_next_gray);
        }
    }

    /**
     * 视频播放和暂停
     */
    private void videoPlayAndPause() {
        //1、如果在播放状态就暂停，设置图片可播放，2、如果暂停状态就播放，设置图片可暂停
        if (video_player_vv.isPlaying()){
            video_player_vv.pause();
            vpControllerVideoPlayPause.setBackgroundResource(R.drawable.vp_controller_video_play_start_selector);
        }else {
            video_player_vv.start();
            vpControllerVideoPlayPause.setBackgroundResource(R.drawable.vp_controller_video_play_pause_selector);
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
                    //显示系统时间
                    vpControllerVideoSystemTime.setText(getSystemTime());
                    //每一秒钟更新一次，先移除消息，再发送消息
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
                case HIDE_PLAYERCONTROLLER:
                    hidePlayerController();
                    break;
            }
        }
    };

    /**
     * 获取系统时间
     * @return
     */
    private String getSystemTime() {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        return s.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化页面控件
        findViews();
        //获取播放数据
        getData();
        //设置数据
        setData();
        //设置Android系统提供的播放控制bar
        //video_player_vv.setMediaController(new MediaController(this));
        setBroadCastReceive();
        gesTureDetector();
        getScreenWidthAndHeight();
        //获得系统声音控制
        getSystemVoice();
        //设置监听器
        setListener();
        //设置屏幕长亮不锁屏幕
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 获得系统声音控制并设置seekBar
     */
    private void getSystemVoice() {
        //获取当前音量，获取系统所能设置对打音量0~15
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVoice = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVoice = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        vpControllerVoiceSeekBar.setProgress(currentVoice);
        vpControllerVoiceSeekBar.setMax(maxVoice);
    }

    /**
     * 获得当前设备的屏幕尺寸
     */
    private void getScreenWidthAndHeight() {
        //获取屏幕的尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    //设置手势识别处理方法
    private void gesTureDetector() {
        //手势识别处理
        gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            /**
             * 长按,控制播放和暂停
             * @param e
             */
            @Override
            public void onLongPress(MotionEvent e) {
                videoPlayAndPause();
                super.onLongPress(e);
            }

            /**
             * 发生确定的单击时执行，控制播放面板
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isShowPlayerController){
                    //显示状态（TRUE）下要隐藏控制面板，隐藏面板后移除handler消息
                    hidePlayerController();
                    handler.removeMessages(HIDE_PLAYERCONTROLLER);
                } else {
                    //隐藏状态（FALSE）下要显示控制面板,同时发消息间隔三秒隐藏控制面板
                    showPlayerController();
                    handler.sendEmptyMessageDelayed(HIDE_PLAYERCONTROLLER,3000);
                }
                return super.onSingleTapConfirmed(e);
            }

            /**
             * 双击 全屏显示
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                setVideoFullScreenAndDefault();
                return super.onDoubleTap(e);
            }
        });
    }

    /**
     * 设置全屏还是默认播放
     */
    private void setVideoFullScreenAndDefault() {
        if(isFullScreen){
            //默认
            setVideoType(DEFAULT_SCREEN);
        }else {
            //全屏
            setVideoType(FULL_SCREEN);
        }
    }

    /**
     * 设置播放屏幕大小
     * @param defaultScreen
     */
    private void setVideoType(int defaultScreen) {
        switch (defaultScreen){
            case FULL_SCREEN:
                //1 根据屏幕大小设置视频大小 2 设置控制面板的按钮式样 3 设置标志位
                video_player_vv.setVideoSize(screenWidth,screenHeight);
                vpControllerVideoFullScreen.setBackgroundResource(R.drawable.vp_controller_video_default_screen_selector);
                isFullScreen = true;
                break;
            case DEFAULT_SCREEN:
                //1 根据屏幕大小设置视频大小 2 设置控制面板的按钮式样 3 设置标志位
                //屏幕大小
                int width = screenWidth;
                int height = screenHeight;
                int mVideoWidth = videoWith;
                int mVideoHeight = videoHeight;
                //等比例拉大算法
                if ( mVideoWidth * height  < width * mVideoHeight ) {
                    width = height * mVideoWidth / mVideoHeight;
                } else if ( mVideoWidth * height  > width * mVideoHeight ) {
                    height = width * mVideoHeight / mVideoWidth;
                }
                video_player_vv.setVideoSize(width,height);
                vpControllerVideoFullScreen.setBackgroundResource(R.drawable.vp_controller_video_full_screen_selector);
                isFullScreen = false;
                break;
        }
    }

    /**
     * 设置监控电量的广播
     */
    private void setBroadCastReceive() {
        //通过广播的方式设置电池电量的图片,动态注册的方式，因为电量变化、锁屏等功能的广播静态注册之后之后是收不到的。
        myBatteryReceive = new MyBatteryReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBatteryReceive, intentFilter);
    }

    /**
     * 设置播放数据
     */
    private void setData() {
        if (null != mediaList && mediaList.size() > 0){
            MediaItem mediaItem = mediaList.get(position);
            vpControllerVideoName.setText(mediaItem.getDisplayName());
            video_player_vv.setVideoPath(mediaItem.getData());
        }else if(null != uri){
            //播放器响应第三方调用的时
            video_player_vv.setVideoURI(uri);
            video_player_vv.setVideoPath(uri.toString());
        } else {
            Toast.makeText(this, "哎吆！无数据...", Toast.LENGTH_SHORT).show();
        }
        //初始化进来时候设置按钮状态
        setButtonStatue();
    }

    /**
     * 拿到从Activity传递过来的数据
     */
    private void getData() {
        //得到播放地址
        uri = getIntent().getData();
        mediaList = (ArrayList<MediaItem>) getIntent().getSerializableExtra("mediaList");
        position = getIntent().getIntExtra("position",0);
    }

    /**
     * 监听电量变化设置电量图片的广播
     */
    private class MyBatteryReceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //电量的值0-100
            int level = intent.getIntExtra("level",0);
            LogUtil.e(level+"电量值");
            //主线程中所以直接设置UI
            setBatteryPicture(level);
        }

    }

    //根据系统电量设置图片
    private void setBatteryPicture(int level) {
        if(level <= 0){
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_0);
        }else if(level <= 10){
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_10);
        }else if(level <= 20){
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_20);
        }else if(level <= 40){
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_40);
        }else if(level <= 60){
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_60);
        }else if(level <= 80){
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_80);
        }else {
            vpControllerVideoBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    private void setListener() {
        //设置播放准备监听
        video_player_vv.setOnPreparedListener(new MyOnPreparedListener());
        //设置播放完成监听
        video_player_vv.setOnCompletionListener(new MyOnCompletionListener());
        //设置播放出错监听
        video_player_vv.setOnErrorListener(new MyOnErrorListener());
        vpControllerVideoVoice.setOnClickListener(this);
        vpControllerVideoSwitch.setOnClickListener( this );
        vpControllerVideoExit.setOnClickListener( this );
        vpControllerVideoPre.setOnClickListener(this);
        vpControllerVideoPlayPause.setOnClickListener(this);
        vpControllerVideoNext.setOnClickListener(this);
        vpControllerVideoFullScreen.setOnClickListener(this);
        //视频进度条的监听
        vpControllerVideoSeekBar.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());
        //声音进度条的监听
        vpControllerVoiceSeekBar.setOnSeekBarChangeListener(new VoiceOnSeekBarChangeListener());
    }

    private class VoiceOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser){
                if (progress >0){
                    //非静音状态
                    flag = false;
                }else {
                    flag = true;
                }
                updateVoice(progress,flag);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDE_PLAYERCONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_PLAYERCONTROLLER,3000);
        }
    }

    /**
     * 更新音量
     * @param progress
     */
    private void updateVoice(int progress,boolean flag) {
        /**
         * setStreamVolume 最后一个参数是1的时候会调用系统的改变音量的seekBar 如果设置是0则不会调用
         */
        if (flag) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
            vpControllerVoiceSeekBar.setProgress(0);
        } else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            vpControllerVoiceSeekBar.setProgress(progress);
        }
        currentVoice = progress;
    }

    private class VideoOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        /**
         *在seekbar拖动的时候回调方法
         * 如果fromUser 是true则说明seekbar的移动是人为的否则是控件自动移动；当控件自动更新移动时候该方法也会被回调
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
            handler.removeMessages(HIDE_PLAYERCONTROLLER);
        }
        //当结束滑动seekabr时候回调该方法
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_PLAYERCONTROLLER,3000);
        }
    }

    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        /**
         * Called when the media file is ready for playback.
         * @param mp the MediaPlayer that is ready for playback
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            //视频的真实宽高 mp.getVideoWidth(), mp.getVideoHeight()
            //video_player_vv.setVideoSize(mp.getVideoWidth(), mp.getVideoHeight());
            videoWith = mp.getVideoWidth();
            videoHeight = mp.getVideoHeight();
            //准备完毕开始播放
            video_player_vv.start();
            //设置视频总时长
            vpControllerVideoDuration.setText(new Utils().stringForTime(video_player_vv.getDuration()));
            //设置视频的总长度为video_seekBar的多少等分
            vpControllerVideoSeekBar.setMax(video_player_vv.getDuration());
            //默认不显示控制面板
            hidePlayerController();
            //发送消息更新UI
            handler.sendEmptyMessage(PROGRESS);
            //默认设置不全屏播放
            setVideoType(DEFAULT_SCREEN);
        }
    }

    private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{

        /**
         * Called when the end of a media source is reached during playback.
         * @param mp the MediaPlayer that reached the end of the file
         */
        @Override
        public void onCompletion(MediaPlayer mp) {
            //当一个视频播放完毕之后自动播放下一个视频
            playNextVideo();
        }
    }

    private class MyOnErrorListener implements MediaPlayer.OnErrorListener{
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        //在Activity中初始化资源的时候先初始化父类的资源，因为在子类中可能使用父类资源；但是在释放资源的时候先释放子类资源
        //防止父类资源在其它地方被使用出现空指针异常。释放时候子类释放代码写在super关键字之前，初始化的时候反之
        if(null != myBatteryReceive){
            unregisterReceiver(myBatteryReceive);
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //调用gestureDetector的onTouchEvent方法设置的手势回调方法才回被调用，若不传递手势识别器不生效
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            //手指按下
            case MotionEvent.ACTION_DOWN:
                break;
            //手指移动
            case MotionEvent.ACTION_MOVE:
                break;
            //手指抬起
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 隐藏控制面板 显示状态（TRUE）下要隐藏控制面板
     */
    private void hidePlayerController() {
        video_player_controller.setVisibility(View.GONE);
        isShowPlayerController = false;
    }
    /**
     * 显示控制面板 隐藏状态（FALSE）下要显示控制面板
     */
    private void showPlayerController() {
        video_player_controller.setVisibility(View.VISIBLE);
        isShowPlayerController = true;
    }

    /**
     * 监听手机物理按键改变音量
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            currentVoice--;
            updateVoice(currentVoice,false);
            showPlayerController();
            handler.removeMessages(HIDE_PLAYERCONTROLLER);
            handler.sendEmptyMessageDelayed(HIDE_PLAYERCONTROLLER,3000);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            currentVoice++;
            updateVoice(currentVoice,false);
            showPlayerController();
            handler.removeMessages(HIDE_PLAYERCONTROLLER);
            handler.sendEmptyMessageDelayed(HIDE_PLAYERCONTROLLER,3000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
