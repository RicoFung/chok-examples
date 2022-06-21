package com.domain.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domain.erp.dao.TbUserInfo0aDao;
import com.domain.erp.entity.TbUserInfo0a;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service("tbUserInfo0aService")
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
		TbUserInfo0a tbUser = dao.getByUsername(username);
		if (tbUser == null)
		{
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		return User.builder().username(tbUser.getTcCode()).password(tbUser.getTcPassword()).roles("ADMIN")
				.build();
	}
}
