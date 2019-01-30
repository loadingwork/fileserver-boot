package com.lgwork.file.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.file.domain.po.PubUserPO;
import com.lgwork.file.util.PasswordUtils;
import com.lgwork.file.util.UuidUtils;


/**
 * 用户测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubUserMapperTest {
	
	/**
	 * 用户信息
	 */
	@Autowired
	private PubUserMapper pubUserMapper;

	@Test
	public void testSave() {
		
		String salt = UuidUtils.uuid32();
		String ucode = UuidUtils.uuid32();
		
		String encryptPassword = PasswordUtils.encryptPassword("123456", salt);
		
		PubUserPO pubUserPO = new PubUserPO();
		
		pubUserPO.setDeleted(Boolean.FALSE);
		pubUserPO.setGmtCreate(new Date());
		
		pubUserPO.setUcode(ucode);
		pubUserPO.setUsername(ucode);
		pubUserPO.setPassword(encryptPassword);
		pubUserPO.setSalt(salt);
		pubUserPO.setNickname(ucode);
		pubUserPO.setEnabled(true);
		pubUserPO.setLastLoginTime(new Date());
		
		
		pubUserMapper.insert(pubUserPO);
	}

}
