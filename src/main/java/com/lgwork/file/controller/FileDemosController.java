package com.lgwork.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;


/**
 * 文件系统
 * @author irays
 *
 */
@Api(tags= {"上传实例控制器"})
@RequestMapping("/file/demos")
@Controller
public class FileDemosController {
	
	
	@GetMapping("/toUpload.do")
	public String toUpload() {
		return "/file/upload_file";
	}
	
	
	/**
	 * 普通form表单上传
	 * @return
	 */
	@GetMapping("/toCommonForm.do")
	public String toFormUpload() {
		return "/home/demos/form/common-file";
	}
	
	
	/**
	 * formdata异步上传
	 * @return
	 */
	@GetMapping("/toAjaxIndex.do")
	public String toAjaxIndex() {
		return "/home/demos/formdata/ajax_index";
	}
	
	/**
	 * jquery 插件上传
	 * @return
	 */
	@GetMapping("/toJqueryFormIndex.do")
	public String toJqueryFormIndex() {
		return "/home/demos/jquery_upload/index";
	}
	
	
	
	/**
	 * jquery plugin 基本上传
	 * @return
	 */
	@GetMapping("/jquery/basic.do")
	public String toJqBasic() {
		return "/home/demos/jquery_upload/demos/basic";
	}
	
	/**
	 * 高级上传
	 * @return
	 */
	@GetMapping("/jquery/basic_plus.do")
	public String toJqBasicPlus() {
		return "/home/demos/jquery_upload/demos/basic-plus";
	}
	

}
