package com.lgwork.file.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgwork.file.config.FileConfigProperties;
import com.lgwork.file.domain.po.FileStoragePO;
import com.lgwork.file.mapper.FileStorageMapper;
import com.lgwork.file.util.HttpRespHeaderUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;


/**
 * java文件访问服务器
 * @author irays
 *
 */
@CrossOrigin
@Slf4j
@Api(tags= {"java文件访问服务器"})
@RequestMapping("/fileserver")
@Controller
public class FileServerController {
	
	/**
	 * 文件没有找到
	 */
	private final String FILE_NOT_FIND = "/static/404/file.png";
	/**
	 *  缩略图404
	 */
	private final String THUMB_NOT_FIND = "/static/404/thumb.png";
	
	/**
	 * 文件持久化接口
	 */
	@Autowired
	private FileStorageMapper fileStorageMapper;
	
	/**
	 * 上传配置
	 */
	@Autowired
	private FileConfigProperties fileConfigProperties;
	
	/**
	 * 单个文件下载接口
	 * @param fileKey 文件名 实际就是文件code加上后缀
	 * @throws Exception 
	 */
	@ApiOperation(value = "单个文件下载接口", notes="单个文件下载接口")
	@GetMapping("/stream/**/{fileKey}")
	public void downloadSingleFile(
			HttpServletResponse resp, 
			@PathVariable("fileKey") String fileKey) throws Exception {
		
		ServletOutputStream output = resp.getOutputStream();
		
		if (StringUtils.isNotEmpty(fileKey)) {
			try {

				String fileCode = fileKey;
				int hasExt = fileKey.lastIndexOf(".");
				if (hasExt > -1) {
					// 有后缀
					fileCode = fileKey.substring(0, hasExt);
				}


				// 根据文件code获取
				FileStoragePO dbFileStoragePO = fileStorageMapper.selectByFileCode(fileCode);

				// 根据code获取文件
				if (dbFileStoragePO != null) {
					
					// 根据后缀添加不同的响应头
					// 添加请求头
					HttpRespHeaderUtils.addFileHeaders(resp, 
							dbFileStoragePO.getStorageName(), dbFileStoragePO.getExt());
					
					// 获取文件绝对路径
					String fileAbsolutePath = fileConfigProperties.getsAbsolutePath(dbFileStoragePO.getFileKey());

					FileInputStream in = new FileInputStream(fileAbsolutePath);

					BufferedInputStream bufferInput = new BufferedInputStream(in);

					// 下载显示
					IOUtils.copy(bufferInput, output);

					// 关闭流
					bufferInput.close();
					in.close();

					return;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}

		// 返回404页面
		HttpRespHeaderUtils.addFileHeaders(resp, "file404.png", "png");

		// 404处理
		InputStream in = FileServerController.class.getClass().getResourceAsStream(FILE_NOT_FIND);

		IOUtils.copy(in, output);

		// 关闭流
		output.flush();
		output.close();
		
	}
	
	
	/**
	 * 获取图片预览图
	 * 
	 * @param width 宽度
	 * @param heigh 高度
	 * @throws Exception
	 */
	@ApiOperation(value = "获取图片预览图", notes="获取图片预览图")
	@GetMapping("/thumb/**/{fileKey}")
	public void getSingleImagePreview(
			HttpServletResponse resp, @PathVariable("fileKey") String fileKey,
			@RequestParam(value = "width", defaultValue = "128") int width,
			@RequestParam(value = "height", defaultValue = "128") int height) throws Exception {
		


		ServletOutputStream output = resp.getOutputStream();

		if (StringUtils.isNotEmpty(fileKey)) {
			try {

				String fileCode = fileKey;
				int hasExt = fileKey.lastIndexOf(".");
				if (hasExt > -1) {
					// 有后缀
					fileCode = fileKey.substring(0, hasExt);
				}

				// 根据code查询
				FileStoragePO dbFileStoragePO = fileStorageMapper.selectByFileCode(fileCode);

				// 根据code获取文件
				if (dbFileStoragePO != null) {
					
					// 根据后缀添加不同的响应头
					// 添加请求头
					HttpRespHeaderUtils.addFileHeaders(resp, 
							dbFileStoragePO.getStorageName(), dbFileStoragePO.getExt());
					
					// 获取文件绝对路径
					String fileAbsolutePath = fileConfigProperties.getsAbsolutePath(dbFileStoragePO.getFileKey());

					// 缩略图路径
					String thumbAbsolutePath = fileConfigProperties.getsAbsoluteThumbPath(width, height, dbFileStoragePO.getFileKey());

					File thumbAbsolutePathFile = new File(thumbAbsolutePath);

					if (!thumbAbsolutePathFile.exists()) {
						File parentFile = thumbAbsolutePathFile.getParentFile();
						// 文件不存在
						if (parentFile != null && !parentFile.exists()) {
							// 创建路径
							parentFile.mkdirs();
						}

						// 压缩图片
						Thumbnails.of(fileAbsolutePath).size(width, height).toFile(thumbAbsolutePath);
					}

					// 获取临时图片地址
					BufferedImage im = ImageIO.read(thumbAbsolutePathFile);
					ImageIO.write(im, "png", output);

					// 关闭流
					im.flush();
					output.close();

					return;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		} 
		
		// 404处理
		InputStream in = FileServerController.class.getClass().getResourceAsStream(THUMB_NOT_FIND);
		// 返回404页面
		HttpRespHeaderUtils.addFileHeaders(resp, "thumb404.png", "png");
		
		try {
			
			// 404图片地址
			String thumb404AbsolutePath = fileConfigProperties.getsAbsoluteThumbPath(width, height, "/thumb404.png");
			
			File thumb404AbsolutePathFile = new File(thumb404AbsolutePath);
			if (!thumb404AbsolutePathFile.exists()) {
				File parentFile = thumb404AbsolutePathFile.getParentFile();
				// 文件不存在
				if (parentFile != null && !parentFile.exists()) {
					// 创建路径
					parentFile.mkdirs();
				}
				
				// 压缩图片
				Thumbnails.of(in).size(width, height).toFile(thumb404AbsolutePath);
			}
			
			// 获取临时图片地址
			BufferedImage im = ImageIO.read(thumb404AbsolutePathFile);
			ImageIO.write(im, "png", output);

			// 关闭流
			im.flush();
			output.close();
			return;
			
		} catch (Exception e) {
		}
		

		IOUtils.copy(in, output);
		
		// 关闭流
		output.flush();
		output.close();

		
	}
	
	
	

}
