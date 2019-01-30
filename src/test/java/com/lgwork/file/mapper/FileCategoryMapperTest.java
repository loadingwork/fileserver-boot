package com.lgwork.file.mapper;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.file.mapper.FileCategoryMapper;



/**
 * 文件分类测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileCategoryMapperTest {
	
	
	@Autowired
	private FileCategoryMapper fileCategoryMapper;

	@Test
	public void test() {
		assertNotNull(fileCategoryMapper);
	}

}
