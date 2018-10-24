package com.hisense.nettyServer.model.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一般违法实体类
 * @author qiush
 *
 */
public class TdIllegalevent {
    private String id;

    private String deviceid;

    private String devicepos;

    private Short laneno;

    private String eventtype;

    private Object eventtime;

    private String plateno;

    private String platetype;

    private String platecolor;

    private String vehicletype;

    private String vehiclelogo;

    private BigDecimal confidence;

    private String direction;

    private Short speed;

    private Date rlontime;

    private Date rlofftime;

    private Short piccount;

    private String isvalid;

    private Date inserttime;

    private String illegalid;

    private String policedevid;

    private String vehiclecolor;

    private String vehicleclass;

    private Short limitspeed;

    private Short maxlimitspeed;

    private String vehnewenerg;

    private String bigplateno;

    private String isAudit;

    private String datasource;

    private String classification;

    private String picurl1;

    private String picname1;

    private Date pictime1;

    private String picurl2;

    private String picname2;

    private Date pictime2;

    private String picurl3;

    private String picname3;

    private Date pictime3;

    private String picurl4;

    private String picname4;

    private Date pictime4;

    private String videourl;

    private String audiourl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno == null ? null : plateno.trim();
    }

    public String getPlatetype() {
        return platetype;
    }

    public void setPlatetype(String platetype) {
        this.platetype = platetype == null ? null : platetype.trim();
    }

    public String getPlatecolor() {
        return platecolor;
    }

    public void setPlatecolor(String platecolor) {
        this.platecolor = platecolor == null ? null : platecolor.trim();
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype == null ? null : vehicletype.trim();
    }

    public String getVehiclelogo() {
        return vehiclelogo;
    }

    public void setVehiclelogo(String vehiclelogo) {
        this.vehiclelogo = vehiclelogo == null ? null : vehiclelogo.trim();
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public void setConfidence(BigDecimal confidence) {
        this.confidence = confidence;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public Short getSpeed() {
        return speed;
    }

    public void setSpeed(Short speed) {
        this.speed = speed;
    }

    public Date getRlontime() {
        return rlontime;
    }

    public void setRlontime(Date rlontime) {
        this.rlontime = rlontime;
    }

    public Date getRlofftime() {
        return rlofftime;
    }

    public void setRlofftime(Date rlofftime) {
        this.rlofftime = rlofftime;
    }

    public Short getPiccount() {
        return piccount;
    }

    public void setPiccount(Short piccount) {
        this.piccount = piccount;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid == null ? null : isvalid.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getIllegalid() {
        return illegalid;
    }

    public void setIllegalid(String illegalid) {
        this.illegalid = illegalid == null ? null : illegalid.trim();
    }

    public String getPolicedevid() {
        return policedevid;
    }

    public void setPolicedevid(String policedevid) {
        this.policedevid = policedevid == null ? null : policedevid.trim();
    }

    public String getVehiclecolor() {
        return vehiclecolor;
    }

    public void setVehiclecolor(String vehiclecolor) {
        this.vehiclecolor = vehiclecolor == null ? null : vehiclecolor.trim();
    }

    public String getVehicleclass() {
        return vehicleclass;
    }

    public void setVehicleclass(String vehicleclass) {
        this.vehicleclass = vehicleclass == null ? null : vehicleclass.trim();
    }

    public Short getLimitspeed() {
        return limitspeed;
    }

    public void setLimitspeed(Short limitspeed) {
        this.limitspeed = limitspeed;
    }

    public Short getMaxlimitspeed() {
        return maxlimitspeed;
    }

    public void setMaxlimitspeed(Short maxlimitspeed) {
        this.maxlimitspeed = maxlimitspeed;
    }

    public String getVehnewenerg() {
        return vehnewenerg;
    }

    public void setVehnewenerg(String vehnewenerg) {
        this.vehnewenerg = vehnewenerg == null ? null : vehnewenerg.trim();
    }

    public String getBigplateno() {
        return bigplateno;
    }

    public void setBigplateno(String bigplateno) {
        this.bigplateno = bigplateno == null ? null : bigplateno.trim();
    }

    public String getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit == null ? null : isAudit.trim();
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
    }

    public String getPicurl1() {
        return picurl1;
    }

    public void setPicurl1(String picurl1) {
        this.picurl1 = picurl1 == null ? null : picurl1.trim();
    }

    public String getPicname1() {
        return picname1;
    }

    public void setPicname1(String picname1) {
        this.picname1 = picname1 == null ? null : picname1.trim();
    }

    public Date getPictime1() {
        return pictime1;
    }

    public void setPictime1(Date pictime1) {
        this.pictime1 = pictime1;
    }

    public String getPicurl2() {
        return picurl2;
    }

    public void setPicurl2(String picurl2) {
        this.picurl2 = picurl2 == null ? null : picurl2.trim();
    }

    public String getPicname2() {
        return picname2;
    }

    public void setPicname2(String picname2) {
        this.picname2 = picname2 == null ? null : picname2.trim();
    }

    public Date getPictime2() {
        return pictime2;
    }

    public void setPictime2(Date pictime2) {
    	this.pictime2 = pictime2;
    }

    public String getPicurl3() {
        return picurl3;
    }

    public void setPicurl3(String picurl3) {
        this.picurl3 = picurl3 == null ? null : picurl3.trim();
    }

    public String getPicname3() {
        return picname3;
    }

    public void setPicname3(String picname3) {
        this.picname3 = picname3 == null ? null : picname3.trim();
    }

    public Date getPictime3() {
        return pictime3;
    }

    public void setPictime3(Date pictime3) {
        this.pictime3 = pictime3;
    }

    public String getPicurl4() {
        return picurl4;
    }

    public void setPicurl4(String picurl4) {
        this.picurl4 = picurl4 == null ? null : picurl4.trim();
    }

    public String getPicname4() {
        return picname4;
    }

    public void setPicname4(String picname4) {
        this.picname4 = picname4 == null ? null : picname4.trim();
    }

    public Date getPictime4() {
        return pictime4;
    }

    public void setPictime4(Date pictime4) {
        this.pictime4 = pictime4;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl == null ? null : videourl.trim();
    }

    public String getAudiourl() {
        return audiourl;
    }

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl == null ? null : audiourl.trim();
    }
}