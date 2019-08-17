package com.bcsfxy.boot.service.back;

import java.util.Map;

import com.bcsfxy.util.entity.BaseResponse;
import com.bcsfxy.vo.Company;
import com.github.pagehelper.PageInfo;

public interface ICompanyService {
	/**
	 * 添加单位操作
	 * @param company
	 * @return
	 */
	BaseResponse add(Company company);
	/**
	 * 分页查询单位列表
	 * @param province 省份
	 * @param city 城市
	 * @param cid 单位编号
	 * @param cname 单位名称
	 * @return
	 */
	PageInfo<Company> list(String province,String city,String cid,String cname);
	/**
	 * 删除单位操作
	 * @param ids
	 * @return
	 */
	BaseResponse delete(String cid);
	/**
	 * 修改单位
	 * @param company
	 * @return
	 */
	BaseResponse update(Company company);
	
	Map<String,Object> show(String cid);
	
	PageInfo<Company> listInAudit(String province,String city,String cid,String cname);
	BaseResponse audit(String cid,int status) ;
}
