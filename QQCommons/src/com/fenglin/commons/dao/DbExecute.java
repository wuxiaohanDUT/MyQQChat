package com.fenglin.commons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fenglin.commons.utils.DBUtils;
import com.fenglin.commons.entity.BaseEntity;
import com.fenglin.commons.entity.User;
import com.fenglin.commons.model.Record;

/**
 * 数据库的直接操纵类
 */
public class DbExecute {

	/**
	 * 更新
	 * @param sql
	 * @return
	 */
	public static int executeUpdate(String sql) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			row = stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, stmt, conn);
		}
		return row;
	}

	/**
	 * 查询
	 * @param sql
	 * @return
	 */
	public static List<Record> executeQuery(String sql) {
		List<Record> result = new ArrayList<Record>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			result = RecordBuilder.builde(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt, conn);
		}
		return result;
	}

	/**
	 * 查询单条
	 * @param sql
	 * @return
	 */
	public static Record findBy(String sql) {
		Record record = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Record> list =  RecordBuilder.builde(rs);
			if(list.size() > 0)record = list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt, conn);
		}
		return record;
	}

	/**
	 * 关闭连接，释放资源
	 * @param resu
	 * @param stmt
	 * @param conn
	 */
	private static void close(ResultSet resu, Statement stmt, Connection conn) {
		try {
			if (resu != null) resu.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	 
}
