package com.lgwork.file.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.JdbcType;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;
import tk.mybatis.mapper.code.IdentityDialect;

/**
 * 
 * @MappedSuperclass 不能有@entity @table注解, 属性映射到子类中
 * 
 * @author irays
 *
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BasePO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *   主键自增
	 */
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@KeySql(
			useGeneratedKeys=true,
			dialect = IdentityDialect.MYSQL
			)
	@Column(name = "id", nullable = false)
	private Long id;
	
	/**
	 * 创建时间
	 */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_create", nullable=false)
	private Date gmtCreate;
	
	/**
	 * 修改时间
	 */
	@Column(name="gmt_modified")
	private Date gmtModified;
	
	/**
	 * 是否删除
	 * 开启逻辑删除
	 */
	@LogicDelete
	@ColumnType(column = "is_deleted", jdbcType = JdbcType.TINYINT)
	private Boolean deleted;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[0]);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, new String[0]);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
