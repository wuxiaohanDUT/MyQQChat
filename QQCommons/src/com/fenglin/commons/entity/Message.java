package com.fenglin.commons.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @auther
* @Email
* @date
* @Description
*/
public class Message implements Serializable{

	private static final long serialVersionUID = 3489550786710278020L;

	private String msg; 
	
	private int firendsId;

	private Date date; 
	
	public Message(String msg, int firendsId) {
		super();
		this.msg = msg;
		this.firendsId = firendsId;
		this.date = new Date();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getFirendsId() {
		return firendsId;
	}

	public void setFirendsId(int firendsId) {
		this.firendsId = firendsId;
	}

	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Message [msg=" + msg + ", firendsId=" + firendsId + ", date=" + date + "]";
	}

	 

	 
	 
}
