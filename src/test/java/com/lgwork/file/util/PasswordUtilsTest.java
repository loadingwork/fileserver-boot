package com.lgwork.file.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lgwork.file.util.PasswordUtils;
import com.lgwork.file.util.UuidUtils;

public class PasswordUtilsTest {

	@Test
	public void test() {
		
		String salt = UuidUtils.uuid32();
		
		String plaintextPassword = "123456";
		
		String encryptPassword = PasswordUtils.encryptPassword(plaintextPassword, salt);
		
		boolean passwordsMatch = PasswordUtils.passwordsMatch(plaintextPassword, encryptPassword, salt);
		
		assertTrue(passwordsMatch);
		
	}

}
