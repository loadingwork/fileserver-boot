package com.lgwork.file.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lgwork.file.mapper.PubUserMapper;

/**
 * shiro配置
 * @author irays
 *
 *url 控制认证
 *注解  控制权限
 *
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * 用户信息持久化接口
	 */
	@Autowired
	private PubUserMapper pubUserMapper;
	
	
	
	/**
	 * 密码匹配
	 * @return
	 */
	@Bean
	public CredentialsMatcher passwordMatcher() {
		
//		使用simpleHash
		HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
		hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		hashMatcher.setStoredCredentialsHexEncoded(true);
        hashMatcher.setHashIterations(1024);
        hashMatcher.setHashIterations(1);
		
        
		return hashMatcher;
	}
	
	/**
	 * 用户认证与授权
	 * @return
	 */
	@Bean
	public Realm realm() {
		UserRealm userRealm = new UserRealm(pubUserMapper);
		
		userRealm.setCredentialsMatcher(passwordMatcher());
		
		return userRealm;
	}
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
	    
	    // 用户需要登录
	    chainDefinition.addPathDefinition("/favicon.ico", "anon");
	    chainDefinition.addPathDefinition("/static/**", "anon");
	    chainDefinition.addPathDefinition("/webjars/**", "anon");
	    chainDefinition.addPathDefinition("/**/*.js", "anon");
	    chainDefinition.addPathDefinition("/**/*.css", "anon");
	    chainDefinition.addPathDefinition("/**", "authc");
	    
	    return chainDefinition;
	}
	
	
	

}
