package com.hisense.nettyServer.model.entity;

import java.util.Date;

/**
 * 交通事件图片实体类
 * @author qiush
 *
 */
public class TdTrafficeventpic {
    private String id;

    private String eventid;

    private Short picno;

    private String picname;

    private String picpath;

    private Object captime;

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

    public Short getPicno() {
        return picno;
    }

    public void setPicno(Short picno) {
        this.picno = picno;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname == null ? null : picname.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public Object getCaptime() {
        return captime;
    }

    public void setCaptime(Object captime) {
        this.captime = captime;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}