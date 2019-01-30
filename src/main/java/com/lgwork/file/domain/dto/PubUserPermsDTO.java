package com.lgwork.file.domain.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.ToString;

/**
 * 权限集合体, 用户信息
 * @author irays
 *
 */
@ToString
@Getter
public class PubUserPermsDTO {
	
	private Long id;
	/**
	 * 编码
	 */
	private String ucode;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 角色集合
	 */
	private Set<String> roles = new HashSet<>();    
	/**
	 * 权限集合
	 */
    private Set<String> perms = new HashSet<>();
    
    
    public PubUserPermsDTO() {
	}
    
	public PubUserPermsDTO(
			Long id, String ucode, 
			String username, String nickname, 
			Set<String> roles,
			Set<String> perms) {
		this.id = id;
		this.ucode = ucode;
		this.username = username;
		this.nickname = nickname;
		this.roles = roles;
		this.perms = perms;
	}

    
    

}
