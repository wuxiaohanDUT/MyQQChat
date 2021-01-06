package com.fenglin.server.service;

import java.util.List;

import com.fenglin.commons.dao.DbExecute;
import com.fenglin.commons.entity.User;
import com.fenglin.commons.model.Record;
import com.fenglin.commons.utils.JacksonUtils;
import com.fenglin.commons.utils.RecordClassCast;
import com.fenglin.tcp.Request;
import com.fenglin.tcp.Response;


/**
 * 提供的注册、更新用户信息、查询好友信息功能的服务类
 */
public class UserService {

	/**
	 * 处理用户的注册请求
	 * @param request
	 * @return
	 */
	public Response register(Request request) {
		Response response = null;
		try {
			User user = (User) request.getData();

			String sql = "select * from tb_user where username= '" + user.getUsername() + "' " ;
			
			List<Record> list =  DbExecute.executeQuery(sql);
			 
			if (list != null && list.size()>0) return new Response("register",212, "账号已存在,请重新输入账号", null, null);

			String sql1 = "insert into tb_user(username,password) values('" + user.getUsername() + "', "+"'" + user.getPassword() + "')";
			
			int rows = DbExecute.executeUpdate(sql1);
			
			if( rows > 0) {
				List<Record> list1  = DbExecute.executeQuery(sql);
				User u = new User();
				RecordClassCast.RecordToObject(u, list1.get(0).getMap());
				response =  new Response("register",200, "注册成功!", JacksonUtils.obj2json(u), null);
			}else {
				response =  new Response("register",500, "注册失败: 网络异常,请稍后重试!", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 处理用户的更新信息请求
	 * @param request
	 * @return
	 */
	public Response updateUserInfo(Request request) {

		Response response = null;
		try {
			String token = request.getToken();
			User user = (User) JacksonUtils.json2pojo(token, User.class);

			String sql1 = "update  tb_user set password = ?";

			int rows = DbExecute.executeUpdate(sql1);

			response = rows > 0 ? new Response("updateUserInfo",200, "修改成功!", token, null)
					: new Response("updateUserInfo",500, "修改失败: 网络异常,请稍后重试!", token, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取用户的好友列表
	 * 获取user的好友id字符串，用‘，’分隔开成好友id数组，然后从数据库中查询好友的信息
	 * 将从数据库查询得到的好友信息构造成集合对象，然后通过响应携带返还给用户
	 * 用户将根据此好友集合信息初始化好友的信息，并用于构造好友列表
	 * @param request
	 * @return
	 */
	public Response getFirends(Request request) {
		Response response = null;
		try {
			String token = request.getToken();
			User user = (User) JacksonUtils.json2pojo(token, User.class);

			if(user.getFirendsIds() != null && user.getFirendsIds().length()>0) {
				String[] ids = user.getFirendsIds().split(",");
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < ids.length; i++) {
					if (i == ids.length - 1) {
						buffer.append("'" + ids[i] + "'");
					}else {
						buffer.append("'" + ids[i] + "',");
					}
				}
				System.out.println("getFirends--->获取朋友列表--->"+buffer.toString());
				String sql = "select * from tb_user where id in (" + buffer.toString() + ")";
				List<Record> list = DbExecute.executeQuery(sql);
				response = list != null ? new Response("getFirends",200, "好友列表获取成功!", token, list)
						: new Response("getFirends",500, "好友列表获取失败: 网络异常,请稍后重试!", token, null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
