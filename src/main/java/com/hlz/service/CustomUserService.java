package com.hlz.service;

import com.hlz.dao.UserDAO;
import com.hlz.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator 2017-3-3
 */
@Service
public class CustomUserService implements UserDetailsService{
    @Autowired
    private UserDAO dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=dao.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }

}
