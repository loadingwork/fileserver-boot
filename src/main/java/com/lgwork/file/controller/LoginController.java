package com.lgwork.file.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;


/**
 * 登录控制器
 * @author irays
 *
 */
@Api(tags= {"登录控制器"})
@Controller
public class LoginController {
	
	
	/**
	 * 去登录页面
	 * @return
	 */
	@GetMapping("/login.do")
	public String toLogin() {
		
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			return "redirect:/home.do";
		}
		
		
		
		return "/login";
	}
	
	/**
	 * 登录操作
	 * @return
	 */
	@PostMapping("/login.do")
	public String loginSubmit(HttpServletRequest req, ModelMap modal) {
		
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
//			登录成功了
			return "redirect:/home.do";
		}
		
		String username = req.getParameter("username");
		modal.addAttribute("username", username);
		
		String errClassName = (String) req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		
		// 登陆出现错误
		if (Objects.equals(UnknownAccountException.class.getName(), errClassName)) {
			modal.addAttribute("error", "账号异常");
		} else if(Objects.equals(IncorrectCredentialsException.class.getName(), errClassName)) {
			modal.addAttribute("error", "密码错误");
		} else if(Objects.equals(LockedAccountException.class.getName(), errClassName)) {
			modal.addAttribute("error", "账号被锁定");
		} else if(Objects.equals(AuthenticationException.class.getName(), errClassName)) {
			modal.addAttribute("error", "登录错误请联系管理员");
		} else {
			modal.addAttribute("error", "登录错误请联系管理员");
		}
		
		return "/login";
	}
	
	
	/**
	 * 请求403
	 * @return
	 */
	@RequestMapping("/403.do")
	public String err403() {
		return "/403";
	}
	
	/**
	 * 404
	 * @return
	 */
	@RequestMapping("/404.do")
	public String err404() {
		return "/404";
	}
	
	/**
	 * 500
	 * @return
	 */
	@RequestMapping("/500.do")
	public String err500() {
		return "/500";
	}
	
	@RequestMapping("/home.do")
	public String home() {
		return "/home/index";
	}
	

}
