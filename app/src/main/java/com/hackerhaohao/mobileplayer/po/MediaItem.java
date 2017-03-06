package com.hackerhaohao.mobileplayer.po;

import java.io.Serializable;

/**
 * Created by ZhangHao on 2017/2/26.
 * 多媒体的实体类
 */
public class MediaItem implements Serializable{

    /**
     * 多媒体名称
     */
    private String displayName;

    /**
     * 多媒体时长 毫秒级
     */
    private long duration;

    /**
     * 多媒体文件大小
     */
    private long size;

    /**
     * 多媒体文件的在SD卡的绝对地址
     */
    private String data;

    /**
     * 显示名称
     */
    private String title;

    /**
     * 艺术家
     */
    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    //Debug时候使用可以看出字段对应的值
    @Override
    public String toString() {
        return "MediaItem{" +
                "displayName='" + displayName + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
