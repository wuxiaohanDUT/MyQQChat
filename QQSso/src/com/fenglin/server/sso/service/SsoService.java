package com.fenglin.server.sso.service;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import com.fenglin.commons.dao.DbExecute;
import com.fenglin.commons.entity.User;
import com.fenglin.commons.model.Record;
import com.fenglin.tcp.Request;
import com.fenglin.tcp.Response;
import com.fenglin.tcp.SocketUtils;
import com.fenglin.commons.utils.JacksonUtils;
import com.fenglin.commons.utils.RecordClassCast;

public class SsoService {

	/*记录用户信息*/
	private  Map<Integer, User> loginMap = new HashMap<Integer, User>();

	/*用来存储所有和sso服务器建立连接用户的套接字*/
	private  Map<Integer, Socket> loginSocketMap = new HashMap<Integer, Socket>();

	/**
	 * 负责对用户的请求进行分派
	 * @param socket
	 */
	public void dispathSevlet(Socket socket) {
		Response response = null;
		try {
				Request request =  SocketUtils.readeRequest(socket);
				System.out.println("request--->"+request);
				
				if ("login".equals(request.getPath())) {
					response = login(request,socket);
					SocketUtils.sendResponse(socket, response);
				}
	
				if ("logOut".equals(request.getPath())) {
					logOut(request);
					socket.close();
				}
	
				if ("loginCheck".equals(request.getPath())) {
					response = loginCheck(request);
					SocketUtils.sendResponse(socket, response);
					socket.close();
				}
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理用户登录请求，判断用户输入的信息是否正确，包括账号是否存在，密码是否正确
	 * 用户成功登录后，sso服务器会将记录用户id - 套接字映射，以便在用户之间转发消息
	 * sso服务器帮助用户广播自己登录成功的消息，通过遍历已经与sso服务器建立套接字连接的映射集来广播消息
	 * @param request
	 * @param socket
	 * @return
	 */
	public Response login(Request request,Socket socket) {

		Response response = null;
		User user = (User) request.getData();

		String sql = "select * from tb_user where username= '" + user.getUsername() + "' ";

		Record record = (Record) DbExecute.findBy(sql); //查询并封装用户信息
		if (record == null)	return new Response("login",210, "账号不存在", null, null);
		
		try {
			String MD5Password = DigestUtils.md5Hex(user.getPassword());
			System.out.println(MD5Password+" "+user.getPassword());
			if (MD5Password.equals(DigestUtils.md5Hex(record.getStr("password")))){//检查用户输入的密码是否正确
				User u = new User();
				RecordClassCast.RecordToObject(u, record.getMap()); //通过反射利用record中的信息初始化一个user用户实体
				// 登陆后既上线了, 将用户信息添加到上线map中
				loginMap.put(u.getId(), u);//用户Id-用户实体
				loginSocketMap.put(u.getId(), socket);//用户Id-套接字
				System.out.println(u.getUsername()+"上线了!: ");
				sql = "update tb_user set online=1 where id=" + u.getId();
				int row = DbExecute.executeUpdate(sql);//用户登录成功，更新数据库中的用户登录标志位
				response = new Response("login",200, "登录成功!", record.toString(), u);
				
				//登录成功后,广播给所有好友自己上线了
				Set<Integer> set = loginMap.keySet();
				for (int id : set ) {
					User us = loginMap.get(id);	
					if(us.getFirendsIds()!=null&&us.getFirendsIds().indexOf(u.getId()+"") != -1 ) {
						Response res = new Response("online-infrom",200, "好友"+u.getUsername()+"上线了!", JacksonUtils.obj2json(u), u);
						Socket soc = loginSocketMap.get(us.getId());
						SocketUtils.sendResponse(soc, res);
					}
				}
			}else {
				response = new Response("login",211, "密码输入有误,请重新尝试", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 查询指定id的用户是否在线
	 * @param request
	 * @return
	 */
	public Response loginCheck(Request request) {
		Response response = null;
			try {
				int id = (int) request.getData();
				User u = loginMap.get(id);
				if (u == null) {
					response = new Response("loginCheck",200, id + "不在线", request.getToken(), null);
				} else {
					response = new Response("loginCheck",200, id + "在线", JacksonUtils.obj2json(u), loginSocketMap.get(id));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return response;
	}

	/**
	 * 用户下线功能，广播给好友下线的消息
	 * 并对数据库做出相应的修改，修改是否在线标识位为0
	 * @param request
	 */
	public void logOut(Request request) {
		try {
				User u = (User) request.getData();
				String sql = "update tb_user set online=0 where id=" + u.getId();
				System.out.println("sql = "+sql);
				int row = DbExecute.executeUpdate(sql);
				System.out.println(row == 1 ? "用户已经成功下线!":"用户下线异常!");
				
				//下线成功后,广播给所有好友自己下线了
				Set<Integer> set = loginMap.keySet();
				for (int id : set ) {
					User us = loginMap.get(id);	
					if(us.getFirendsIds().indexOf(u.getId()+"") != -1 ) {
						Response res = new Response("offline",200, "好友"+u.getUsername()+"下线了!", JacksonUtils.obj2json(u), u);
						Socket soc = loginSocketMap.get(us.getId());
						SocketUtils.sendResponse(soc, res);
					}
				}
				loginMap.remove(u.getId());
				loginSocketMap.remove(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
