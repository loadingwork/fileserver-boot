package com.lgwork.file.spring;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceUrlProviderTest {
	
	
	@Autowired
	private ResourceUrlProvider resourceUrlProvider;
	
	
	@Test
	public void test() {
		assertNotNull(resourceUrlProvider);
	}
	
	
	@Test
	public void testCss() {
		
		String path = resourceUrlProvider.getForLookupPath("/webjars/bootstrap/4.2.1/css/bootstrap.css");
		System.err.println(path);
//		/webjars/bootstrap/4.2.1/css/bootstrap-7356c18e6f4a8e908bed8a24468e3b1e.css
		
	}
	
	
	

}
