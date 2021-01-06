package com.fenglin.commons.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Record implements Serializable{

	private static final long serialVersionUID = -8855739303083130594L;
	
	private Map<String, Object> map;
	
	public Map<String, Object> getMap() {
		return this.map;
	}

	public void setColumnsMap(Map<String, Object> columns) {
		this.map = columns;
	}
	
	public Record setColumns(Map<String, Object> columns) {
		this.getMap().putAll(columns);
		return this;
	}
	

	@SuppressWarnings("unused")
	private void processColumnsMap() {
		if (map == null || map.size() == 0) {
			map = new HashMap<String, Object>();
		} else {
			Map<String, Object> columnsOld = map;
			map = new HashMap<String, Object>();
			map.putAll(columnsOld);
		}
	}
	
	

	
	/**
	 * Set columns value with Record.
	 * @param record the Record object
	 */
	public Record setColumns(Record record) {
		getMap().putAll(record.getMap());
		return this;
	}
	
	 
	/**
	 * Remove attribute of this record.
	 * @param column the column name of the record
	 */
	public Record remove(String column) {
		getMap().remove(column);
		return this;
	}
	
	/**
	 * Remove columns of this record.
	 * @param columns the column names of the record
	 */
	public Record remove(String... columns) {
		if (columns != null)
			for (String c : columns)
				this.getMap().remove(c);
		return this;
	}
	
	/**
	 * Remove columns if it is null.
	 */
	public Record removeNullValueColumns() {
		for (java.util.Iterator<Entry<String, Object>> it = getMap().entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() == null) {
				it.remove();
			}
		}
		return this;
	}
	
	/**
	 * Keep columns of this record and remove other columns.
	 * @param columns the column names of the record
	 */
	public Record keep(String... columns) {
		if (columns != null && columns.length > 0) {
			Map<String, Object> newColumns = new HashMap<String, Object>(columns.length);	// getConfig().containerFactory.getColumnsMap();
			for (String c : columns)
				if (this.getMap().containsKey(c))	// prevent put null value to the newColumns
					newColumns.put(c, this.getMap().get(c));
			
			this.getMap().clear();
			this.getMap().putAll(newColumns);
		}
		else
			this.getMap().clear();
		return this;
	}
	
	/**
	 * Keep column of this record and remove other columns.
	 * @param column the column names of the record
	 */
	public Record keep(String column) {
		if (getMap().containsKey(column)) {	// prevent put null value to the newColumns
			Object keepIt = getMap().get(column);
			getMap().clear();
			getMap().put(column, keepIt);
		}else {
			getMap().clear();
		}
		return this;
	}
	
	/**
	 * Remove all columns of this record.
	 */
	public Record clear() {
		getMap().clear();
		return this;
	}
	
	/**
	 * Set column to record.
	 * @param column the column name
	 * @param value the value of the column
	 */
	public Record set(String column, Object value) {
		getMap().put(column, value);
		return this;
	}
	
	/**
	 * Get column of any mysql type
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String column) {
		return (T)getMap().get(column);
	}
	
	/**
	 * Get column of any mysql type. Returns defaultValue if null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String column, Object defaultValue) {
		Object result = getMap().get(column);
		return (T)(result != null ? result : defaultValue);
	}
	
	/**
	 * Get column of mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
	 */
	public String getStr(String column) {
		return (String)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
	 */
	public Integer getInt(String column) {
		return (Integer)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: bigint
	 */
	public Long getLong(String column) {
		return (Long)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String column) {
		return (java.math.BigInteger)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: date, year
	 */
	public java.util.Date getDate(String column) {
		return (java.util.Date)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: time
	 */
	public java.sql.Time getTime(String column) {
		return (java.sql.Time)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: timestamp, datetime
	 */
	public java.sql.Timestamp getTimestamp(String column) {
		return (java.sql.Timestamp)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: real, double
	 */
	public Double getDouble(String column) {
		return (Double)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: float
	 */
	public Float getFloat(String column) {
		return (Float)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String column) {
		return (Boolean)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String column) {
		return (java.math.BigDecimal)getMap().get(column);
	}
	
	/**
	 * Get column of mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob
	 * I have not finished the test.
	 */
	public byte[] getBytes(String column) {
		return (byte[])getMap().get(column);
	}
	
	/**
	 * Get column of any type that extends from Number
	 */
	public Number getNumber(String column) {
		return (Number)getMap().get(column);
	}
	

	
	public boolean equals(Object o) {
		if (!(o instanceof Record))
            return false;
		if (o == this)
			return true;
		return this.getMap().equals(((Record)o).getMap());
	}
	
	public int hashCode() {
		return getMap() == null ? 0 : getMap().hashCode();
	}
	
	/**
	 * Return column names of this record.
	 */
	public String[] getColumnNames() {
		Set<String> attrNameSet = getMap().keySet();
		return attrNameSet.toArray(new String[attrNameSet.size()]);
	}
	
	/**
	 * Return column values of this record.
	 */
	public Object[] getColumnValues() {
		java.util.Collection<Object> attrValueCollection = getMap().values();
		return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
	}
	
	@Override
	public String toString() {
		return "" + map ;
	}
	 
}
