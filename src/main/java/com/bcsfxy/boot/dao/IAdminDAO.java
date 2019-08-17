package com.bcsfxy.boot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bcsfxy.util.IDAO;
import com.bcsfxy.vo.Admin;
@Mapper
public interface IAdminDAO extends IDAO<String, Admin> {
	boolean addSealMaker(Admin vo);
	List<Admin> findSMSplitPage(Map<String,Object> param);
	List<Admin> findAllSM();
	/*public static void main(String[] args)throws Exception {
		Class<?> cla =		Class.forName("com.bcsfxy.vo.Admin");
		Field [] fs =  cla.getDeclaredFields();
		StringBuffer sql = new StringBuffer("SELECT ");
		
		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			System.out.println();
			sql.append(field.getName()).append(",");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(" FROM ").append(DataConverterUtil.initLowerCase(cla.getSimpleName()));
		System.out.println(sql);
	}*/
}
