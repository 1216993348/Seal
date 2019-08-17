package com.bcsfxy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bcsfxy.util.string.ValidateUtil;

/*import net.sf.json.JSONArray;
import net.sf.json.JSONObject;*/

/**
 * 提供有数据转换的方法
 * 
 * @author xhy
 *
 */
public class DataConverterUtil{
	/**
	 * 将字符串拆分为Set<String>集合
	 * 
	 * @param param
	 * @param regex
	 * @return
	 */
	public static Set<String> splitStringToStringSet(String param, String regex) {
		Set<String> all = new HashSet<String>();
		if (!ValidateUtil.validateString(param)) {
			return all;
		}
		// 根据正则表达式进行数据拆分,并将结果保存到Set集合内
		String data[] = param.split(regex);
		for (int x = 0; x < data.length; x++) {
			all.add(data[x]);
		}
		return all;
	}

	/**
	 * 将字符串拆分为Set<Integer>集合
	 * 
	 * @param param
	 * @param regex
	 * @return
	 */
	public static Set<Integer> splitStringToIntegerSet(String param, String regex) {
		Set<Integer> all = null;
		// 如果参数为空返回null
		if (param == null || "".equals(param)) {
			return null;
		}
		all = new HashSet<Integer>();
		// 根据正则表达式进行数据拆分,并将结果保存到Set集合内
		String data[] = param.split(regex);
		for (int x = 0; x < data.length; x++) {
			if (data[x].matches("\\d+")) {
				all.add(Integer.parseInt(data[x]));
			} else {
				// 如果拆分完的参数不是整数，直接返回null
				return null;
			}
		}
		// 全部拆分成功返回Set集合
		return all;
	}

	/**
	 * 将字符串拆分为List<String>集合
	 * 
	 * @param param
	 * @param regex
	 * @return
	 */
	public static List<String> splitStringToStringList(String param, String regex) {
		List<String> all = new ArrayList<String>();
		if (!ValidateUtil.validateString(param)) {
			return all;
		}
		// 根据正则表达式进行数据拆分,并将结果保存到Set集合内
		String data[] = param.split(regex);
		for (int x = 0; x < data.length; x++) {
			all.add(data[x]);
		}
		return all;
	}

	/**
	 * 将字符串拆分为List<Integer>集合
	 * 
	 * @param param
	 * @param regex
	 * @return
	 */
	public static List<Integer> splitStringToIntegerList(String param, String regex) {
		// 如果参数为空返回null
		if (param == null || "".equals(param)) {
			return null;
		}
		List<Integer> all = new ArrayList<Integer>();
		// 根据正则表达式进行数据拆分,并将结果保存到Set集合内
		String data[] = param.split(regex);
		for (int x = 0; x < data.length; x++) {
			if (data[x].matches("\\d+")) {
				all.add(Integer.parseInt(data[x]));
			} else {
				// 如果拆分完的参数不是整数，直接返回null
				return null;
			}
		}
		// 全部拆分成功返回Set集合
		return all;
	}

	/**
	 * 将字符串数组转换为Set<Integer>集合
	 * 
	 * @param str
	 * @return
	 */
	public static Set<Integer> parseIntSet(String str[]) {
		Set<Integer> set = null;
		if (str.length > 0) {
			set = new HashSet<Integer>();
			for (int x = 0; x < str.length; x++) {
				if (ValidateUtil.validateRegex(str[x], "\\d+")) {
					set.add(Integer.parseInt(str[x]));
				} else {
					return null;
				}
			}
		}
		return set;
	}

	/**
	 * 将字符串数组转换为Set<String>集合
	 * 
	 * @param str
	 * @return
	 */
	public static Set<String> parseStringSet(String str[]) {
		Set<String> set = null;
		if (str != null && str.length > 0) {
			set = new HashSet<String>();
			for (int x = 0; x < str.length; x++) {
				set.add(str[x]);
			}
		}
		return set;
	}
	/**
	 * 将字符串数组转换为Set<String>集合
	 * 
	 * @param str
	 * @return
	 */
	public static Set<Integer> parseIntegerSet(int[] rid) {
		Set<Integer> set = null;
		if (rid.length > 0) {
			set = new HashSet<Integer>();
			for (int x = 0; x < rid.length; x++) {
				set.add(rid[x]);
			}
		}
		return set;
	}

