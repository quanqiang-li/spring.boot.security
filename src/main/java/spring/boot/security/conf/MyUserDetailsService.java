package spring.boot.security.conf;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//查询用户信息,包括权限
		List<GrantedAuthority> createAuthorityList = AuthorityUtils.createAuthorityList("ADMIN","USER");
		MyUserDetails details = new MyUserDetails(username, null, createAuthorityList);
		details.setEmail("123456@qq.com");
		return null;
	}

}
