package com.lgwork.file.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgwork.file.base.BaseResult;
import com.lgwork.file.domain.dto.FileUploadRespDTO;
import com.lgwork.file.service.FileStorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;



/**
 * 文件上传控制
 * @author irays
 *
 */
@Api(tags= {"文件上传控制器"})
@RequestMapping("/file/upload")
@Controller
public class FileUploadController {
	
	
	/**
	 * 文件储存服务接口
	 */
	@Autowired
	private FileStorageService fileStorageService;
	
	/**
	 * 持久化类
	 */
//	@Autowired
//	private FilePersistLocalService  filePersistLocalService;
	
	
	/**
	 * @exception
	 * Required request part 'file' is not present  文件必须存在
	 * 
	 * 单个文件上传
	 * @param req
	 * @param name
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "单个文件上传", notes="单个文件上传, {name} 与 input name={name}一致")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "文件名", required = true, dataType = "string", paramType="path", defaultValue="file"),
		@ApiImplicitParam(name = "code", value = "业务代码", required = true, dataType = "string", paramType="path"),
		@ApiImplicitParam(name = "file", value = "上传文件", required = true, dataType="__file", paramType="form")
	})
	@PostMapping(value="/single/{name}/{code}", headers="content-type=multipart/form-data")
	@ResponseBody
	public BaseResult<FileUploadRespDTO> uploadSingleFile(
			@ApiIgnore  MultipartHttpServletRequest req, 
			@PathVariable("name") String name,
			@PathVariable(value = "code") String code) throws Exception {

		// 获取文件
		MultipartFile file = req.getFile(name);

		// 上传单个文件
		BaseResult<FileUploadRespDTO> result = 
				fileStorageService.uploadSingleFile(file, code);

		return result;
	}
	
	
	
	/**
	 * 多个文件上传
	 * @param req
	 * @param name
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "多个文件上传", notes="多个文件上传")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "业务代码", required = true, dataType = "string", paramType="path"),
		@ApiImplicitParam(name = "files[]", value = "上传文件", required = true, dataType="__file", paramType="form")
	})
	@PostMapping(value="/multiple/files/{code}", headers="content-type=multipart/form-data")
	@ResponseBody
	public BaseResult<List<FileUploadRespDTO>> uploadMultipleFile(
			@ApiIgnore MultipartHttpServletRequest req, 
			@PathVariable("code") String code) throws Exception {

		// 获取所有上传的文件
		List<MultipartFile> files = req.getFiles("files[]");
		
		Map<String, MultipartFile> fileMap = req.getFileMap();
		System.err.println(fileMap);

//		处理文件上传
		BaseResult<List<FileUploadRespDTO>> result = 
				fileStorageService.uploadMultipleFile(files, code);

		return result;
	}
	
	
	
	/**
	 * 文件删除
	 * 注: 
	 * 文件启用后无法使用此方法删除
	 * @param code 为文件编码
	 * @return
	 */
	@ApiOperation(value = "文件删除接口", notes="文件删除接口")
	@DeleteMapping("/remove/{code}")
	@ResponseBody
	public BaseResult<String>  deleteUploadFileByCode(@PathVariable("code") String code) {
		
		
		BaseResult<String> baseResult = 
				fileStorageService.deleteUploadFileByCode(code);
		
		return baseResult;
	}
	
	
	
	
	/**
	 * @RequestParam("file") 不要也可以, 为了api显示
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "form表单直接上传", notes="form表单单个文件直接上传")
	@PostMapping("/singleFormUpload.do")
	public ModelAndView singleFormUpload(
			@ApiParam @RequestParam("file") MultipartFile file,
			@ApiParam(value="业务代码") @RequestParam("code") String code,
			@ApiIgnore ModelAndView view
			) throws Exception {
		
		
		FileUploadRespDTO result = 
				fileStorageService.saveSingleFormUpload(file, code);
		
		view.addObject("result", result);
		view.setViewName("/home/demos/form/result/common_file_result1");
		
		return view;
	}
	
	
	
	
	/**
	 * 多个文件from表单上传
	 * @param files
	 * @param code
	 * @param view
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "业务代码", required = true, dataType = "string", paramType="path"),
		@ApiImplicitParam(name = "files[]", value = "上传文件", required = true, dataType="__file", paramType="form")
	})
	@ApiOperation(value = "form表单直接上传", notes="form表单多个文件直接上传")
	@PostMapping("/multipleFormUpload.do")
	public ModelAndView multipleFormUpload(
			@ApiParam @RequestParam("file[]") List<MultipartFile> files,
			@ApiParam(value="业务代码") @RequestParam("code") String code,
			@ApiIgnore ModelAndView view,
			@ApiIgnore HttpServletRequest req
			) throws Exception {
		
		List<FileUploadRespDTO> results = 
				fileStorageService.saveMultipleFormUpload(files, code);
		
		view.addObject("results", results);
		view.setViewName("/home/demos/form/result/common_file_result2");
		
		return view;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
