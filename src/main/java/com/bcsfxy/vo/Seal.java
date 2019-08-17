package com.bcsfxy.vo;

import java.io.Serializable;
import java.util.Date;

public class Seal implements Serializable{
	private static final long serialVersionUID = -6002510403901980862L;
	private Integer sid;
	private String cid;
	private String cname;
	private String aid;
	private String sname;
	private String photo;
	private Date sdate;
	private Date smdate;
	private String smid;
	private Admin smdesc;
	public void setSmdesc(Admin smdesc) {
		this.smdesc = smdesc;
	}
	public Admin getSmdesc() {
		return smdesc;
	}
	public String getSmid() {
		return smid;
	}


	public void setSmid(String smid) {
		this.smid = smid;
	}


	public Date getSmdate() {
		return smdate;
	}
	

	public void setSmdate(Date smdate) {
		this.smdate = smdate;
	}
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public String getCname() {
		return cname;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	
}
