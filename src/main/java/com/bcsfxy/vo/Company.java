package com.bcsfxy.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Company implements Serializable{
	private static final long serialVersionUID = 5162689708579106375L;
	private String cid;
	private String  cname;
	private String  cdesc;
	private String  province;
	private String  city;
	private Date  cdate;
	private List<Seal> seals;
	private String phone ;
	private String leader ;
	private String password;
	private Integer status;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLeader() {
		return leader;
	}
	public List<Seal> getSeals() {
		return seals;
	}
	public void setSeals(List<Seal> seals) {
		this.seals = seals;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	
}
