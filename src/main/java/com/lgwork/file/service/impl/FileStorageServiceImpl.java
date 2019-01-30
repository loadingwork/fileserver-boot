package com.lgwork.file.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgwork.file.base.BaseResult;
import com.lgwork.file.config.FileConfigProperties;
import com.lgwork.file.domain.dto.FileUploadRespDTO;
import com.lgwork.file.domain.po.FileCategoryPO;
import com.lgwork.file.domain.po.FileStoragePO;
import com.lgwork.file.mapper.FileCategoryMapper;
import com.lgwork.file.mapper.FileStorageMapper;
import com.lgwork.file.service.FilePersistLocalService;
import com.lgwork.file.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;



/**
 * 文件储存服务实现
 * @author irays
 *
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	
	/**
	 * 文件分类持久化接口
	 */
	@Autowired
	private FileCategoryMapper fileCategoryMapper;
	/**
	 * 上传配置
	 */
	@Autowired
	private FileConfigProperties fileConfigProperties;
	/**
	 * 上传配置
	 */
	@Autowired
	private FilePersistLocalService filePersistLocalService;
	
	
	/**
	 * 文件持久化接口
	 */
	@Autowired
	private FileStorageMapper fileStorageMapper;

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<FileUploadRespDTO> uploadSingleFile(MultipartFile file, String code) throws Exception {
		
		BaseResult<FileUploadRespDTO> baseResult = new BaseResult<FileUploadRespDTO>();
		
		Map<String, String> sysFiles = fileConfigProperties.getSysFiles();
		if (sysFiles == null) {
			throw new RuntimeException("请管理员配置参数");
		}
		
		if (file == null) {
			log.debug("上传文件不存在");
			baseResult.setErrcode("1");
			baseResult.setErrmsg("上传文件不存在");
			return baseResult;
		}
		
		// 获取系统路径
		String path = sysFiles.get(code);
		String storageType = null;
		if (StringUtils.isEmpty(path)) {
			// 获取分类中的文件
			FileCategoryPO dbFileCategoryPO = fileCategoryMapper.selectByCode(code);
			if (dbFileCategoryPO == null) {
				log.debug("业务代码不存在, 无法上传");
				baseResult.setErrcode("1");
				baseResult.setErrmsg("业务代码不存在, 无法上传");
				return baseResult;
			}
//			@TODO	增加路径字段
			path = "/" + code;
			storageType = dbFileCategoryPO.getStorageType();
		}
		
		// 检查路径
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		
		
		FileUploadRespDTO saveSingleFile = 
				filePersistLocalService.saveSingleFile(file, code, storageType, path, false);
		
		baseResult.setErrcode("0");
		baseResult.setErrmsg("success");
		baseResult.setData(saveSingleFile);
		
		return baseResult;
	}

	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<List<FileUploadRespDTO>> uploadMultipleFile(List<MultipartFile> files, String code)
			throws Exception {
		
		BaseResult<List<FileUploadRespDTO>> baseResult = new BaseResult<>();
		
		Map<String, String> sysFiles = fileConfigProperties.getSysFiles();
		if (sysFiles == null) {
			// 重量级bug抛出
			throw new RuntimeException("请管理员配置参数");
		}
		
		if (files == null || files.isEmpty()) {
			log.debug("上传文件不存在");
			baseResult.setErrcode("1");
			baseResult.setErrmsg("上传文件不存在");
			return baseResult;
		}
		
		// 获取系统路径
		String path = sysFiles.get(code);
		String storageType = null;
		if (StringUtils.isEmpty(path)) {
			// 获取分类中的文件
			FileCategoryPO dbFileCategoryPO = fileCategoryMapper.selectByCode(code);
			if (dbFileCategoryPO == null) {
				log.debug("业务代码不存在, 无法上传");
				baseResult.setErrcode("1");
				baseResult.setErrmsg("业务代码不存在, 无法上传");
				return baseResult;
			}
//			@TODO	增加路径字段
			path = "/" + code;
			storageType = dbFileCategoryPO.getStorageType();
		}
		
		// 检查路径
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		
		
		List<FileUploadRespDTO> filesResult = new ArrayList<FileUploadRespDTO>();
		for(MultipartFile  file: files) {
			
			try {
				FileUploadRespDTO uploadResult = 
						filePersistLocalService.saveSingleFile(file, code, storageType, path, false);
				filesResult.add(uploadResult);
			} catch (Exception e) {
				FileUploadRespDTO errUploadResult = new FileUploadRespDTO();
				errUploadResult.setStatus("error");
				errUploadResult.setErrmsg("系统错误, 请联系管理员");
				filesResult.add(errUploadResult);
			}
			
		}
		
		
		
		baseResult.setErrcode("0");
		baseResult.setErrmsg("success");
		baseResult.setData(filesResult);
		
		return baseResult;
	}


	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public BaseResult<String> deleteUploadFileByCode(String fileCode) {
		
		BaseResult<String> baseResult = new BaseResult<>();
		
		if (StringUtils.isEmpty(fileCode)) {
			baseResult.setErrcode("1");
			baseResult.setErrmsg("code缺失");
			return baseResult;
		}
		
//		根据code获取数据
		FileStoragePO dbFileStoragePO = fileStorageMapper.selectByFileCode(fileCode);
		
		if (dbFileStoragePO == null) {
			baseResult.setErrcode("1");
			baseResult.setErrmsg("文件不存在");
			return baseResult;
		}
		
		if (dbFileStoragePO.getEnabled()) {
			baseResult.setErrcode("1");
			baseResult.setErrmsg("文件已经启用, 请到管理界面删除");
			return baseResult;
		}
		
		// 执行删除操作
		fileStorageMapper.delete(dbFileStoragePO);
		
		baseResult.setErrcode("0");
		baseResult.setErrmsg("操作成功");
		return baseResult;
	}


	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public FileUploadRespDTO saveSingleFormUpload(MultipartFile file, String code) throws Exception {
		
		if (file == null) {
			throw new RuntimeException("file 不存在");
		}
		
		if (StringUtils.isEmpty(code)) {
			throw new RuntimeException("code 不存在");
		}
		
		Map<String, String> sysFiles = fileConfigProperties.getSysFiles();
		if (sysFiles == null) {
			// 重量级bug抛出
			throw new RuntimeException("请管理员配置参数");
		}
		
		String path = sysFiles.get(code);
		String storageType = null;
		if (StringUtils.isEmpty(path)) {
			// 根据分类查询
			// 获取分类中的文件
			FileCategoryPO dbFileCategoryPO = fileCategoryMapper.selectByCode(code);
			if (dbFileCategoryPO == null) {
				throw new RuntimeException("业务代码不存在, 无法上传");
			}
//						@TODO	增加路径字段
			path = "/" + code;
			storageType = dbFileCategoryPO.getStorageType();
		}
		
		
		FileUploadRespDTO uploadResult = 
				filePersistLocalService.saveSingleFile(file, code, storageType, path, true);
		
		return uploadResult;
	}


	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<FileUploadRespDTO> saveMultipleFormUpload(List<MultipartFile> files, String code) {
		
		if (files == null || files.isEmpty()) {
			throw new RuntimeException("files 不存在");
		}
		
		if (StringUtils.isEmpty(code)) {
			throw new RuntimeException("code 不存在");
		}
		
		Map<String, String> sysFiles = fileConfigProperties.getSysFiles();
		if (sysFiles == null) {
			// 重量级bug抛出
			throw new RuntimeException("请管理员配置参数");
		}
		
		String path = sysFiles.get(code);
		String storageType = null;
		if (StringUtils.isEmpty(path)) {
			// 根据分类查询
			// 获取分类中的文件
			FileCategoryPO dbFileCategoryPO = fileCategoryMapper.selectByCode(code);
			if (dbFileCategoryPO == null) {
				throw new RuntimeException("业务代码不存在, 无法上传");
			}
//						@TODO	增加路径字段
			path = "/" + code;
			storageType = dbFileCategoryPO.getStorageType();
		}
		
		List<FileUploadRespDTO> filesResult = new ArrayList<>();
		
		for(MultipartFile file : files) {
			
			try {
				FileUploadRespDTO uploadResult = 
						filePersistLocalService.saveSingleFile(file, code, storageType, path, true);
				filesResult.add(uploadResult);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				FileUploadRespDTO errUploadResult = new FileUploadRespDTO();
				errUploadResult.setStatus("error");
				errUploadResult.setErrmsg("系统错误, 请联系管理员");
				filesResult.add(errUploadResult);
			}
			
		}
		
		return filesResult;
	}

}
