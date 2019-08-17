package com.bcsfxy.boot.serivce.back.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bcsfxy.boot.dao.IAdminDAO;
import com.bcsfxy.boot.dao.ICompanyDAO;
import com.bcsfxy.boot.dao.ISealDAO;
import com.bcsfxy.boot.service.back.ICompanyService;
import com.bcsfxy.util.PasswordUtil;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.util.string.ValidateUtil;
import com.bcsfxy.vo.Company;
import com.bcsfxy.vo.enu.CompanyStatus;
import com.github.pagehelper.PageInfo;
@Service
public class CompanyServiceImpl implements ICompanyService {
	@Resource
	private ICompanyDAO companyDAO;
	@Resource
	private ISealDAO sealDAO;
	
	@Resource
	private IAdminDAO adminDAO;
	@Override
	public BaseResponse add(Company company) {
		BaseResponse rb = new BaseResponse();
		company.setCdate(new Date());
		company.setPassword(PasswordUtil.getPassword(company.getPassword()));
		company.setStatus(CompanyStatus.AUDIT_SUCCESS.getStatus());//管理雇员添加的企业信息状态默认为通过审核
		Company vo = companyDAO.findById(company.getCid());
		boolean flag =false;
		if(vo != null ) {//如果已经存在该单位信息
			if(vo.getStatus() == CompanyStatus.AUDIT_FAILED.getStatus()) {//如果该单位信息审核未通过,则允许重新审核
				flag =companyDAO.doUpdate(company);
			}else {
				flag = false;
			}
		}else {//如果不存在则添加
			flag =companyDAO.doCreate(company);
		}
		if(flag) {
			rb.setMessage("添加成功！");
			rb.setState(true);
		}else {
			rb.setMessage("添加失败！");
			rb.setState(false);
		}
		return rb;
	}

	@Override
	public PageInfo<Company> list(String province, String city, String cid, String cname) {
		Map<String, Object> map = new HashMap<>();
		if(ValidateUtil.validateString(province)) {
			map.put("province","%"+ province+"%");
		}
		if(ValidateUtil.validateString(city)) {
			map.put("city", "%"+city+"%");
		}
		if(ValidateUtil.validateString(cid)) {
			map.put("cid", "%"+cid+"%");
		}
		if(ValidateUtil.validateString(cname)) {
			map.put("cname", "%"+cname+"%");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("map", map);
		return new PageInfo<>(companyDAO.findSplit(param));
	}

	@Override
	public BaseResponse delete(String cid) {
		BaseResponse rb = new BaseResponse();
		if(companyDAO.doRemoveById(cid)) {
			rb.setMessage("删除成功！");
			rb.setState(true);
			// TODO 删除图片
		}else {
			rb.setMessage("删除失败！");
			rb.setState(false);
		}
		return rb;
	}

	@Override
	public BaseResponse update(Company company) {
		BaseResponse rb = new BaseResponse();
		if(ValidateUtil.validateString(company.getPassword())) {
			company.setPassword(PasswordUtil.getPassword(company.getPassword()));
		}
		rb.setState(companyDAO.doUpdate(company));
		if(rb.getState()) {
			rb.setMessage("修改成功！");
		}else {
			rb.setMessage("修改失败！");
		}
		return rb;
	}

	@Override
	public Map<String,Object> show(String cid) {
		Map<String,Object>  map =new HashMap<>();
		map.put("company",companyDAO.findById(cid)); 
		if(map.get("company") != null) {
			map.put("seals",sealDAO.findAllByCompany(cid)); 
			map.put("allSMs",adminDAO.findAllSM()); 
		}
		return map;
	}

	@Override
	public PageInfo<Company> listInAudit(String province, String city, String cid, String cname) {
		Map<String, Object> map = new HashMap<>();
		if(ValidateUtil.validateString(province)) {
			map.put("province","%"+ province+"%");
		}
		if(ValidateUtil.validateString(city)) {
			map.put("city", "%"+city+"%");
		}
		if(ValidateUtil.validateString(cid)) {
			map.put("cid", "%"+cid+"%");
		}
		if(ValidateUtil.validateString(cname)) {
			map.put("cname", "%"+cname+"%");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("map", map);
		return new PageInfo<>(companyDAO.findSplitInAudit(param));
	}

	@Override
	public BaseResponse audit(String cid,int status) {
		BaseResponse rb = new BaseResponse();
		Company company =new Company();
		company.setStatus(status);
		company.setCid(cid);
		if(companyDAO.doUpdate(company)) {
			rb.setMessage("修改成功！");
		}else {
			rb.setMessage("修改失败！");
		}
		return rb;
	}
}
