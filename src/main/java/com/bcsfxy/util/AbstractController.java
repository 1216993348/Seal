package com.bcsfxy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcsfxy.util.entity.BaseResponse;
import com.github.pagehelper.page.PageMethod;

public abstract class AbstractController {
	@Resource
	private MessageSource messageSource;
	private int currentPage = 1;
	private int pageSize = 10;
	private String column = "";
	private String keyword = "";
	private String columnData = "";

	/*
	 * protected String getUserId() { return
	 * SecurityUtils.getSubject().getPrincipal().toString(); }
	 */
	protected int setPS(int pageSize) {
		return this.pageSize = pageSize;
	}

	public static ResponseEntity<BaseResponse> buildSuccessResponse() {
		BaseResponse response = new BaseResponse();
		response.setState(true);
		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	public static ResponseEntity<BaseResponse> buildResponse(BaseResponse response) {
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	public static ResponseEntity<BaseResponse> buildSuccessResponse(Object result) {
		BaseResponse response = new BaseResponse();
		response.setState(true);
		response.setResult(result);
		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	public static ResponseEntity<BaseResponse> buildSuccessResponse(Object result,String message) {
		BaseResponse response = new BaseResponse();
		response.setState(true);
		response.setResult(result);
		response.setMessage(message);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	public static ResponseEntity<BaseResponse> buildFailResponse() {
		BaseResponse response = new BaseResponse();
		response.setState(false);
		response.setMessage(BaseResponse.FAIL);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	public static ResponseEntity<BaseResponse> buildFailResponse(Object result) {
		BaseResponse response = new BaseResponse();
		response.setState(false);
		response.setResult(result);
		response.setMessage(BaseResponse.FAIL);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	public static ResponseEntity<BaseResponse> buildFailResponse(Object result,String message) {
		BaseResponse response = new BaseResponse();
		response.setState(false);
		response.setResult(result);
		response.setMessage(message);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @param url
	 * @return
	 */
	/*
	 * protected boolean deleteFile(String fileName,String url) { return new
	 * File(this.getRequest().getServletContext().getRealPath(url) +
	 * fileName).delete(); }
	 */
	/**
	 * 保存多个上传文件
	 */
	protected List<String> saveMultipartFile(HttpServletRequest request,String path) {
		List<String> list = null;
		if (request instanceof MultipartHttpServletRequest) {
			list = new ArrayList<>();

			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> files = mRequest.getFiles("photo");
			Iterator<MultipartFile> fIter = files.iterator();
			while (fIter.hasNext()) {
				MultipartFile file = fIter.next();
				String fileName = this.createFileName(file);
				if (this.saveFile(path+fileName, file)) {
					list.add(fileName);
				}
			}
		}
		return list;
	}
	protected boolean moveFile(String oldUrl,String newUrl) {
		InputStream input = null;
		OutputStream output = null;
		File oldFile = new File(oldUrl);
		try {
			//System.out.println("oldFile.exists()是否存在："+oldFile.exists());
			if(oldFile.exists()) {
				input = new FileInputStream(oldFile);
				File newFile = new File(newUrl);
				if (!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				//System.out.println("文件目录是否存在："+!newFile.getParentFile().exists());
				output = new FileOutputStream(newFile);
				byte[] data = new byte[1024];
				int len = 0;
				while ((len = input.read(data)) != -1) {
					output.write(data, 0, len);
				}
				
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (input != null) {
					input.close();
					oldFile.delete();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param url
	 * @param file
	 * @return
	 */
	protected boolean saveFile(String filePath, MultipartFile file) {
		if (file == null || file.getSize() == 0) {
			return false;
		}
		InputStream input = null;
		OutputStream output = null;
		try {
			input = file.getInputStream();
			File saveFile = new File(filePath);
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			output = new FileOutputStream(saveFile);
			byte[] data = new byte[1024];
			int len = 0;
			while ((len = input.read(data)) != -1) {
				output.write(data, 0, len);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	protected String getClassPath() {
		try {
			return ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 创建一个文件名称
	 * 
	 * @param file
	 * @return
	 */
	protected String createFileName(String ext) {
		return UUID.randomUUID().toString()+ext;
	}
	protected String createFileName(MultipartFile file) {
		if (file != null) {
			String name = file.getOriginalFilename();
			if (name.contains(".")) {
				return UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
			}
		}
		return UUID.randomUUID().toString()+"fileisnull";
	}

	/**
	 * 取出request对象
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 取出session对象
	 */
	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	/**
	 * 取出application对象
	 */
	public ServletContext getApplication() {
		HttpServletRequest request = this.getRequest();
		if (request != null) {
			return request.getSession().getServletContext();
		}

		return null;
	}

	/**
	 * 分页预处理参数
	 */
	public void initSplitParam(String url) {
		HttpServletRequest request = this.getRequest();
		try {
			currentPage = Integer.parseInt(request.getParameter("cp"));
		} catch (Exception e) {
			currentPage= 1;
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("ps"));
		} catch (Exception e) {
		}
		this.getRequest().setAttribute("currentPage", this.getCP());
		this.getRequest().setAttribute("pageSize", this.getPS());
		this.getRequest().setAttribute("column", this.getCol());
		this.getRequest().setAttribute("keyWord", this.getKW());
		this.getRequest().setAttribute("url", url);
		PageMethod.startPage(this.getCP(), this.getPS());
	}

	/**
	 * 分页预处理参数
	 */
	public void initSplitParam(String defaultColumn, String columnData, String url) {
		HttpServletRequest request = this.getRequest();
		try {
			currentPage = Integer.parseInt(request.getParameter("cp"));
		} catch (Exception e) {
			currentPage= 1;
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("ps"));
		} catch (Exception e) {
		}
		column = request.getParameter("col");
		boolean flag = true;// 使用默认列
		String columnTemp[] = columnData.split("\\|");
		for (int i = 0; i < columnTemp.length; i++) {
			String temp[] = columnTemp[i].split(":");
			if (temp[1].equals(column)) {
				flag = false;// 如果包含被查询的列，则不使用默认列
				break;
			}
		}
		if (flag) {
			column = defaultColumn;
		}
		keyword = request.getParameter("kw");
		if (keyword == null) {
			keyword = "";
		}
		this.columnData = columnData;
		this.getRequest().setAttribute("currentPage", this.getCP());
		this.getRequest().setAttribute("pageSize", this.getPS());
		this.getRequest().setAttribute("column", this.getCol());
		this.getRequest().setAttribute("keyWord", this.getKW());
		this.getRequest().setAttribute("url", url);
		this.getRequest().setAttribute("columnData", this.columnData);
		PageMethod.startPage(this.getCP(), this.getPS());
	}

	/**
	 * 分页预处理参数
	 */
	public void initAjaxSplitParam() {
		HttpServletRequest request = this.getRequest();
		try {
			currentPage = Integer.parseInt(request.getParameter("cp"));
		} catch (Exception e) {
			currentPage= 1;
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("ps"));
		} catch (Exception e) {
		}
		keyword = request.getParameter("kw");
		PageMethod.startPage(this.getCP(), this.getPS());
	}

	/**
	 * 分页预处理参数
	 */
	public void initAjaxSplitParam(String defaultColumn, String columnData) {
		HttpServletRequest request = this.getRequest();
		try {
			currentPage = Integer.parseInt(request.getParameter("cp"));
		} catch (Exception e) {
			currentPage= 1;
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("ps"));
		} catch (Exception e) {
		}
		column = request.getParameter("col");
		boolean flag = true;// 使用默认列
		String columnTemp[] = columnData.split("\\|");
		for (int i = 0; i < columnTemp.length; i++) {
			String temp[] = columnTemp[i].split(":");
			if (temp[1].equals(column)) {
				flag = false;// 如果包含被查询的列，则不使用默认列
				break;
			}
		}
		if (flag) {
			column = defaultColumn;
		}
		keyword = request.getParameter("kw");
		if (keyword == null) {
			keyword = "";
		}
		this.columnData = columnData;
		PageMethod.startPage(this.getCP(), this.getPS());
	}
	
	/**
	 * 取得当前页数
	 * 
	 * @return
	 */
	public int getCP() {
		return currentPage;
	}

	/**
	 * 取得页面大小
	 * 
	 * @return
	 */
	public int getPS() {
		return pageSize;
	}

	/**
	 * 取得模糊查询列
	 * 
	 * @return
	 */
	public String getCol() {
		return column;
	}
	public String getColumnData() {
		return this.columnData;
	}
	/**
	 * 取得模糊查询条件
	 * 
	 * @return
	 */
	public String getKW() {
		return keyword;
	}

	/**
	 * 取得资源文件信息
	 * 
	 * @param key
	 *            信息的key
	 * @param param
	 *            参数
	 * @return
	 */
	public String getResource(String key, String... param) {
		return this.messageSource.getMessage(key, param, Locale.getDefault());
	}

	/**
	 * 日期转换
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {// 进行WEB数据转换绑定
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 注册一个专门的日期转换类，并允许为空
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

}
