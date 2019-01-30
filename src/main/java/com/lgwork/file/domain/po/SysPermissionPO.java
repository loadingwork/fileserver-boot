package com.lgwork.file.domain.po;

import javax.persistence.Column;
import javax.persistence.Table;

import com.lgwork.file.base.BasePO;

import lombok.Getter;
import lombok.Setter;



/**
 * 权限表
 * @author irays
 *
 */
@Table(name = "sys_permission")
@Getter
@Setter
public class SysPermissionPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 权限编码
	 */
	@Column(name="sys_permission_code")
	private String sysPermissionCode;
	
	/**
	 * 权限描述
	 */
	@Column(name="permission_desc")
	private String permissionDesc;
	
	/**
	 * 权限名称
	 */
	private String name;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	

}
