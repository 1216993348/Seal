package com.bcsfxy.util;

import java.util.HashMap;
import java.util.Map;

public class SplitPageParam {
	public static Map<String,Object> getMap(String column,String keyWord){
		Map<String,Object> map = new HashMap<>();
		map.put("column", column);
		if(keyWord == null) {
			keyWord="";
		}
		map.put("keyWord", "%" + keyWord+"%");
		return map;
	}
	public static Map<String,Object> getMap(String keyWord){
		Map<String,Object> map = new HashMap<>();
		if(keyWord == null) {
			keyWord="";
		}
		map.put("keyWord", "%" + keyWord+"%");
		return map;
	}
}
