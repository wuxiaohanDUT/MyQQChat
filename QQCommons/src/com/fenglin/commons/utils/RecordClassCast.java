package com.fenglin.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fenglin.commons.entity.BaseEntity;

/**
 *
 */
public class RecordClassCast {

	
	 
	// 把一个字符串的第一个字母大写、效率是最高的、
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/**Record 转换为具体的实体类
	 * 
	 * @param object
	 * @param map
	 * @throws Exception
	 */
	public static void RecordToObject(Object object, Map<String, Object> map) throws Exception {
		
		if (object != null && object instanceof BaseEntity) {
			 
			Class<?> clz = object.getClass();
		
			Field[] fields = clz.getDeclaredFields();// 获取实体类的所有属性，返回Field数组

			for (Field field : fields) {
				String fieldName = field.getName();
				Object fieldValue = map.get(fieldName);
				if(fieldValue == null) continue;
//				System.out.println("name="+fieldName+", typt="+field.getGenericType()
//				 +", value="+fieldValue+", fieldValue.type="+fieldValue.getClass().toString());
				Method m = null;
				
				if("serialVersionUID".equals(fieldName)) continue;
				
				if (field.getGenericType().toString().equals("boolean")) {
					m = (Method) object.getClass().getMethod("set" +getMethodName(fieldName), boolean.class);
				}
				
				if (field.getGenericType().toString().equals("int")) { 
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), int.class);
				}
				
				if (field.getGenericType().toString().equals("short")) { 
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), short.class);
				}
				 
				if (field.getGenericType().toString().equals("double")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), double.class);
				}
				
				if (field.getGenericType().toString().equals("byte")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), byte.class);
				}
				
				if (field.getGenericType().toString().equals("long")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), long.class);
				}
				
				if (field.getGenericType().toString().equals("float")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), float.class);
				}
				

				if (field.getGenericType().toString().equals("char")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), char.class);
				}
				
				if (field.getGenericType().toString().equals("class java.lang.String")) { 
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), String.class);
				}
				
				if (field.getGenericType().toString().equals("class java.lang.Integer")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), Integer.class);
				}

				if (field.getGenericType().toString().equals("class java.lang.Double")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), Double.class);
				}

				if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
					m = (Method) object.getClass().getMethod("set" +getMethodName(fieldName), Boolean.class);
				}

				if (field.getGenericType().toString().equals("class java.util.Date")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), Date.class);
				}
				 
				if (field.getGenericType().toString().equals("class java.lang.Short")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), Short.class);
				}
				 
				if (field.getGenericType().toString().equals("class java.sql.Clob")) {
					m = (Method) object.getClass().getMethod("set" + getMethodName(fieldName), Short.class);
				}
				
				// 调用setter方法设置取属性值
				if (fieldValue!= null) {
					m.invoke(object,fieldValue);
				}
			}
		} 
	}

	
}
