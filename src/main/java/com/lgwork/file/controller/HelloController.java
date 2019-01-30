package com.lgwork.file.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;



@Api(tags= {"测试控制器"})
@RestController
public class HelloController {
	
	
	/**
	 * 测试接口
	 * @return
	 */
	@GetMapping("/")
	public String ping() {
		return "pong";
	}

}
