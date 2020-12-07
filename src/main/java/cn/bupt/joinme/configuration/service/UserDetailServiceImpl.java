package cn.bupt.joinme.configuration.service;

import cn.bupt.joinme.dao.UserDao;
import cn.bupt.joinme.dao.PermissonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissonDao permissonDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cn.bupt.joinme.model.User user = userDao.getUser(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        List<String> sysPermissions = permissonDao.getUserPermisson(username);
        sysPermissions.forEach(sysPermission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission);
            grantedAuthorities.add(grantedAuthority);
        });

        System.out.println("password" + user.getPassword());
        return new User(user.getName(), user.getPassword(),grantedAuthorities);
    }
}
