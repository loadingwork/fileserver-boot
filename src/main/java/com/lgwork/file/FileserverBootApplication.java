package com.lgwork.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring4all.swagger.EnableSwagger2Doc;


/**
 * 程序启动类
 * @author irays
 *
 */
@EnableSwagger2Doc
@tk.mybatis.spring.annotation.MapperScan(basePackages = {
		"com.lgwork.file.mapper"
})
@SpringBootApplication
public class FileserverBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileserverBootApplication.class, args);
	}

}

