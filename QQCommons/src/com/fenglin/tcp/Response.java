package com.fenglin.tcp;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * 定义的响应实体类
 */
public class Response implements Serializable {

	private static final long serialVersionUID = 7729766224350185410L;

	private int state;

	private String message;

	private String path;

	private String token;

	private Object data;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void success(String path, String message, String token, Object data) {
		this.state = 200;
		this.path = path;
		this.message = message;
		this.token = token;
		this.data = data;

	}

	public void fail(String path, int state, String message, String token, Object data) {
		this.path = path;
		this.state = state;
		this.message = message;
		this.token = token;
		this.data = data;
	}

	public Response(String path,int state, String message, String token, Object data) {
		this.path = path;
		this.state = state;
		this.message = message;
		this.token = token;
		this.data = data;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Response [state=" + state + ", message=" + message + ", path=" + path + ", token=" + token + ", data="
				+ data + "]";
	}

	 

}
