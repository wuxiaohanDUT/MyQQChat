package com.fenglin.tcp;

import java.io.Serializable;
import java.util.Date;

/**
 * 定义的请求实体类
 */
public class Request implements Serializable{

	private static final long serialVersionUID = 4699301442025877168L;

	private static final String GET = "get";
	
	private static final String POST = "post";
	
	private static final String UPDATE = "update";
	
	private  String method;
	
	private  String path;
	/*令牌*/
	private  String token;
	
	private  Object data;
	
	private  String host;
	
	private  int port;
	
	private Date sendTime;
	
	public Request() {
		super();
	}
	
	public Request(String method, String path, String token, Object data, String host, int port) {
		super();
		this.method = method;
		this.token = token;
		this.path = path;
		this.data = data;
		this.host = host;
		this.port = port;
		sendTime = new Date();
	}
	
	public Request(String method, String path, String token, Object data) {
		super();
		this.method = method;
		this.token = token;
		this.path = path;
		this.data = data;
		sendTime = new Date();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "Request [method=" + method + ", path=" + path + ", data=" + data + ", host=" + host + ", port=" + port
				+ ", sendTime=" + sendTime + "]";
	}

 
	
	
	
	

}
