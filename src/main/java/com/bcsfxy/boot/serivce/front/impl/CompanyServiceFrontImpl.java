package com.bcsfxy.boot.serivce.front.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcsfxy.boot.dao.ICompanyDAO;
import com.bcsfxy.boot.service.front.ICompanyServiceFront;
import com.bcsfxy.util.PasswordUtil;
import com.bcsfxy.vo.Company;
import com.bcsfxy.vo.enu.CompanyStatus;
import com.github.pagehelper.PageInfo;

@Service
public class CompanyServiceFrontImpl implements ICompanyServiceFront{
	@Autowired
	private ICompanyDAO companyDAO;

	@Override
	public boolean add(Company company) {
		company.setCdate(new Date());
		company.setPassword(PasswordUtil.getPassword(company.getPassword()));
		Company vo = companyDAO.findById(company.getCid());
		boolean flag =false;
		if(vo != null ) {//如果已经存在该单位信息
			if(vo.getStatus() == CompanyStatus.AUDIT_FAILED.getStatus()) {//如果该单位信息审核未通过,则允许重新审核
				company.setStatus(CompanyStatus.INAUDIT.getStatus());
				flag =companyDAO.doUpdate(company);
			}else {
				flag = false;
			}
		}else {//如果不存在则添加
			company.setStatus(CompanyStatus.INAUDIT.getStatus());
			flag =companyDAO.doCreate(company);
		}
		return flag;
	}

	@Override
	public boolean checkCid(String cid) {
		Company company=companyDAO.findById(cid);
		return company==null || company.getStatus()==CompanyStatus.CANCELLED.getStatus()
				||company.getStatus()==CompanyStatus.AUDIT_FAILED.getStatus();
	}

	@Override
	public Company get(String cid) {
		return companyDAO.findById(cid);
	}
	@Override
	public PageInfo<Company> findAllByCidAndCname(String cid, String cname) {
		Map<String,Object> param = new HashMap<>();
		param.put("cid", "%"+cid+"%");
		param.put("cname", "%"+cname+"%");
		return new PageInfo<>(companyDAO.findAllByCidAndCname(param));
	}
	@Override
	public boolean checkPwd(String cid, String password) {
		Company company = companyDAO.findById(cid);
		System.out.println("password："+password);
		System.out.println("PasswordUtil.getPassword(password):"+PasswordUtil.getPassword(password));
		System.out.println("company.getPassword():"+company.getPassword());
		System.out.println("flag："+ PasswordUtil.getPassword(password).equals(company.getPassword()));
		return PasswordUtil.getPassword(password).equals(company.getPassword());
	}

	@Override
	public List<Company> findCompanyIndexList() {
		return companyDAO.findAll();
	}
}
