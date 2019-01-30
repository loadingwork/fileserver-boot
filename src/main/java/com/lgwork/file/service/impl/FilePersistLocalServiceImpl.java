package com.lgwork.file.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgwork.file.config.FileConfigProperties;
import com.lgwork.file.consts.FileConst;
import com.lgwork.file.domain.dto.FileUploadRespDTO;
import com.lgwork.file.domain.po.FileStoragePO;
import com.lgwork.file.mapper.FileStorageMapper;
import com.lgwork.file.service.FilePersistLocalService;
import com.lgwork.file.util.UuidUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * 持久化文件到本地
 * 注: 这个类只处理文件
 * @author irays
 *
 */
@Slf4j
@Service
public class FilePersistLocalServiceImpl implements FilePersistLocalService {

	
	/**
	 * 文件配置
	 */
	@Autowired
	private FileConfigProperties fileConfigProperties;
	/**
	 * 文件持久化接口
	 */
	@Autowired
	private FileStorageMapper fileStorageMapper;
	
	/**
	 * 每次开启一个新事物
	 */
	@Transactional(rollbackFor=Exception.class,  propagation=Propagation.REQUIRES_NEW)
	@Override
	public FileUploadRespDTO saveSingleFile(MultipartFile file, String code, 
			String saveType, String path, boolean enabled) throws Exception {
		
		if (file == null) {
			throw new RuntimeException("MultipartFile文件不存在");
		}
		
		if (StringUtils.isEmpty(code)) {
			throw new RuntimeException("编码为空");
		}
		
		if (StringUtils.isEmpty(path)) {
			throw new RuntimeException("二级路径为空");
		}
		
//		--- 
//		获取文件名
		final String originalFilename = file.getOriginalFilename();
//		 获取文件后缀
		final String ext = FilenameUtils.getExtension(originalFilename);
		final String fileCode = UuidUtils.uuid32();
		final Map<String, String> sysFiles = fileConfigProperties.getSysFiles();
		final String storageType = fileConfigProperties.getStorageType();
		// 保存的名称
		final String storageName = fileCode + "." + ext;
		// 文件大小
		final long fileSize = file.getSize();
		
		if (StringUtils.isEmpty(storageType)) {
			throw new RuntimeException("sysFileType没有配置");
		}
		
		String validateFiles = validateFiles(file, code);
		if (!"ok".equals(validateFiles)) {
			// 验证文件错误
			log.debug("验证文件错误: {}", validateFiles);
		}
		
		
		// 获取文件key
		final String fileKey = fileConfigProperties.getsFileKey(path, storageName);
		
//		保存的文件对象
		FileStoragePO fileStoragePO = new FileStoragePO();
		
		if(sysFiles != null 
				&& StringUtils.isNotEmpty(sysFiles.get(code))) {
			// 系统文件
			fileStoragePO.setStorageType(storageType);
			
		} else {
			if (StringUtils.isEmpty(saveType)) {
				throw new RuntimeException("storageType is empty");
			}
			// 分类文件
			fileStoragePO.setStorageType(saveType);
		}
		
		fileStoragePO.setCategoryCode(code);
		
		// 设置表信息
		DateTime dt = new DateTime();
		
		fileStoragePO.setGmtCreate(dt.toDate());
		fileStoragePO.setDeleted(false);
		fileStoragePO.setGmtModified(dt.toDate());
		
//		一般FileCode 
		fileStoragePO.setFileCode(fileCode);
//		fileStoragePO.setCategoryCode(categoryCode);
		fileStoragePO.setFileKey(fileKey);
		fileStoragePO.setDescription("");
		fileStoragePO.setOriginalName(originalFilename);
		fileStoragePO.setExt(ext);
		fileStoragePO.setFileSize(fileSize);
		fileStoragePO.setStorageName(storageName);
		
		// 增加一个小时
		DateTime tempExpiresAt = dt.plusHours(1);
		
		fileStoragePO.setTempExpiresAt(tempExpiresAt.toDate());
		fileStoragePO.setCreateBy("");
		fileStoragePO.setEnabled(enabled);
		
		// 保存数据
		fileStorageMapper.insert(fileStoragePO);
		
		// 保存文件
		String fileAbsolutePath = fileConfigProperties.getsAbsolutePath(fileKey);
		
		// 获取文件
		File fileAbsolutePathFile = new File(fileAbsolutePath);
		
		// 创建文件父级目录
		if (!fileAbsolutePathFile.exists()) {
			File parentFile = fileAbsolutePathFile.getParentFile();
			// 文件不存在
			if (parentFile !=  null &&
					!parentFile.exists()) {
				// 创建路径
				parentFile.mkdirs();
			}
		}
		
		
		// 获取输入流
		InputStream inputStream = file.getInputStream();
		
		// 保存文件
		FileUtils.copyInputStreamToFile(inputStream, fileAbsolutePathFile);
		
		FileUploadRespDTO fileUploadRespDTO = new FileUploadRespDTO();
		
		// 设置访问路径  root + {code}.ext
		String url = fileConfigProperties.getsPublicUrl(fileKey);
		String deleteUrl = fileConfigProperties.getsDeleteUrl(fileCode);
		String thumbnailUrl = "";
		if (FileConst.THUMB_LIST.contains(ext)) {
			thumbnailUrl = fileConfigProperties.getsThumbnailUrl(fileKey);
		}
		
		fileUploadRespDTO.setCode(fileCode);
		fileUploadRespDTO.setName(originalFilename);
		// 文件显示大小
		fileUploadRespDTO.setSize(FileUtils.byteCountToDisplaySize(fileSize));
		fileUploadRespDTO.setUrl(url);
		fileUploadRespDTO.setThumbnailUrl(thumbnailUrl);
		fileUploadRespDTO.setDeleteUrl(deleteUrl);
		fileUploadRespDTO.setDeleteType(fileConfigProperties.getDeleteType());
		fileUploadRespDTO.setStatus("ok");
		fileUploadRespDTO.setErrmsg("上传成功");
		
		if (inputStream != null) {
			inputStream.close();
		}
		
		return fileUploadRespDTO;
	}
	
	
	
	/**
	 * 不是ok都是错误信息
	 * @param file
	 * @param code
	 * @return
	 */
	public String validateFiles(MultipartFile file, String code) {
		
		
		return "ok";
	}

	
	
	
	

}
