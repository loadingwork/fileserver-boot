package com.lgwork.file.consts;

import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;


/**
 * 文件常量
 * @author irays
 *
 */
public class FileConst {
	
	/**
	 * 预览文件类型
	 */
	public static List<String> THUMB_LIST = Lists.newArrayList("jpg", "png", "jpeg", "gif");
	
	/**
	 * 文件名称正则
	 */
	public static Pattern FILE_FOLDER_NAME = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9_-]{1,31}$");

}
