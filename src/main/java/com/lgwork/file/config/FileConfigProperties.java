package com.lgwork.file.config;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 文件配置
 * @author irays
 *
 */
@Component
@Slf4j
@Data
@ConfigurationProperties(prefix = "files")
public class FileConfigProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件根路径
	 */
	private String root;
	/**
	 * 缩略图路径
	 */
	private String thumbnailRoot;
	/**
	 * 文件访问地址
	 */
	private String url;
	/**
	 * 缩略图
	 */
	private String thumbnailUrl;
	/**
	 * 删除的url
	 * 文件生效无法使用
	 */
	private String deleteUrl;
	/**
	 * 删除接口访问的类型
	 */
	private String deleteType;
	/**
	 * 储存二级位置
	 * 实际系统配置文件
	 */
	private Map<String, String>  sysFiles;
	/**
	 * 系统
	 */
	private String storageType;
	
	/**
	 * 获取文件key
	 * @param second  二级路径
	 * @param storageName  储存的名称, 如果没有使用uuid代替
	 * @return
	 */
	public String getsFileKey(String second, String storageName) {
		
		DateTime dt = new DateTime();
		// 获取年份
		int year = dt.getYear();
		int monthOfYear = dt.getMonthOfYear();
		
		// 构建文件key
		StringBuffer key = new StringBuffer();
		// 保证开头都是/
		key.append("/root");
		key.append(second);
		key.append("/");
		key.append(year);
		key.append("/");
		key.append(monthOfYear);
		key.append("/");
		
		if (StringUtils.isEmpty(storageName)) {
			throw new RuntimeException("storageName异常"); 
		}
		
		key.append(storageName);
		
		String result = key.toString();
		
		// 路径格式化
		result = FilenameUtils.normalizeNoEndSeparator(result, true);
		log.debug("实际路径:  {}", result);
		if (result == null) {
			throw new RuntimeException("路径格式化异常");
		}
		
		return result;
	}
	
	/**
	 * 获取文件绝对路径
	 * @param fileKey
	 * @return
	 */
	public String getsAbsolutePath(String fileKey) {
		if (StringUtils.isEmpty(fileKey)) {
			new RuntimeException("fileKey不允许为空");
		}
		return root + fileKey;
	}
	
	/**
	 * 获取绝对路径
	 * @param width  宽度
	 * @param height  高度
	 * @param fileKey
	 * @return
	 */
	public String getsAbsoluteThumbPath(int width, int height ,String fileKey) {
		return thumbnailRoot + "/" + width + "x" + height + fileKey;
	}
	
	
	/**
	 * 获取文件访问的绝对地址
	 * @param fileKey  
	 * @return
	 */
	public String getsPublicUrl(String fileKey) {
		return this.url + fileKey;
	}
	
	/**
	 * 获取预览图地址
	 * @param fileKey
	 * @return
	 */
	public String getsThumbnailUrl(String fileKey) {
		return this.thumbnailUrl + fileKey;
	}
	
	/**
	 * 删除地址
	 * @param fileKey
	 * @return
	 */
	public String getsDeleteUrl(String fileCode) {
		return this.deleteUrl + "/" + fileCode;
	}
	
	
	

}
