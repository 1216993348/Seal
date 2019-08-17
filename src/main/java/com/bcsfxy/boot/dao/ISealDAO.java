package com.bcsfxy.boot.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.bcsfxy.util.IDAO;
import com.bcsfxy.vo.Seal;
@Mapper
public interface ISealDAO extends IDAO<Integer, Seal> {
	List<Seal> findAllByCompany(String cid);
	public List<Seal> findSplitAudit(Map<String, Object> map);
	public List<Seal> findSplitBySM(Map<String, Object> map);
	public List<Seal> findSplitBySMDeliver(Map<String, Object> map);
}
