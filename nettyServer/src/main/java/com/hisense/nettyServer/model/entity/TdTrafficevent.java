package com.hisense.nettyServer.model.entity;

import java.util.Date;

/**
 * 交通事件实体类
 * @author qiush
 *
 */
public class TdTrafficevent {
    private String id;

    private String datasource;

    private String deviceid;

    private String devicepos;

    private Short laneno;

    private String eventtype;

    private Object eventtime;

    private Date starttime;

    private Date endtime;

    private Object videostarttime;

    private Object videoendtime;

    private String direction;

    private Short piccount;

    private String picx;

    private String picy;

    private String isvalid;

    private String isabnormal;

    private Date inserttime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getDevicepos() {
        return devicepos;
    }

    public void setDevicepos(String devicepos) {
        this.devicepos = devicepos == null ? null : devicepos.trim();
    }

    public Short getLaneno() {
        return laneno;
    }

    public void setLaneno(Short laneno) {
        this.laneno = laneno;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype == null ? null : eventtype.trim();
    }

    public Object getEventtime() {
        return eventtime;
    }

    public void setEventtime(Object eventtime) {
        this.eventtime = eventtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public Short getPiccount() {
        return piccount;
    }

    public void setPiccount(Short piccount) {
        this.piccount = piccount;
    }

    public String getPicx() {
        return picx;
    }

    public void setPicx(String picx) {
        this.picx = picx == null ? null : picx.trim();
    }

    public String getPicy() {
        return picy;
    }

    public void setPicy(String picy) {
        this.picy = picy == null ? null : picy.trim();
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid == null ? null : isvalid.trim();
    }

    public String getIsabnormal() {
        return isabnormal;
    }

    public void setIsabnormal(String isabnormal) {
        this.isabnormal = isabnormal == null ? null : isabnormal.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}