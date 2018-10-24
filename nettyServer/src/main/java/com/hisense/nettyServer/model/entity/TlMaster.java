package com.hisense.nettyServer.model.entity;

import java.util.Date;

/**
 * 操作日志实体�?
 * @author qiush
 *
 */
public class TlMaster {
    private String id;

	private String deviceid;

	private Object creationtime;

	private Date eventtime;

	private String eventdesc;

	private String note;

	private Date inserttime;

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

	public Object getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Object creationtime) {
		this.creationtime = creationtime;
	}

	public Date getEventtime() {
		return eventtime;
	}

	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}

	public String getEventdesc() {
		return eventdesc;
	}

	public void setEventdesc(String eventdesc) {
		this.eventdesc = eventdesc == null ? null : eventdesc.trim();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	public Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
}