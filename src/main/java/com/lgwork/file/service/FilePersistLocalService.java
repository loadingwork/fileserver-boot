package com.lgwork.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.lgwork.file.domain.dto.FileUploadRespDTO;

/**
 * 持久化本地文件
 * @author irays
 *
 */
public interface FilePersistLocalService {
	
	
	
	/**
	 * 单个文件上传
	 * @param file  上传的文件
	 * @param code  文件分类编码或者sysFiles中的key 主要作用是用来校验
	 * @param saveType  储存类型  @TODO  对接云盘
	 * @param path  二级路径  如  /temp /root/aaa  
	 * @param enabled  是否启用 默认为false 
	 * @return
	 * @throws Exception
	 */
	FileUploadRespDTO saveSingleFile(MultipartFile file, String code, String saveType, String path, boolean enabled) throws Exception;
	
	

}
