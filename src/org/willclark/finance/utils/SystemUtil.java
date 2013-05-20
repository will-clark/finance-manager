package org.willclark.finance.utils;

public class SystemUtil {

	public static void shutdown(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}
	
}
