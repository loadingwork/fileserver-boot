package com.lgwork.file.mapper;

import com.lgwork.file.domain.po.FileCategoryPO;

import tk.mybatis.mapper.common.Mapper;


/**
 * 文件分类持久化接口
 * @author irays
 */
public interface FileCategoryMapper extends Mapper<FileCategoryPO> {
	
	
	/**
	 * 根据code查询
	 * @param code
	 * @return
	 */
	FileCategoryPO selectByCode(String code);
	

}
