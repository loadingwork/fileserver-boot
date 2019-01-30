package com.lgwork.file.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgwork.file.base.BaseResult;

import lombok.extern.slf4j.Slf4j;


/**
 * 异常控制器
 * @author irays
 *
 */
@Slf4j
@ControllerAdvice("com.lgwork.file.controller")
public class GlobalExceptionAdvice {
	
	
	
	/**
	 * 处理全局异常
	 * 一定要有404 uri
	 * @param req
	 * @param resp
	 * @param e
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
    public void handleException(
    		HttpServletRequest req,
    		HttpServletResponse resp,
    		Exception e)  {
		
		log.error(e.getMessage(), e);
		
		try {
			
			String requestURI = req.getRequestURI();
			
			if (requestURI != null 
					&& requestURI.contains(".do")) {
				// 跳转到500页面
				req.getRequestDispatcher("/500.do").forward(req, resp);
				return;
			}
			
			// 返回json
			BaseResult<String> result = new BaseResult<>();
			
			String jsonResult = "";
			
			ObjectMapper mapper = new ObjectMapper();
			
			result.setErrcode("-1");
			result.setErrmsg(e.getMessage());
			
			jsonResult = mapper.writeValueAsString(result);
			
			PrintWriter writer = resp.getWriter();
			
			writer.write(jsonResult);
			writer.close();
			return;
		} catch (Exception e1) {
			log.error(e1.getMessage(), e1);
		}
		
    }
	

}
