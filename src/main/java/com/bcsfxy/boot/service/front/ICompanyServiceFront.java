package com.bcsfxy.boot.service.front;

import java.util.List;

import com.bcsfxy.vo.Company;
import com.github.pagehelper.PageInfo;

public interface ICompanyServiceFront {
	boolean add(Company vo);
	boolean checkCid(String cid);
	Company get(String cid);
	PageInfo<Company> findAllByCidAndCname(String cid,String cname);
	boolean checkPwd(String cid,String password);
	List<Company> findCompanyIndexList();
}
