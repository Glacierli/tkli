package com.bcli.backend.vo;

import java.util.HashMap;

/**
 * 异常对象
 * @author bcli
 *
 */
public class ErrorInfo {
	
	//当code值大于0 代表没有出现错误，小于0则表示有错误
	private int code;
	
	//异常信息说明
	private String msg;
	
	//
	private HashMap<String,Object> argMap;
	
	public HashMap<String, Object> getArgMap() {
		return argMap;
	}

	public void setArgMap(HashMap<String, Object> argMap) {
		this.argMap = argMap;
	}

	public ErrorInfo() {
		this.code = 0;
		this.msg = "";
	}

	public ErrorInfo(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ErrorInfo [code=" + code + ", msg=" + msg + ", argMap="
				+ argMap + "]";
	}

}
