package test;

import java.sql.Connection;
import java.util.List;


import com.fenglin.commons.utils.DBUtils;
import com.fenglin.commons.dao.DbExecute;
import com.fenglin.commons.model.Record;

public class mainTest {

	 public static void main(String[] args) {
		 getMysqlConnection();
//		 query();
//		 00d797e4b576e25c922e84c9d533ad5a
//		 System.out.println(DigestUtils.md5Hex("wck123"));
		 
	}

	 //测试数据库是否连接成功
	 private static void getMysqlConnection() {
		 Connection conn = DBUtils.getConnection();
		 System.out.println( conn !=null?"Mysql连接成功, CONN="+conn:"Mysql连接失败");
	 }
	 
	 @SuppressWarnings("unchecked")
	 public static void query() {
		String sql = "select * from tb_user where username= 'wck'";
		
		List<Record> list = DbExecute.executeQuery(sql);
	    if(list.size() > 0) {
	    	for (Record record : list) {
	    		System.out.println(record);
			}
	    }
		
	 }
	 
}
