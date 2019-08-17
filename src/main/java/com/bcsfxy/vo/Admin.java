package com.bcsfxy.vo;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable{
	private static final long serialVersionUID = -838760231870580094L;
	private String aid;
	private String password;
	private Integer lev;
	private Integer state;
	private String smname ;
	private String smphone;
	private String smdesc ;
	private Date lastdate;
	private Date createdate;
	private Date updatedate;
	
	public String getSmname() {
		return smname;
	}
	public String getSmphone() {
		return smphone;
	}
	public String getSmdesc() {
		return smdesc;
	}
	public void setSmname(String smname) {
		this.smname = smname;
	}
	public void setSmphone(String smphone) {
		this.smphone = smphone;
	}
	public void setSmdesc(String smdesc) {
		this.smdesc = smdesc;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getLev() {
		return lev;
	}
	public void setLev(Integer lev) {
		this.lev = lev;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
}
