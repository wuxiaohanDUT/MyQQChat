package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fenglin.client.view.HomePage;
import com.fenglin.commons.dao.DbExecute;
import com.fenglin.commons.entity.User;
import com.fenglin.commons.utils.DBUtils;

public class mainTest {

	public static void main(String[] args) {
		 getMysqlConnection();
	}

	private static void getMysqlConnection() {
		// 测试数据库是否连接成功
//		 System.out.println("jdbc="+DBUtils.getConnection());
//		String sql = "update tb_user set online=1 where id= 103";
//		int row = DbExecute.executeUpdate(sql);
//		System.out.println("row="+row);
		new HomePage(new User()).createFrame();
	}

}
