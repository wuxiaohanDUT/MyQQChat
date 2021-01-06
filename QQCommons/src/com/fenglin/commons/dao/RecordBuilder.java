package com.fenglin.commons.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fenglin.commons.model.Record;

/**
 * 用来将sql查询得到的resultset数据类型封装成record集合对象
 */
public class RecordBuilder {

	public static final List<Record> builde(ResultSet rs) {
		List<Record> result = new ArrayList<Record>();
		try {
			ResultSetMetaData metaData =  rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] labelNames = new String[columnCount + 1];
			int [] type = new int[columnCount+1];
			buildLabelNamesAndTypes(metaData, labelNames,type);
			while(rs.next()) {
				Record record = new Record();
				record.setColumnsMap(new HashMap<String, Object>());
				Map<String, Object> columns = record.getMap();
				for (int i = 1; i <= columnCount; i++) {
					Object value ;
					if(type[i] < Types.BLOB) {
						value = rs.getObject(i);
					} else if(type[i] < Types.CLOB) {
						value = ModelBuilder.handleClob(rs.getClob(i));
					}else if(type[i] < Types.NCLOB) {
						value = ModelBuilder.handleClob(rs.getNClob(i));
					}else if(type[i] < Types.BLOB) {
						value = ModelBuilder.handleBlob(rs.getBlob(i));
					}else {
						value = rs.getObject(i);
					}
					columns.put(labelNames[i], value);
				}
				result.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 return result;
	}

	
	
	private static final void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types) throws SQLException {
		for (int i=1; i<labelNames.length; i++) {
			labelNames[i] = rsmd.getColumnLabel(i);
			types[i] = rsmd.getColumnType(i);
		}
	}
	 
	
	
}
