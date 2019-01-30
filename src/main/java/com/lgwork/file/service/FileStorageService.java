package com.lgwork.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lgwork.file.base.BaseResult;
import com.lgwork.file.domain.dto.FileUploadRespDTO;

/**
 * 文件储存服务接口
 * @author irays
 *
 */
public interface FileStorageService {
	
	
	/**
	 * 单个文件上传
	 * @param file
	 * @param code
	 * @return
	 */
	BaseResult<FileUploadRespDTO> uploadSingleFile(MultipartFile file, String code) throws Exception;
	
	
	/**
	 * 多个文件上传
	 * @param files
	 * @param code
	 * @return
	 * @throws Exception
	 */
	BaseResult<List<FileUploadRespDTO>> uploadMultipleFile(List<MultipartFile> files, String code) throws Exception;
	
	
	/**
	 * 删除单个没有启用的文件
	 * @param code
	 * @return
	 */
	BaseResult<String> deleteUploadFileByCode(String fileCode);


	/**
	 * form上传文件
	 * @param file
	 * @param code
	 * @return
	 * @throws Exception
	 */
	FileUploadRespDTO saveSingleFormUpload(MultipartFile file, String code) throws Exception;


	/**
	 * form多个文件上传
	 * @param files
	 * @param code
	 * @return
	 */
	List<FileUploadRespDTO> saveMultipleFormUpload(List<MultipartFile> files, String code);
	

}
