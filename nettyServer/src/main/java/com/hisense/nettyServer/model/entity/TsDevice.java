package com.hisense.nettyServer.model.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备基础信息表
 * @author qiush
 *
 */
public class TsDevice {
    private String deviceid;

    private String devicetype;

    private String devicename;

    private String orgtype;

    private String orgid;

    private String areaid;

    private String roadid;

    private String stakenum;

    private String devicepos;

    private String longitude;

    private String latitude;

    private String direction;

    private String masterip;

    private String interfaceip;

    private String deviceip;

    private String shaftstakenum;

    private String shaftname;

    private String devicecompany;

    private BigDecimal queuelengththreshold;

    private Short stoptimethreshold;

    private Short lowspeedthreshold;

    private Short tminterval;

    private Short deviceloginterval;

    private Short sysloginterval;

    private Short state;

    private String note;

    private String devicemodel;

    private String devicesource;

    private Date operatetime;

    private String operator;

    private String watermarkname;

    private String gisaddress;

    private Short posheight;

    private Short horizontalangle;

    private Short cabrageangle;

    private Short lanenum;

    private String coveragearea;

    private String gateways;

    private String devicemac;

    private Short lightnum;

    private String drivingdirection;

    private String upshaftstake;

    private Short upshaftdis;

    private String upjuncname;

    private Short upjuncdis;

    private String downshaftstake;

    private Short downshaftdis;

    private String downjuncname;

    private Short downjuncdis;

    private String buildstate;

    private String convergebox;

    private Date convergetime;

    private Date devicetranstime;

    private Date deviceremovetime;

    private String constructionunit;

    private String policedevid;

    private String objname;

    private Short ftpport;

    private String ftpuser;

    private String ftppassword;

    private String pospic1;

    private String checkpic;

    private String deviceresolution;

    private Short devicenum;

    private String deviceorientation;

    private String devicearea;

    private String region;

    private String pospic2;

    private String attachment;

    private String designpic;

    private String videopath;

    private String linkid;

    private String sectid;

    private String pospic4;

    private String memo1;

    private String memo2;

    private String memo3;

    private String memo4;

//    private String memo5;

    private String maintenanceunit;

    private Short ramplaneno;

    private Short buslaneno;

    private Short bicyclelaneno;

    private Short otherlaneno;

