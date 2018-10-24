package com.hisense.nettyServer.model.entity;

import java.util.Date;

/**
 * 交通事件视频实体类
 * @author qiush
 *
 */
public class TdTrafficeventvideo {
    private String id;

    private String eventid;

    private String videoname;

    private String videopath;

    private Object videostarttime;

    private Object videoendtime;

    private Date inserttime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid == null ? null : eventid.trim();
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname == null ? null : videoname.trim();
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath == null ? null : videopath.trim();
    }

    public Object getVideostarttime() {
        return videostarttime;
    }

    public void setVideostarttime(Object videostarttime) {
        this.videostarttime = videostarttime;
    }

    public Object getVideoendtime() {
        return videoendtime;
    }

    public void setVideoendtime(Object videoendtime) {
        this.videoendtime = videoendtime;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}