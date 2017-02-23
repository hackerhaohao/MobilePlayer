package com.hackerhaohao.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hackerhaohao.mobileplayer.R;

/**
 * Created by ZhangHao on 2017/2/22.
 * 自定义控件标题类，设置监听事件
 */
public class TitleBar extends LinearLayout implements View.OnClickListener{

    /**
     * 上下文
     */
    private  Context context;

    private  View title_bar_tv_search;

    private  View title_bar_rl;

    private  View title_bar_tv_game;
    private  View title_bar_iv_new_game;

    private  View title_bar_iv_record;

    /**
     * 在代码中实例化该类的时候调用该构造方法
     * @param context
     */
    public TitleBar(Context context) {
        this(context,null);
    }

    /**
     * 当XML布局文件使用该类的时候，android系统通过反射的方式调用该构造方法实例化
     * @param context
     * @param attrs
     */
    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 当使用样式的时候调用该构造方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    /**
     * 当布局XML文件加载完之后回调该方法，实例化各个子元素控件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //实例化子元素
        title_bar_tv_search = getChildAt(1);
        title_bar_rl = getChildAt(2);
        title_bar_iv_record = getChildAt(3);
        //获取相对布局的子元素
        title_bar_tv_game = title_bar_rl.findViewById(R.id.title_bar_tv_game);
        title_bar_iv_new_game = title_bar_rl.findViewById(R.id.title_bar_iv_new_game);
        //设置监听事件
        title_bar_tv_search.setOnClickListener(this);
        title_bar_rl.setOnClickListener(this);
        title_bar_iv_record.setOnClickListener(this);
    }

    /**
     * 设置监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //搜索
            case R.id.title_bar_tv_search:
                Toast.makeText(context,"搜索",Toast.LENGTH_SHORT).show();
            break;
            //游戏
            case R.id.title_bar_rl:
                Toast.makeText(context,"游戏",Toast.LENGTH_SHORT).show();
                if (title_bar_iv_new_game.getVisibility() == View.VISIBLE){
                    title_bar_iv_new_game.setVisibility(View.GONE);
                }else{
                    title_bar_iv_new_game.setVisibility(View.VISIBLE);
                }
            break;
            //播放历史
            case R.id.title_bar_iv_record:
                Toast.makeText(context,"播放历史",Toast.LENGTH_SHORT).show();
            break;
        }
    }
}