    private String isall;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename == null ? null : devicename.trim();
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype == null ? null : orgtype.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid == null ? null : areaid.trim();
    }

    public String getRoadid() {
        return roadid;
    }

    public void setRoadid(String roadid) {
        this.roadid = roadid == null ? null : roadid.trim();
    }

    public String getStakenum() {
        return stakenum;
    }

    public void setStakenum(String stakenum) {
        this.stakenum = stakenum == null ? null : stakenum.trim();
    }

    public String getDevicepos() {
        return devicepos;
    }

    public void setDevicepos(String devicepos) {
        this.devicepos = devicepos == null ? null : devicepos.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public String getMasterip() {
        return masterip;
    }

    public void setMasterip(String masterip) {
        this.masterip = masterip == null ? null : masterip.trim();
    }

    public String getInterfaceip() {
        return interfaceip;
    }

    public void setInterfaceip(String interfaceip) {
        this.interfaceip = interfaceip == null ? null : interfaceip.trim();
    }

    public String getDeviceip() {
        return deviceip;
    }

    public void setDeviceip(String deviceip) {
        this.deviceip = deviceip == null ? null : deviceip.trim();
    }

    public String getShaftstakenum() {
        return shaftstakenum;
    }

    public void setShaftstakenum(String shaftstakenum) {
        this.shaftstakenum = shaftstakenum == null ? null : shaftstakenum.trim();
    }

    public String getShaftname() {
        return shaftname;
    }

    public void setShaftname(String shaftname) {
        this.shaftname = shaftname == null ? null : shaftname.trim();
    }

    public String getDevicecompany() {
        return devicecompany;
    }

    public void setDevicecompany(String devicecompany) {
        this.devicecompany = devicecompany == null ? null : devicecompany.trim();
    }

    public BigDecimal getQueuelengththreshold() {
        return queuelengththreshold;
    }

    public void setQueuelengththreshold(BigDecimal queuelengththreshold) {
        this.queuelengththreshold = queuelengththreshold;
    }

    public Short getStoptimethreshold() {
        return stoptimethreshold;
    }

    public void setStoptimethreshold(Short stoptimethreshold) {
        this.stoptimethreshold = stoptimethreshold;
    }

    public Short getLowspeedthreshold() {
        return lowspeedthreshold;
    }

    public void setLowspeedthreshold(Short lowspeedthreshold) {
        this.lowspeedthreshold = lowspeedthreshold;
    }

    public Short getTminterval() {
        return tminterval;
    }

    public void setTminterval(Short tminterval) {
        this.tminterval = tminterval;
    }

    public Short getDeviceloginterval() {
        return deviceloginterval;
    }

    public void setDeviceloginterval(Short deviceloginterval) {
        this.deviceloginterval = deviceloginterval;
    }

    public Short getSysloginterval() {
        return sysloginterval;
    }

    public void setSysloginterval(Short sysloginterval) {
        this.sysloginterval = sysloginterval;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel == null ? null : devicemodel.trim();
    }

    public String getDevicesource() {
        return devicesource;
    }

    public void setDevicesource(String devicesource) {
        this.devicesource = devicesource == null ? null : devicesource.trim();
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getWatermarkname() {
        return watermarkname;
    }

    public void setWatermarkname(String watermarkname) {
        this.watermarkname = watermarkname == null ? null : watermarkname.trim();
    }

    public String getGisaddress() {
        return gisaddress;
    }

    public void setGisaddress(String gisaddress) {
        this.gisaddress = gisaddress == null ? null : gisaddress.trim();
    }

    public Short getPosheight() {
        return posheight;
    }

    public void setPosheight(Short posheight) {
        this.posheight = posheight;
    }

    public Short getHorizontalangle() {
        return horizontalangle;
    }

    public void setHorizontalangle(Short horizontalangle) {
        this.horizontalangle = horizontalangle;
    }

    public Short getCabrageangle() {
        return cabrageangle;
    }

    public void setCabrageangle(Short cabrageangle) {
        this.cabrageangle = cabrageangle;
    }

    public Short getLanenum() {
        return lanenum;
    }

    public void setLanenum(Short lanenum) {
        this.lanenum = lanenum;
    }

    public String getCoveragearea() {
        return coveragearea;
    }

    public void setCoveragearea(String coveragearea) {
        this.coveragearea = coveragearea == null ? null : coveragearea.trim();
    }

    public String getGateways() {
        return gateways;
    }

    public void setGateways(String gateways) {
        this.gateways = gateways == null ? null : gateways.trim();
    }

    public String getDevicemac() {
        return devicemac;
    }

    public void setDevicemac(String devicemac) {
        this.devicemac = devicemac == null ? null : devicemac.trim();
    }

    public Short getLightnum() {
        return lightnum;
    }

    public void setLightnum(Short lightnum) {
        this.lightnum = lightnum;
    }

    public String getDrivingdirection() {
        return drivingdirection;
    }

    public void setDrivingdirection(String drivingdirection) {
        this.drivingdirection = drivingdirection == null ? null : drivingdirection.trim();
    }

    public String getUpshaftstake() {
        return upshaftstake;
    }

    public void setUpshaftstake(String upshaftstake) {
        this.upshaftstake = upshaftstake == null ? null : upshaftstake.trim();
    }

    public Short getUpshaftdis() {
        return upshaftdis;
    }

    public void setUpshaftdis(Short upshaftdis) {
        this.upshaftdis = upshaftdis;
    }

    public String getUpjuncname() {
        return upjuncname;
    }

    public void setUpjuncname(String upjuncname) {
        this.upjuncname = upjuncname == null ? null : upjuncname.trim();
    }

    public Short getUpjuncdis() {
        return upjuncdis;
    }

    public void setUpjuncdis(Short upjuncdis) {
        this.upjuncdis = upjuncdis;
    }

    public String getDownshaftstake() {
        return downshaftstake;
    }

    public void setDownshaftstake(String downshaftstake) {
        this.downshaftstake = downshaftstake == null ? null : downshaftstake.trim();
    }

    public Short getDownshaftdis() {
        return downshaftdis;
    }

    public void setDownshaftdis(Short downshaftdis) {
        this.downshaftdis = downshaftdis;
    }

    public String getDownjuncname() {
        return downjuncname;
    }

    public void setDownjuncname(String downjuncname) {
        this.downjuncname = downjuncname == null ? null : downjuncname.trim();
    }

    public Short getDownjuncdis() {
        return downjuncdis;
    }

    public void setDownjuncdis(Short downjuncdis) {
        this.downjuncdis = downjuncdis;
    }

    public String getBuildstate() {
        return buildstate;
    }

    public void setBuildstate(String buildstate) {
        this.buildstate = buildstate == null ? null : buildstate.trim();
    }

    public String getConvergebox() {
        return convergebox;
    }

    public void setConvergebox(String convergebox) {
        this.convergebox = convergebox == null ? null : convergebox.trim();
    }

    public Date getConvergetime() {
        return convergetime;
    }

    public void setConvergetime(Date convergetime) {
        this.convergetime = convergetime;
    }

    public Date getDevicetranstime() {
        return devicetranstime;
    }

    public void setDevicetranstime(Date devicetranstime) {
        this.devicetranstime = devicetranstime;
    }

    public Date getDeviceremovetime() {
        return deviceremovetime;
    }

    public void setDeviceremovetime(Date deviceremovetime) {
        this.deviceremovetime = deviceremovetime;
    }

    public String getConstructionunit() {
        return constructionunit;
    }

    public void setConstructionunit(String constructionunit) {
        this.constructionunit = constructionunit == null ? null : constructionunit.trim();
    }

    public String getPolicedevid() {
        return policedevid;
    }

    public void setPolicedevid(String policedevid) {
        this.policedevid = policedevid == null ? null : policedevid.trim();
    }

    public String getObjname() {
        return objname;
    }

    public void setObjname(String objname) {
        this.objname = objname == null ? null : objname.trim();
    }

    public Short getFtpport() {
        return ftpport;
    }

    public void setFtpport(Short ftpport) {
        this.ftpport = ftpport;
    }

    public String getFtpuser() {
        return ftpuser;
    }

    public void setFtpuser(String ftpuser) {
        this.ftpuser = ftpuser == null ? null : ftpuser.trim();
    }

    public String getFtppassword() {
        return ftppassword;
    }

    public void setFtppassword(String ftppassword) {
        this.ftppassword = ftppassword == null ? null : ftppassword.trim();
    }

    public String getPospic1() {
        return pospic1;
    }

    public void setPospic1(String pospic1) {
        this.pospic1 = pospic1 == null ? null : pospic1.trim();
    }

    public String getCheckpic() {
        return checkpic;
    }

    public void setCheckpic(String checkpic) {
        this.checkpic = checkpic == null ? null : checkpic.trim();
    }

    public String getDeviceresolution() {
        return deviceresolution;
    }

    public void setDeviceresolution(String deviceresolution) {
        this.deviceresolution = deviceresolution == null ? null : deviceresolution.trim();
    }

    public Short getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(Short devicenum) {
        this.devicenum = devicenum;
    }

    public String getDeviceorientation() {
        return deviceorientation;
    }

    public void setDeviceorientation(String deviceorientation) {
        this.deviceorientation = deviceorientation == null ? null : deviceorientation.trim();
    }

    public String getDevicearea() {
        return devicearea;
    }

    public void setDevicearea(String devicearea) {
        this.devicearea = devicearea == null ? null : devicearea.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getPospic2() {
        return pospic2;
    }

    public void setPospic2(String pospic2) {
        this.pospic2 = pospic2 == null ? null : pospic2.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getDesignpic() {
        return designpic;
    }

    public void setDesignpic(String designpic) {
        this.designpic = designpic == null ? null : designpic.trim();
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath == null ? null : videopath.trim();
    }

    public String getLinkid() {
        return linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid == null ? null : linkid.trim();
    }

    public String getSectid() {
        return sectid;
    }

    public void setSectid(String sectid) {
        this.sectid = sectid == null ? null : sectid.trim();
    }

    public String getPospic4() {
        return pospic4;
    }

    public void setPospic4(String pospic4) {
        this.pospic4 = pospic4 == null ? null : pospic4.trim();
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1 == null ? null : memo1.trim();
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2 == null ? null : memo2.trim();
    }

    public String getMemo3() {
        return memo3;
    }

    public void setMemo3(String memo3) {
        this.memo3 = memo3 == null ? null : memo3.trim();
    }

    public String getMemo4() {
        return memo4;
    }

    public void setMemo4(String memo4) {
        this.memo4 = memo4 == null ? null : memo4.trim();
    }

//    public String getMemo5() {
//        return memo5;
//    }
//
//    public void setMemo5(String memo5) {
//        this.memo5 = memo5 == null ? null : memo5.trim();
//    }

    public String getMaintenanceunit() {
        return maintenanceunit;
    }

    public void setMaintenanceunit(String maintenanceunit) {
        this.maintenanceunit = maintenanceunit == null ? null : maintenanceunit.trim();
    }

    public Short getRamplaneno() {
        return ramplaneno;
    }

    public void setRamplaneno(Short ramplaneno) {
        this.ramplaneno = ramplaneno;
    }

    public Short getBuslaneno() {
        return buslaneno;
    }

    public void setBuslaneno(Short buslaneno) {
        this.buslaneno = buslaneno;
    }

    public Short getBicyclelaneno() {
        return bicyclelaneno;
    }

    public void setBicyclelaneno(Short bicyclelaneno) {
        this.bicyclelaneno = bicyclelaneno;
    }

    public Short getOtherlaneno() {
        return otherlaneno;
    }

    public void setOtherlaneno(Short otherlaneno) {
        this.otherlaneno = otherlaneno;
    }

    public String getIsall() {
        return isall;
    }

    public void setIsall(String isall) {
        this.isall = isall == null ? null : isall.trim();
    }
}