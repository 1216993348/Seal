package com.bcsfxy.util.entity;

import java.io.Serializable;

public class BaseResponse implements Serializable {
	private static final long serialVersionUID = -5914359094466481230L;
	public static String SUCCESS ="success";
	public static String FAIL ="fail";
	public static String ERROR ="error";
	private Boolean state =false ;
	private String message;
	private Object result;
	public BaseResponse() {
	} 
	public BaseResponse(Boolean state, String message, Object result) {
		this.state = state;
		this.message = message;
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return this.result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

}
