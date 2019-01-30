package com.lgwork.file.config;

import java.io.Serializable;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.lgwork.file.domain.dto.PubUserPermsDTO;
import com.lgwork.file.domain.po.PubUserPO;
import com.lgwork.file.mapper.PubUserMapper;

/**
 * 用户认证与授权
 * @author irays
 *
 */
public class UserRealm extends AuthorizingRealm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户持久化接口
	 */
	private final PubUserMapper pubUserMapper;

	public UserRealm(PubUserMapper pubUserMapper) {
		this.pubUserMapper = pubUserMapper;
	}


	public PubUserMapper getPubUserMapper() {
		return pubUserMapper;
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token != null && UsernamePasswordToken.class.isAssignableFrom(token.getClass());
	}


	/**
	 * 授权接口
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        
        PubUserPermsDTO user = (PubUserPermsDTO) getAvailablePrincipal(principals);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getPerms());
		
		return info;
	}

	
	/**
	 * 认证接口
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		
		// 获取用户名
		String username = (String) usernamePasswordToken.getPrincipal();
		
		if (StringUtils.isEmpty(username)) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		
		PubUserPO dbPubUserPO = pubUserMapper.selectByUsername(username);
		
		if (dbPubUserPO == null) {
			throw new UnknownAccountException("No account found for admin [" + username + "]");
		}
		
		
		PubUserPermsDTO pubUserPermsDTO = 
				new PubUserPermsDTO(dbPubUserPO.getId(), dbPubUserPO.getUcode(), username, 
						dbPubUserPO.getNickname(), new HashSet<String>(), new HashSet<String>());
		
//		授权信息
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(pubUserPermsDTO, dbPubUserPO.getPassword(), getName());
        if (StringUtils.isNotEmpty(dbPubUserPO.getSalt())) {
            info.setCredentialsSalt(ByteSource.Util.bytes(dbPubUserPO.getSalt()));
        }

        return info;
	}
	
	
	
	

}
