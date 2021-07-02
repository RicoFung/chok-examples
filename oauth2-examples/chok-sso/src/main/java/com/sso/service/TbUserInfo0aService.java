package com.sso.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sso.dao.TbUserInfo0aDao;
import com.sso.entity.OauthAuthority;
import com.sso.entity.OauthUser;
import com.sso.entity.TbUserInfo0a;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service
public class TbUserInfo0aService extends BaseService<TbUserInfo0a,Long> implements UserDetailsService
{
	@Autowired
	private TbUserInfo0aDao dao;

	@Override
	public BaseDao<TbUserInfo0a,Long> getEntityDao() 
	{
		return dao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		OauthUser u = getByUsername(username);
		if (u == null)
		{
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		return new User(u.getUsername(), u.getPassword(), u.getAuthorities());
	}
	
	public OauthUser getByUsername(String username)
	{
		OauthUser oUser = null;
		TbUserInfo0a tbUser = dao.getByUsername(username);
		if (null != tbUser)
		{
			oUser = new OauthUser();
			oUser.setUsername(tbUser.getTcCode());
			oUser.setPassword(tbUser.getTcPassword());
			List<OauthAuthority> authorities = new ArrayList<OauthAuthority>();

			authorities.add(new OauthAuthority(tbUser.getTcCode(), "ADMIN"));
			oUser.setAuthorities(authorities);
		}
		return oUser;
	}
}
