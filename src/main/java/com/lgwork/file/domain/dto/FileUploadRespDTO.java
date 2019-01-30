package com.lgwork.file.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 文件响应实体
 * @author irays
 *
 */
@ApiModel("文件上传返回结果")
@JsonInclude(Include.NON_NULL)
@Data
public class FileUploadRespDTO {
	
	/**
	 * 文件唯一标识符
	 */
	@ApiModelProperty(value="文件唯一标识符" , example="1215652608af44e69acb4ce1b8b4c1dc")
	private String code;
	/**
	 * 文件名称
	 */
	@ApiModelProperty(value="文件名称" , example="实例图片.png")
	private String name;
	/**
	 * 文件大小
	 */
	@ApiModelProperty(value="文件大小" , example="1MB, 1GB")
	private String size;
	/**
	 * 访问的url
	 */
	@ApiModelProperty(value="访问的url全路径" , example="//localhost:8080/stream/...")
	private String url;
	/**
	 * 缩略图
	 */
	@ApiModelProperty(value="缩略图访问的url全路径" , example="//localhost:8080/thumb/...")
	private String thumbnailUrl;
	/**
	 * 删除文件url, 文件生效无法使用
	 */
	@ApiModelProperty(value="删除文件url, 文件生效无法使用" , example="//localhost:8080/delete/...")
	private String deleteUrl;
	/**
	 * 删除接口访问的类型
	 */
	@ApiModelProperty(value="删除文件方式" , example="DELETE")
	private String deleteType;
	/**
	 * 状态
	 * ok
	 * error
	 */
	@ApiModelProperty(value="上传状态" , example="ok")
	private String status;
	/**
	 * 返回的错误信息(status=error)
	 */
	@ApiModelProperty(value="上传错误信息" , example="文件过大限制1mb")
	private String errmsg;

}
