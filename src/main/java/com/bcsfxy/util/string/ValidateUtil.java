package com.bcsfxy.util.string;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
/**
 * 提供有数据验证的方法
 * @author Administrator
 *
 */
public class ValidateUtil {
	/** 
	 * 基于Spring MVC 拦截器进行的服务器端数据验证
	 * @param rule
	 * @param request
	 * @param errors
	 * @param source
	 */
	public static void validate(String rule,HttpServletRequest request,Map<String,String> errors,MessageSource source){
		String temp[] = rule.split(":");
		boolean isAllowNull = true;
		if(temp.length == 2 || "false".equals(temp[2])){//如果验证规则是rids:int[],即temp长度为2，则默认不能为null或空串
			//当前验证的情况不允许为null
			isAllowNull = false;
		}
		if(temp[1].contains("[]")){//按照数组处理
			String data[] = request.getParameterValues(temp[0]+"[]");
			if(data != null){
				//System.out.print("不能为空      ");
				if("int[]".equals(temp[1])){
					for(int x = 0; x < data.length; x++) {
						if(!ValidateUtil.validateInt(data[x],isAllowNull)){//没通过验证！
							errors.put(temp[0], source.getMessage("int.validate.error.msg", null,null));//添加错误信息！
						}
					}
				}else if("double[]".equals(temp[1])){
					for(int x = 0; x < data.length; x++) {
						if(!ValidateUtil.validateDouble(data[x],isAllowNull)){//没通过验证！
							errors.put(temp[0], source.getMessage("double.validate.error.msg", null,null));//添加错误信息！
						}
					}
				}else if("string[]".equals(temp[1])){
					for(int x = 0; x < data.length; x++) {
						if(isAllowNull) {//如果验证规则是MessageController.insert=sid:string[]:1,2 则isAllowNull为true
							String range[] = temp[2].split(",");
							if(!ValidateUtil.validateString(data[x],Integer.parseInt(range[0]),Integer.parseInt(range[1]))){//没通过验证！
								errors.put(temp[0], source.getMessage("string.validate.error.msg", range,null));//添加错误信息！
							}
						}else if(!ValidateUtil.validateString(data[x])){//否则验证规则是MessageController.insert=sid:string ,没有限制范围
							errors.put(temp[0], source.getMessage("string.validate.error.msg", null,null));//添加错误信息！
						}
					}
				}else if("date[]".equals(temp[1])){
					for(int x = 0; x < data.length; x++) {
						if(!ValidateUtil.validateDate(data[x],isAllowNull)){//没通过验证！
							errors.put(temp[0], source.getMessage("date.validate.error.msg", null,null));//添加错误信息！
						}
					}
				}else if("long[]".equals(temp[1])){
					for(int x = 0; x < data.length; x++) {
						if(!ValidateUtil.validateLong(data[x],isAllowNull)){//没通过验证！
							errors.put(temp[0], source.getMessage("long.validate.error.msg", null,null));//添加错误信息！
						}
					}
				}
			}else{//不允许为空的情况下数据为空了，则没有通过验证,添加错误信息
				if("int[]".equals(temp[1])){
					errors.put(temp[0], source.getMessage("int.validate.error.msg", null,null));//添加错误信息！
				}else if("double[]".equals(temp[1])){
					errors.put(temp[0], source.getMessage("double.validate.error.msg", null,null));//添加错误信息！
				}else if("string[]".equals(temp[1])){
					errors.put(temp[0], source.getMessage("string.validate.error.msg", null,null));//添加错误信息！
				}else if("date[]".equals(temp[1])){
					errors.put(temp[0], source.getMessage("date.validate.error.msg", null,null));//添加错误信息！
				}else if("long[]".equals(temp[1])){
					errors.put(temp[0], source.getMessage("long.validate.error.msg", null,null));//添加错误信息！
				}
			}
		}else{//按照单值处理
			String data = request.getParameter(temp[0]);
			//if(temp.length == 2 || "false".equals(temp[2])){//如果验证规则是news.nid:int,即temp长度为2，则默认不能为null或空串
				//System.out.print("不能为空      ");
			if("int".equals(temp[1])){
				if(!ValidateUtil.validateInt(data,isAllowNull)){//没通过验证！
					errors.put(temp[0], source.getMessage("int.validate.error.msg", null,null));//添加错误信息！
				}
			}else if("double".equals(temp[1])){
				if(!ValidateUtil.validateDouble(data,isAllowNull)){
					errors.put(temp[0], source.getMessage("double.validate.error.msg", null,null));//添加错误信息！
				}
			}else if("string".equals(temp[1])){
				if(isAllowNull) {//如果验证规则是MessageController.insert=sid:string:1,2 则isAllowNull为true
					String range[] = temp[2].split(",");
					if(!ValidateUtil.validateString(data,Integer.parseInt(range[0]),Integer.parseInt(range[1]))){//没通过验证！
						errors.put(temp[0], source.getMessage("string.validate.error.msg", range,null));//添加错误信息！
					}
				}else if(!ValidateUtil.validateString(data)){//否则验证规则是MessageController.insert=sid:string ,没有限制范围
					errors.put(temp[0], source.getMessage("string.validate.error.msg", null,null));//添加错误信息！
				}
			}else if("date".equals(temp[1])){
				if(!ValidateUtil.validateDate(data,isAllowNull)){
					errors.put(temp[0], source.getMessage("date.validate.error.msg", null,null));//添加错误信息！
				}
			}else if("rand".equals(temp[1])) {
				String code = request.getParameter("code");
				String rand = (String)request.getSession().getAttribute("rand");
				if(rand == null || !rand.equalsIgnoreCase(code)) {
					errors.put(temp[0], source.getMessage("rand.validate.error.msg", null,null));//添加错误信息！
				}
			}else if("long".equals(temp[1])) {
				if(!ValidateUtil.validateLong(data,isAllowNull)){
					errors.put(temp[0], source.getMessage("long.validate.error.msg", null,null));//添加错误信息！
				}
			}
		}
	}
	/**
	 * 验证字符串是否满足长度范围，默认验证是否为空
	 * @param str 被验证的字符串
	 * @param range 表示字符串长度范围，range[0]为最小长度，range[1]为最大长度，闭区间
	 * @return 如果字符串不为空且长度不为零,或者满足长度范围返回true,否则返回flase
	 */
	public static boolean validateString(String str,Integer ... range){
		//如果没有规定范围，则不允许为null
		if(range == null || range.length != 2){
			return str != null && !"".equals(str);
		}else{
			if(range[0] == 0) {//长度范围如果包含0，则可以为null
				return str == null || str.length() <= range[1];
			}else {//长度范围如果大于0，则不允许为null
				return str != null && str.length() >= range[0] && str.length() <= range[1];
			}
		}
	}
	/**
	 * 进行字符串的正则验证
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean validateRegex(String str,String regex){
		if(validateString(str)){
			return str.matches(regex);
		}else{
			return false;
		}
	}
	/**
	 * 判断字符串是否可以转换为Int数据
	 * @param str
	 * @param empty
	 * @return
	 */
	public static boolean validateInt(String str,boolean empty){
		if(empty){//允许为空
			if(str == null || "".equals(str)){
				return true;
			}
		}
		try {
			Integer.valueOf(str);
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	/**
	 * 判断字符串是否可以转换为Double数据
	 * @param str
	 * @param empty
	 * @return
	 */
	public static boolean validateDouble(String str,boolean empty){
		if(empty){//允许为空
			if(str == null || "".equals(str)){
				return true;
			}
		}
		try {
			if(validateString(str)) {
				Double.valueOf(str);
			}else {
				return false;
			}
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	/**
	 * 判断字符串是否可以转换为Date数据
	 * @param str
	 * @param empty
	 * @return
	 */
	public static boolean validateDate(String str,boolean empty){
		if(empty){//允许为空
			if(str == null || "".equals(str)){
				return true;
			}
			if(str.matches("\\d{4}-\\d{2}-\\d{2}")){
				return true;
			}else{
				return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
			}
		}else{
			if(ValidateUtil.validateString(str)){
				if(str.matches("\\d{4}-\\d{2}-\\d{2}")){
					return true;
				}else{
					return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
				}
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 判断字符串是否可以转换为Long数据
	 * @param str
	 * @param empty
	 * @return
	 */
	public static boolean validateLong(String str,boolean empty){
		if(empty){//允许为空
			if(str == null || "".equals(str)){
				return true;
			}
		}
		try {
			Long.valueOf(str);
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
