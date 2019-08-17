package com.bcsfxy.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

public class FileUploadPath {
	/**
	 * SpringBoot jar包运行项目的目录结构为：“*.jar!/BOOT-INF/classes/..”
	 * 如果使用jar包运行项目，则需要找到与jar包同级目录，这样才能进行后续的文件上传操作
	 * “.jar!/BOOT-INF/classes/” 的父目录是“.jar!/BOOT-INF/”
	 * “.jar!/BOOT-INF/” 的父目录是“.jar!/”
	 * “.jar!/” 的父目录是jar包同级目录，所以使用如下代码即可完成操作
	 */
	public static String getFileUploadPath() {
		File parentFile;
		try {
			parentFile = new File(ResourceUtils.getURL("classpath:").getPath());
			if(parentFile.getPath().contains("!")) {//带有!认为是jar包运行项目
				for (int i = 0; i <3; i++) {
					if( parentFile.getParentFile()!= null) {
						parentFile = parentFile.getParentFile();
					}
				}
				if(parentFile.getPath().startsWith("file:")) {
					return parentFile.getPath().substring(5, parentFile.getPath().length())+File.separator ;
				}else {
					return parentFile.getPath()+File.separator ;
				}
			}else {
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		String url = "file:\\F:\\eclipseWorkspace2";
		if(url.startsWith("file:")) {
			url = url.substring(5, url.length());
		}
		System.out.println(url);
	}
}
