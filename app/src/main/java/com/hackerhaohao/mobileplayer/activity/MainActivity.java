package com.hackerhaohao.mobileplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.hackerhaohao.mobileplayer.R;
import com.hackerhaohao.mobileplayer.base.BasePager;
import com.hackerhaohao.mobileplayer.pager.AudioPager;
import com.hackerhaohao.mobileplayer.pager.NetAudioPager;
import com.hackerhaohao.mobileplayer.pager.NetVideoPager;
import com.hackerhaohao.mobileplayer.pager.VideoPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHao on 2017/2/20.
 */
public class MainActivity extends FragmentActivity {

    private FrameLayout fl_main_content;

    private RadioGroup rg_bottom_tag;

    private List<BasePager> bList;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);
        fl_main_content =(FrameLayout) findViewById(R.id.fl_main_content);

        //初始化页面数据
        initDate();
        //设置监听RadioGroup
        rg_bottom_tag.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选中项
        rg_bottom_tag.check(R.id.rb_video);
    }

    /**
     * 初始化UI页面
     */
    private void initDate() {
        bList = new ArrayList<>();
        bList.add(new VideoPager(this));
        bList.add(new AudioPager(this));
        bList.add(new NetVideoPager(this));
        bList.add(new NetAudioPager(this));
    }

    class  MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        /**
         * <p>Called when the checked radio button has changed. When the
         * selection is cleared, checkedId is -1.</p>
         *
         * @param group     the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio button
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            //根据ID判断哪个radioButton被点击了,设置点击位置,根据位置加载对应的视图
            switch (checkedId){
                case R.id.rb_video:
                    position = 0;
                    break;
                case R.id.rb_audio:
                    position = 1;
                    break;
                case R.id.rb_netvideo:
                    position = 2;
                    break;
                case R.id.rb_netaudio:
                    position = 3;
                    break;
            }
            //设置fragment:根据位置positon值把对应的页面添加到Fragment中
            setFragment();
        }
    }

    private void setFragment() {
        //1、得到FragmentManager,因为使用getSupportFragment方法所以MainActivity要继承FragmentActivity
        FragmentManager fm = getSupportFragmentManager();
        //2、开启Fragment的事务
        FragmentTransaction ft = fm.beginTransaction();
        //3、替换Fragment
        ft.replace(R.id.fl_main_content,new Fragment(){
            //设置需要被替换的新UI页面
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                BasePager basePager = getBasePager();
                if(null != basePager){
                    //给Fragment返回UI页面
                    return  basePager.getRootView();
                }
                return null;
            }
        });
        //4、提交事务
        ft.commit();
    }

    private BasePager getBasePager() {
        BasePager b = bList.get(position);
        if(null != b && !b.isInit()){
            //联网请求数据，绑定数据源等等
            b.initData();
            b.setIsInit(true);
        }
        return b;
    }
}
