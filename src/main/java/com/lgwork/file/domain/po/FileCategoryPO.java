package com.lgwork.file.domain.po;

import javax.persistence.Column;
import javax.persistence.Table;

import com.lgwork.file.base.BaseTreePO;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件分类表
 * @author irays
 *
 */
@Table(name = "file_category")
@Setter
@Getter
public class FileCategoryPO extends BaseTreePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * system 本地
	 * local  数据库
	 */
	@Column(name="storage_type")
	private String storageType;
	/**
	 * 备注
	 */
	private String remarks;
	

}
