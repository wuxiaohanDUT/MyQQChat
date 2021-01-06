package com.fenglin.commons.entity;

import com.fenglin.commons.utils.JacksonUtils;

/**
 * 用户实体类
 */
public class User extends BaseEntity {
	
	private static final long serialVersionUID = 976955318343270711L;
	
	private int id ;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String avatar;
	private String salt;
	private int grade;
	private int state;
	private int online;
	private String firendsIds ;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String pwd) {
		this.username = name;
		this.password = pwd;
	}
	
	
	
	public User(int id, String username,String avatar, String firendsIds) {
		super();
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		this.firendsIds = firendsIds;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getFirendsIds() {
		return firendsIds;
	}

	public void setFirendsIds(String firendsIds) {
		this.firendsIds = firendsIds;
	}
	
	
	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phone="
				+ phone + ", avatar=" + avatar + ", salt=" + salt + ", grade=" + grade + ", state=" + state
				+ ", firendsIds=" + firendsIds + "]";
	}

	 
	 
	
 
	 
	
	
}
