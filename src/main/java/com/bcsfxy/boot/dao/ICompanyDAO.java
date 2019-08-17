package com.bcsfxy.boot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bcsfxy.util.IDAO;
import com.bcsfxy.vo.Company;
@Mapper
public interface ICompanyDAO extends IDAO<String, Company>{
	public List<Company> findSplitInAudit(Map<String, Object> map);
	public List<Company> findAllByCidAndCname(Map<String,Object> param);
}
