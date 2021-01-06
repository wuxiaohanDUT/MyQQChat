package com.fenglin.commons.utils;

import java.awt.Component;

import javax.swing.JOptionPane;



/**
*
*/
public class ViewMessageBox {

	 public static void Warning(Component parentComponent,String message) {
		 JOptionPane.showMessageDialog(parentComponent, message, "警告提示",
					JOptionPane.WARNING_MESSAGE);
	 }

	 public static void Success(Component parentComponent,String message) {
		 JOptionPane.showMessageDialog(parentComponent, message, "成功提示",
					JOptionPane.PLAIN_MESSAGE);
	 }
	 
	 public static void Error(Component parentComponent,String message) {
		 JOptionPane.showMessageDialog(parentComponent, message, "错误提示",
					JOptionPane.ERROR_MESSAGE);
	 }

	 
	 
}
