package com.bcsfxy.boot.service.back;

import java.util.List;
import java.util.Map;

import com.bcsfxy.vo.Seal;
import com.github.pagehelper.PageInfo;

public interface ISealService {
	boolean add(Seal seal);
	boolean createFile(String mainTitle,String viceTitle,String centerTitle,String topTitle
			,String filePath,int version);
	
	PageInfo<Seal> list(String cid,String cname,String sid,String sname);
	
	boolean delete(int sid);
	boolean update(Seal seal);
	
	Seal show(int sid);
	
	PageInfo<Seal> listAudit(String cid,String cname,String sid,String sname);
	
	boolean audit(Seal seal);
	
	List<Seal> findAllByCompany(String cid,String password);
	
	Map<String,Object> reauditSealPre(int sid);
	
	boolean applyCancell(int sid);
	
	PageInfo<Seal> findSplitBySM(String cid,String cname,String sid,String sname,String smid);
	PageInfo<Seal> findSplitBySMDeliver(String cid,String cname,String sid,String sname,String smid);
	boolean submitSeal(int sid);
}
