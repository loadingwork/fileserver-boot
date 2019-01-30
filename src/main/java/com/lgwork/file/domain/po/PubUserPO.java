package com.lgwork.file.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ibatis.type.JdbcType;

import com.lgwork.file.base.BasePO;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.ColumnType;


/**
 * 用户实体
 * @author irays
 *
 */
@Table(name = "pub_user")
@Getter
@Setter
public class PubUserPO extends BasePO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编码
	 */
	private String ucode;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 盐值
	 */
	private String salt;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 是否可用
	 */
	@ColumnType(column = "is_enabled", jdbcType = JdbcType.TINYINT)
	private Boolean enabled;
	/**
	 * 最后登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login_time", nullable=false)
	private Date lastLoginTime;
	

}
