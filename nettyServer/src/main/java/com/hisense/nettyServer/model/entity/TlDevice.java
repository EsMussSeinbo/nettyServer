package com.hisense.nettyServer.model.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备日志实体类
 * @author qiush
 *
 */
public class TlDevice {
    private String fileName;

    private String deviceid;

    private String epdeviceid;

    private Date recordTime;

    private String ipAddress;

    private BigDecimal cpuTemp;

    private BigDecimal cpuFanSpeed;

    private String freeMemory;

    private Long threadCount;

    private String diskSpace;

    private Short nonuploadnum;

    private String camid;

    private String cameraState;

    private String cameraStateDes;

    private String videoid;

    private String videoState;

    private String videoStateDes;

    private String triggerid;

    private String triggerState;

    private String triggerStateDes;

    private String lightid;

    private String lightState;

    private String lightStateDes;

    private String isNormal;

    private Long fileSize;

    private Date fileCreatetime;

    private Date fileAnalyseTime;

    private String serverip;

    private String note;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getEpdeviceid() {
        return epdeviceid;
    }

    public void setEpdeviceid(String epdeviceid) {
        this.epdeviceid = epdeviceid == null ? null : epdeviceid.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public BigDecimal getCpuTemp() {
        return cpuTemp;
    }

    public void setCpuTemp(BigDecimal cpuTemp) {
        this.cpuTemp = cpuTemp;
    }

    public BigDecimal getCpuFanSpeed() {
        return cpuFanSpeed;
    }

    public void setCpuFanSpeed(BigDecimal cpuFanSpeed) {
        this.cpuFanSpeed = cpuFanSpeed;
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory == null ? null : freeMemory.trim();
    }

    public Long getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Long threadCount) {
        this.threadCount = threadCount;
    }

    public String getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(String diskSpace) {
        this.diskSpace = diskSpace == null ? null : diskSpace.trim();
    }

    public Short getNonuploadnum() {
        return nonuploadnum;
    }

    public void setNonuploadnum(Short nonuploadnum) {
        this.nonuploadnum = nonuploadnum;
    }

    public String getCamid() {
        return camid;
    }

    public void setCamid(String camid) {
        this.camid = camid == null ? null : camid.trim();
    }

    public String getCameraState() {
        return cameraState;
    }

    public void setCameraState(String cameraState) {
        this.cameraState = cameraState == null ? null : cameraState.trim();
    }

    public String getCameraStateDes() {
        return cameraStateDes;
    }

    public void setCameraStateDes(String cameraStateDes) {
        this.cameraStateDes = cameraStateDes == null ? null : cameraStateDes.trim();
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid == null ? null : videoid.trim();
    }

    public String getVideoState() {
        return videoState;
    }

    public void setVideoState(String videoState) {
        this.videoState = videoState == null ? null : videoState.trim();
    }

    public String getVideoStateDes() {
        return videoStateDes;
    }

    public void setVideoStateDes(String videoStateDes) {
        this.videoStateDes = videoStateDes == null ? null : videoStateDes.trim();
    }

    public String getTriggerid() {
        return triggerid;
    }

    public void setTriggerid(String triggerid) {
        this.triggerid = triggerid == null ? null : triggerid.trim();
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState == null ? null : triggerState.trim();
    }

    public String getTriggerStateDes() {
        return triggerStateDes;
    }

    public void setTriggerStateDes(String triggerStateDes) {
        this.triggerStateDes = triggerStateDes == null ? null : triggerStateDes.trim();
    }

    public String getLightid() {
        return lightid;
    }

    public void setLightid(String lightid) {
        this.lightid = lightid == null ? null : lightid.trim();
    }

    public String getLightState() {
        return lightState;
    }

    public void setLightState(String lightState) {
        this.lightState = lightState == null ? null : lightState.trim();
    }

    public String getLightStateDes() {
        return lightStateDes;
    }

    public void setLightStateDes(String lightStateDes) {
        this.lightStateDes = lightStateDes == null ? null : lightStateDes.trim();
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal == null ? null : isNormal.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getFileCreatetime() {
        return fileCreatetime;
    }

    public void setFileCreatetime(Date fileCreatetime) {
        this.fileCreatetime = fileCreatetime;
    }

    public Date getFileAnalyseTime() {
        return fileAnalyseTime;
    }

    public void setFileAnalyseTime(Date fileAnalyseTime) {
        this.fileAnalyseTime = fileAnalyseTime;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip == null ? null : serverip.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}