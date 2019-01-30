package com.lgwork.file.domain.po;

import javax.persistence.Column;
import javax.persistence.Table;

import com.lgwork.file.base.BasePO;

import lombok.Getter;
import lombok.Setter;


/**
 * 角色表
 * @author irays
 *
 */
@Table(name = "sys_role")
@Getter
@Setter
public class SysRolePO extends BasePO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	@Column(name="role_code")
	private String roleCode;
	/**
	 * 角色名
	 */
	private String  name;
	/**
	 * 描述
	 */
	private String describtion;
	/**
	 * 备注
	 */
	private String remarks;
	
	

}
