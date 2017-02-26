package com.hackerhaohao.mobileplayer.po;

/**
 * Created by ZhangHao on 2017/2/26.
 * 多媒体的实体类
 */
public class MediaItem {

    /**
     * 多媒体名称
     */
    private String displayName;

    private Long duration;

    private String size;

    private String data;

    private String title;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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
}
