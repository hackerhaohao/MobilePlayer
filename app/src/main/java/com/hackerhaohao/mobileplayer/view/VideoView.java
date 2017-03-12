package com.hackerhaohao.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by ZhangHao on 2017/3/10.
 * 自定义的VideoView视频播放器
 */
public class VideoView extends android.widget.VideoView {

    /**
     * 在代码中实例化时候调用该类
     * @param context
     */
    public VideoView(Context context) {
        this(context,null);
    }

    /**
     * android系统通过反射的方式调用该构造方法实例化，当XML布局文件实例化的时候调用该方法；该方法不能省略
     * Android系统在更加xml布局找这个类，并且实例化的时候，用该构造方法实例化
     * @param context
     * @param attrs
     */
    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 加样式的初始化调用该方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新测量控件的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置视频的大小
     * @param width
     * @param height
     */
    public void setVideoSize(int width, int height){
        //思路：通过getLayoutParams拿到当前控件的参数params，设置外部传入的宽和高然后把设置好的参数传递进去。
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        params.width  = width;
        setLayoutParams(params);
    }
}
