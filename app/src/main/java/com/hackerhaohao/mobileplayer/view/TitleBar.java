package com.hackerhaohao.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ZhangHao on 2017/2/22.
 * 自定义控件标题类，设置监听事件
 */
public class TitleBar extends LinearLayout implements View.OnClickListener{

    /**
     * 上下文
     */
    private  Context context;

    private  View textView;

    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    /**
     * 当布局文件实例化之后回调该方法，实例化各个子元素控件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getChildAt(1);
    }

    /**
     * 设置监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
