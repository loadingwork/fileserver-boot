package com.lgwork.file.mapper;

import org.apache.ibatis.annotations.Param;

import com.lgwork.file.domain.po.PubUserPO;

import tk.mybatis.mapper.common.Mapper;


/**
 * 用户持久化接口
 * @author irays
 *
 */
public interface PubUserMapper extends Mapper<PubUserPO> {
	
	
	PubUserPO selectByUsername(@Param("username") String username);
	

}
