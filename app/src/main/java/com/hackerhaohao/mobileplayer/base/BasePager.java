package com.hackerhaohao.mobileplayer.base;

import android.content.Context;
import android.view.View;

/**
 * Created by ZhangHao on 2017/2/21.
 * 基类供子页面调用
 * 实现页面初始化的数据分离，保证创建页面的多样性
 */
public abstract class BasePager {

    /**
     *上下文
     */
    public final Context context;

    /**
     * 子页面实例
     */
    private View rootView;

    /**
     * 控制initData方法指挥被调用一次
     */
    private boolean isInit;

    public void setIsInit(boolean isInit) {
        this.isInit = isInit;
    }

    public boolean isInit() {
        return isInit;
    }

    private void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public View getRootView() {
        return rootView;
    }

    public BasePager(Context context){
        this.context = context;
        setRootView(intiView());
    }

    /**
     * 子类继承BasePager就必须实现自己initView方法，写自己的veiw,从而实现不同的效果
     * @return
     */
    public abstract View intiView() ;

    /**
     * 子页面初始化数据时候重写该方法
     * 联网请求，绑定数据
     */
    public void initData(){}
}
