package com.sso.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.sso.dao.TbUserInfo0aDao;
import com.sso.entity.OauthAuthority;
import com.sso.entity.OauthUser;
import com.sso.entity.TbUserInfo0a;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;
import me.zhyd.oauth.model.AuthUser;
import top.dcenter.ums.security.core.oauth.exception.RegisterUserFailureException;
import top.dcenter.ums.security.core.oauth.service.UmsUserDetailsService;

@Service
public class TbUserInfo0aService extends BaseService<TbUserInfo0a,Long> implements UmsUserDetailsService//UserDetailsService
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired(required = false)
    private UserCache userCache;
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

	@Override
	public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException
	{
        UserDetails userDetails = loadUserByUsername(userId);
        User.withUserDetails(userDetails);
        return User.withUserDetails(userDetails).build();
	}

    /**
     * {@link #existedByUsernames(String...)} usernames 生成规则.
     * 如需自定义重新实现此逻辑
     * @param authUser     第三方用户信息
     * @return  返回一个 username 数组
     */
    @Override
    public String[] generateUsernames(AuthUser authUser) {
        return new String[]{
                authUser.getUsername(),
                // providerId = authUser.getSource()
                authUser.getUsername() + "_" + authUser.getSource(),
                // providerUserId = authUser.getUuid()
                authUser.getUsername() + "_" + authUser.getSource() + "_" + authUser.getUuid()
        };
    }

	@Override
	public List<Boolean> existedByUsernames(String... usernames) throws IOException
	{
        // ... 在本地账户上查询 userIds 是否已被使用
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(false);
        list.add(false);

        return list;
	}

    @Override
    public UserDetails registerUser(@NonNull AuthUser authUser, @NonNull String username,
                                    @NonNull String defaultAuthority, String decodeState) throws RegisterUserFailureException {

        // 第三方授权登录不需要密码, 这里随便设置的, 生成环境按自己的逻辑
        String encodedPassword = passwordEncoder.encode(authUser.getUuid());

        // 这里的 decodeState 可以根据自己实现的 top.dcenter.ums.security.core.oauth.service.Auth2StateCoder 接口的逻辑来传递必要的参数.
        // 比如: 第三方登录成功后的跳转地址
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 假设 decodeState 就是 redirectUrl, 我们直接把 redirectUrl 设置到 request 上
        // 后续经过成功处理器时直接从 requestAttributes.getAttribute("redirectUrl", RequestAttributes.SCOPE_REQUEST) 获取并跳转
        if (requestAttributes != null) {
            requestAttributes.setAttribute("redirectUrl", decodeState, RequestAttributes.SCOPE_REQUEST);
        }
        // 当然 decodeState 也可以传递从前端传到后端的用户信息, 注册到本地用户

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(defaultAuthority);

        // ... 用户注册逻辑

        log.info("Demo ======>: 用户名：{}, 注册成功", username);

        // @formatter:off
        UserDetails user = User.builder()
                               .username(username)
                               .password(encodedPassword)
                               .disabled(false)
                               .accountExpired(false)
                               .accountLocked(false)
                               .credentialsExpired(false)
                               .authorities(grantedAuthorities)
                               .build();
        // @formatter:off

        // 把用户信息存入缓存
        if (userCache != null)
        {
            userCache.putUserInCache(user);
        }

        return user;
    }
}
