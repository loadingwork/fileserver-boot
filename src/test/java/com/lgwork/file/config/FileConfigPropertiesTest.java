package com.lgwork.file.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.file.config.FileConfigProperties;


/**
 * 文件配置
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileConfigPropertiesTest {
	
	
	
	@Autowired
	private FileConfigProperties fileConfigProperties;

	@Test
	public void test() {
		System.out.println(fileConfigProperties);
	}

}
