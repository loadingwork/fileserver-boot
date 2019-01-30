package com.lgwork.file.util;

import java.util.Objects;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 密码工具类
 * @author irays
 *
 */
public class PasswordUtils {
	
	
	/**
	 * 加密
	 * @param plaintextPassword  原密码
	 * @param salt  新盐值
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String encryptPassword(Object plaintextPassword, String salt) throws IllegalArgumentException {
		
		SimpleHash simpleHash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, plaintextPassword, salt, 1);
		
		String encrypted = simpleHash.toHex();
		
		return encrypted;
	}
	
	/**
	 * 密码匹配
	 * @param submittedPlaintext   用户提交的密码
	 * @param encrypted   加密后的密码
	 * @param salt   数据库中的盐值
	 * @return
	 */
	public static boolean passwordsMatch(Object submittedPlaintext, String encrypted, String salt) {
		
		SimpleHash simpleHash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, submittedPlaintext, salt, 1);
		
		String encryptedPwd = simpleHash.toHex();
		
		boolean result = Objects.equals(encrypted, encryptedPwd);
		
		return result;
	}

}
