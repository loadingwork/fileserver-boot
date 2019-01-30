package com.lgwork.file.util;

import java.util.UUID;

public class UuidUtils {
	
	
	/**
	 * 获取uu32
	 * @return
	 */
	public static String uuid32() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
