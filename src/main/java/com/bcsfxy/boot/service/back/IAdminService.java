package com.bcsfxy.boot.service.back;

import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Admin;
import com.github.pagehelper.PageInfo;

public interface IAdminService {
	/**
	 * 根据用户名、密码验证用户是否合法
	 * @param mid
	 * @param password
	 * @return
	 */
	BaseResponse login(String mid,String password);
	
	PageInfo<Admin> adminList(String keyWord);
	
	boolean checkAid(String aid);
	BaseResponse addAdmin(Admin admin);
	
	BaseResponse updateStatus(String aid);
	
	boolean updatePassword(String aid,String password);
	
	boolean delete(String ids[]);
	
	boolean addSealMaker(Admin vo);
	
	PageInfo<Admin> findSMSplitPage(String aid,String smname);
	
	Admin show(String aid);
	
	boolean update(Admin vo);
}
