package com.lgwork.file.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.file.domain.po.FileStoragePO;
import com.lgwork.file.mapper.FileStorageMapper;


/**
 * 文件测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageMapperTest {
	
	
	/**
	 * 文件储存mapper
	 */
	@Autowired
	private FileStorageMapper fileStorageMapper;

	@Test
	public void test() {
		FileStoragePO selectByFileCode = fileStorageMapper.selectByFileCode("67d1a7f077a24451bd8283ef633fafea");
		System.err.println(selectByFileCode);
	}

}
