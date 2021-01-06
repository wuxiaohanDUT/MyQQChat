package com.fenglin.commons.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/** 数据库连接工具类 */
public class DBUtils {

	// 利用properties文件初始化数据库连接池
	private static BasicDataSource basic;

	static {
		// properties文件处理对象
		PropertiesUtils cfg = PropertiesUtils.createPropertiesUtils("resources/jdbc.properties");

		String driver = cfg.getValue("driver");
		String url = cfg.getValue("url");
		String username = cfg.getValue("username");
		String password = cfg.getValue("password");
		String initSize = cfg.getValue("initSize");
		String maxSize = cfg.getValue("maxSize");
//		System.out.println("driver="+driver+"; url="+url+"; username"+username+"; password"+password);

		basic = new BasicDataSource();

		basic.setDriverClassName(driver);
		basic.setUrl(url);
		basic.setUsername(username);
		basic.setPassword(password);
	}

	/** 连接数据库 */
	public static Connection getConnection() {

		try {
			Connection conn = basic.getConnection();
			if (conn == null) {
				throw new SQLException("数据库连接失败,请检查是否网络异常!");
			}
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/** 关闭数据库 */
	public void close(Connection conn) {

		if (conn != null) {
			try {
				conn.close();
				System.out.println("数据库连接已关闭");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
