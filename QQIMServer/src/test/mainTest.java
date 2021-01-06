package test;

import com.fenglin.commons.utils.DBUtils;

public class mainTest {

	 public static void main(String[] args) {
		//测试数据库是否连接成功
		 System.out.println("jdbc="+DBUtils.getConnection());
	}

	 
	 private static void getMysqlConnection() {
		 System.out.println("jdbc="+DBUtils.getConnection());
	 }
	 
}
