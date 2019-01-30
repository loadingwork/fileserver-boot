package com.lgwork.file.mapper;

import com.lgwork.file.domain.po.FileStoragePO;

import tk.mybatis.mapper.common.Mapper;


/**
 * 文件持久化接口
 * @author irays
 *
 */
public interface FileStorageMapper extends Mapper<FileStoragePO> {
	
	
	/**
	 * 根据文件编码获取信息
	 * @param fileCode
	 * @return
	 */
	FileStoragePO selectByFileCode(String fileCode);
	

}
