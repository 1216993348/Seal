package com.bcsfxy.boot.serivce.back.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcsfxy.boot.dao.IAdminDAO;
import com.bcsfxy.boot.service.back.IAdminService;
import com.bcsfxy.util.DataConverterUtil;
import com.bcsfxy.util.PasswordUtil;
import com.bcsfxy.util.SplitPageParam;
import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.util.string.ValidateUtil;
import com.bcsfxy.vo.Admin;
import com.github.pagehelper.PageInfo;
@Service
public class AdminServiceImpl implements IAdminService {
	@Autowired
	private IAdminDAO adminDAO;
	@Override
	@Transactional
	public BaseResponse login(String aid,String password) {
		BaseResponse response = new BaseResponse();
		Admin admin =adminDAO.findById(aid);
		if(admin != null && PasswordUtil.getPassword(password).equals(admin.getPassword())) {
			
			if(admin.getState() == 0) {
				response.setMessage("该账号已被锁定！");
				response.setState(false);
			}else {
				response.setState(true);
				Admin vo = new Admin();
				vo.setAid(admin.getAid());
				vo.setLastdate(new Date());
				adminDAO.doUpdate(vo);//更新最后一次登陆日期
				admin.setPassword(null);
				response.setResult(admin);
			}
			
		}else {
			response.setState(false);
			response.setMessage("用户名或密码错误");
		}
		return response;
	}
	@Override
	public PageInfo<Admin> adminList(String keyWord){
		return new PageInfo<>(adminDAO.findSplit(SplitPageParam.getMap(keyWord)));
	}
	@Override
	public BaseResponse addAdmin(Admin admin) {
		BaseResponse br = new BaseResponse();
		try {
			admin.setLastdate(new Date());
			admin.setCreatedate(new Date());
			admin.setUpdatedate(new Date());
			admin.setLev(0);
			admin.setPassword(PasswordUtil.getPassword(admin.getPassword()));
			br.setState(adminDAO.doCreate(admin));
			br.setMessage("添加成功！");
		}catch (Exception e) {
			br.setMessage("添加失败！");
		}
		return br;
	}
	@Override
	public boolean checkAid(String aid) {
		return adminDAO.findById(aid) == null;
	}
	@Override
	public BaseResponse updateStatus(String aid) {
		BaseResponse br = new BaseResponse();
		
		Admin admin = adminDAO.findById(aid);
		if(admin != null && admin.getLev() == 0) {
			Admin vo =new Admin();
			vo.setAid(admin.getAid());
			if(admin.getState() ==0) {
				vo.setState(1);
			}else {
				vo.setState(0);
			}
			vo.setUpdatedate(new Date());
			br.setResult(adminDAO.doUpdate(vo));
		}else {
			br.setState(false);
		}
		
		return br;
	}
	@Override
	public boolean updatePassword(String aid, String password) {
		Admin vo = new Admin();
		vo.setPassword(PasswordUtil.getPassword(password));
		vo.setAid(aid);
		return adminDAO.doUpdate(vo);
	}
	@Override
	public boolean delete(String []ids) {
		Set<String > set= DataConverterUtil.parseStringSet(ids);
		if(set.size() > 0) {
			return adminDAO.doRemoveBatch(set) == set.size();
		}else {
			return false;
		}
	}
	@Override
	public boolean addSealMaker(Admin vo) {
		vo.setPassword(PasswordUtil.getPassword(vo.getPassword()));
		vo.setLev(2);//设置级别为：印章制作单位级别
		vo.setState(1);//状态设置为有效
		vo.setCreatedate(new Date());//添加日期
		return adminDAO.addSealMaker(vo);
	}
	@Override
	public PageInfo<Admin> findSMSplitPage(String aid,String smname){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("aid",aid);
		map.put("smname", smname);
		return new PageInfo<>(adminDAO.findSMSplitPage(map));
	}
	@Override
	public Admin show(String aid) {
		return adminDAO.findById(aid);
	}
	@Override
	public boolean update(Admin vo) {
		if(ValidateUtil.validateString(vo.getPassword())) {
			vo.setPassword(PasswordUtil.getPassword(vo.getPassword()));
		}
		vo.setUpdatedate(new Date());
		return adminDAO.doUpdate(vo);
	}
}