	/**
	 * 将集合转换为JSONArray
	 * 
	 * @param all
	 * @return
	 */
	/**public static JSONArray convertorListToJSON(Collection<?> all) {
		JSONArray array = new JSONArray();
		Iterator<?> iter = all.iterator();
		while (iter.hasNext()) {
			array.add(convertorObjectToJSON(iter.next()));
		}
		return array;
	}

	/**
	 * 将Map集合转换为JSON对象
	 * 
	 * @param map
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	*public static JSONObject convertorMapToJSON(Map<?, ?> map) {
		JSONObject json = new JSONObject();
		Set<?> entrys = map.entrySet();
		Iterator<Map.Entry<?, ?>> iter = (Iterator<Entry<?, ?>>) entrys.iterator();

		while (iter.hasNext()) {
			Map.Entry<?, ?> entry = iter.next();
			System.out.println(entry.getValue() + "   ***************   " + entry.getValue());
			if (entry.getValue() instanceof Integer || entry.getValue() instanceof Float
					|| entry.getValue() instanceof Double || entry.getValue() instanceof String) {
				json.put(entry.getKey(), entry.getValue());
			} else if (entry.getValue() instanceof List || entry.getValue() instanceof Set) {
				json.put(entry.getKey(), convertorListToJSON((Collection<?>) entry.getValue()));
			} else if (entry.getValue() instanceof Map) {
				json.put(entry.getKey(), convertorMapToJSON((Map<?, ?>) entry.getValue()));
			} else {
				json.put(entry.getKey(), convertorObjectToJSON(entry.getValue()));
			}
		}
		return json;
	}

	/**
	 * 将VO对象转换为JSON对象
	 * 
	 * @param vo
	 * @return
	 */
	/**public static JSONObject convertorObjectToJSON(Object vo) {
		// System.out.println("长度 ："+ jsons.length+ " , "+jsons);
		if (vo == null) {
			return null;
		}
		// 之所以要使用数组表示，是因为可以通过使用可变参数，以达到最好的使用效果，否则还得重载方法
		JSONObject json = new JSONObject();
		Class<?> cla = vo.getClass();
		Field fields[] = vo.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String methodName = "get" + DataConverterUtil.initUpperCase(fields[i].getName());
			if (!methodName.equals("getSerialVersionUID")) {
				try {
					Method method = cla.getMethod(methodName);
					// System.out.println(fields[i].getType().getSimpleName());
					// System.out.println("方法名称 ：" + methodName +fields[i].getType().getSimpleName()
					// + " : " +method.invoke(vo) );
					if (method.invoke(vo) != null) {// 如果成员不为null
						if (fields[i].getType().getSimpleName().equals("Integer")
								|| fields[i].getType().getSimpleName().equals("Double")
								|| fields[i].getType().getSimpleName().equals("Float")
								|| fields[i].getType().getSimpleName().equals("String")) {
							if (json.get(fields[i].getName()) == null) {
								json.put(fields[i].getName(), method.invoke(vo));
							}
						} else if (fields[i].getType().getSimpleName().equals("Date")) {
							Date date = (Date) method.invoke(vo);
							json.put(fields[i].getName(), date.getTime());
						} else if (fields[i].getType().getSimpleName().equals("List")
								|| fields[i].getType().getSimpleName().equals("Set")) {
							json.put(fields[i].getName(), convertorListToJSON((Collection<?>) method.invoke(vo)));
						} else if (fields[i].getType().getSimpleName().equals("Map")) {
							json.put(fields[i].getName(), convertorMapToJSON((Map<?, ?>) method.invoke(vo)));
						} else {
							json.put(fields[i].getName(), convertorObjectToJSON(method.invoke(vo)));
						}
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * 将字符串首字符大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initUpperCase(String str) {
		if (str == null || "".equals(str)) { // 保证操作的数据有内容不为空
			return str;
		}
		char ch[] = str.toCharArray();
		ch[0] -= 32;
		return String.valueOf(ch);
	}

	/**
	 * 将字符串首字符小写
	 * 
	 * @param str
	 * @return
	 */
	public static String initLowerCase(String str) {
		if (str == null || "".equals(str)) { // 保证操作的数据有内容不为空
			return str;
		}
		char ch[] = str.toCharArray();
		ch[0] += 32;
		return String.valueOf(ch);
	}
	public static  Map<Integer,Integer> splitStringToIntegerMap(String data,String regex1,String regex2){
		Map<Integer,Integer> map = new HashMap<>();
		String temp1[] = data.split(regex1);
		for(String x: temp1) {
			String temp2[] = x.split(regex2);
			if(temp2[0].matches("\\d+") && temp2[1].matches("\\d+")) {
				map.put(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]));
			}else {
				return null;
			}
		}
		return map;
		
	}
}
